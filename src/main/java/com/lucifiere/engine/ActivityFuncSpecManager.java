package com.lucifiere.engine;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lucifiere.bef.util.ClassPathScanHandler;
import com.lucifiere.engine.annotation.ActivityFuncDefine;
import com.lucifiere.engine.register.ActivityFuncSpec;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 活动函数定义管理器
 *
 * @author 忠魂
 */
public class ActivityFuncSpecManager {

    /**
     * key: code
     */
    private final Map<String, ActivityFuncSpec> codeCaches = Maps.newConcurrentMap();
    /**
     * key : macroName
     */
    private final Map<String, ActivityFuncSpec> macroCaches = Maps.newConcurrentMap();
    /**
     * instance.
     */
    private static volatile ActivityFuncSpecManager instance = null;

    /**
     * private singleton
     */
    private ActivityFuncSpecManager() {
    }

    /**
     * singleton
     *
     * @return get the instance.
     */
    public static ActivityFuncSpecManager getInstance() {
        if (instance == null) {
            synchronized (ActivityFuncSpecManager.class) {
                if (instance == null) {
                    instance = new ActivityFuncSpecManager();
                }
            }
        }
        return instance;
    }

    /**
     * Register activity funcs list.
     *
     * @param classPackages the class packages
     */
    public void registerActivityFuncs(String... classPackages) {
        if (null == classPackages) {
            return;
        }
        ClassPathScanHandler handler = new ClassPathScanHandler();
        for (String classPackage : classPackages) {
            Set<Class<?>> classSet = handler.getPackageAllClasses(classPackage, true);
            for (Class<?> targetClass : classSet) {
                Method[] methods = targetClass.getMethods();
                for (Method m : methods) {
                    ActivityFuncDefine activity = m.getAnnotation(ActivityFuncDefine.class);
                    if (null == activity) {
                        continue;
                    }
                    ActivityFuncSpec activityFuncSpec = ActivityFuncSpec.of(activity.code());
                    activityFuncSpec.setDesc(activity.desc());
                    activityFuncSpec.setMacroName(activity.macroName());
                    activityFuncSpec.setExpress(
                        StringUtils.uncapitalize(StringUtils.substringAfterLast(targetClass.getName(), ".")) + "." + m
                            .getName() + "(context)");
                    activityFuncSpec.setParams(activity.params());
                    activityFuncSpec.setActionType(activity.actionType());
                    activityFuncSpec.setMetaType(activity.metaType());
                    if (codeCaches.containsKey(activityFuncSpec.getCode())) {
                        throw new RuntimeException(
                            "activity func spec code repeat! code=" + activityFuncSpec.getCode());
                    }
                    if (macroCaches.containsKey(activityFuncSpec.getMacroName())) {
                        throw new RuntimeException(
                            "activity func spec macroName repeat! macroName=" + activityFuncSpec.getMacroName());
                    }
                    macroCaches.putIfAbsent(activityFuncSpec.getMacroName(), activityFuncSpec);
                    codeCaches.putIfAbsent(activityFuncSpec.getCode(), activityFuncSpec);
                }
            }
        }
    }

    /**
     * get all
     *
     * @return the all activity func spec
     */
    public List<ActivityFuncSpec> getAllActivityFuncSpec() {
        if (CollectionUtils.isEmpty(macroCaches)) {
            return Collections.emptyList();
        }
        return Lists.newArrayList(macroCaches.values());
    }

    /**
     * 根据code获取ActivityFuncSpec
     *
     * @param code the code
     * @return the activity func spec by code
     */
    public ActivityFuncSpec getActivityFuncSpecByCode(String code) {
        return codeCaches.get(code);
    }

    /**
     * 根据macroName获取ActivityFuncSpec
     *
     * @param macroName the macro name
     * @return the activity func spec by macro name
     */
    public ActivityFuncSpec getActivityFuncSpecByMacroName(String macroName) {
        return macroCaches.get(macroName);
    }
}
