package com.lucifiere.engine.register;

import com.lucifiere.engine.ActivityFuncSpecManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * activity中提供的方法相关 自定义注册器
 * 主要为规则引擎自动注册使用
 *
 * @author 忠魂
 */
@Component
public class ActivityFuncRegister  {

    /**
     * The Register activities.
     */
    private final String[] registerActivities = {"com.lucifiere.dme.bef.activities"};

    /**
     * Register.
     */
    @PostConstruct
    public void register() {
        ActivityFuncSpecManager.getInstance().registerActivityFuncs(registerActivities);
    }

}
