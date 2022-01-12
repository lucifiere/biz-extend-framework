package com.lucifiere.biz.activities.scene;

import com.lucifiere.biz.activities.constants.ThemisFunctionNameConstants;
import com.lucifiere.common.utils.SceneTools;
import com.lucifiere.engine.EngineContext;
import com.lucifiere.engine.annotation.ActivityFuncDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 场景域节点
 *
 * @author 沾清
 * @version 1.0
 * @since 1.0 2019年4月8日
 */
@Component
public class SceneActivity {

    private static final Logger LOGGER = LoggerFactory.getLogger(SceneActivity.class);

//    @Autowired
//    private SceneDomainService sceneDomainService;

    /**
     * 场景域--规则有效时间过滤
     *
     * @param context 计算引擎上下文
     */
    @ActivityFuncDefine(code = "com.lucifiere.dme.bef.scene.effectiveTimeCheck",
            macroName = ThemisFunctionNameConstants.SceneDomainExpressName.EFFECTIVE_TIME_CHECK, desc = "有效时间规则过滤")
    public void effectiveTimeCheck(EngineContext context) {
//        SceneTools.sceneCheck(context, model -> sceneDomainService.effectiveTimeCheck(model));
    }

    /**
     * 场景域--规则有效时间过滤
     *
     * @param context 计算引擎上下文
     */
    @ActivityFuncDefine(code = "com.lucifiere.dme.bef.scene.fixedCheck",
            macroName = ThemisFunctionNameConstants.SceneDomainExpressName.FIXED_CHECK, desc = "固定规则查询")
    public void fixedCheck(EngineContext context) {
//        SceneTools.sceneCheck(context, model -> sceneDomainService.fixedCheck(model));
//        if (context.getSceneContext().getSceneResults().stream().anyMatch(SceneResult::ineligible)) {
//            throw new BusinessException(CommonInnerCode.BREAK_SCENE_CHECK);
//        }
    }

    /**
     * 场景域--规则排除时间过滤
     *
     * @param context 计算引擎上下文
     */
    @ActivityFuncDefine(code = "com.lucifiere.dme.bef.scene.excludeTimeCheck",
            macroName = ThemisFunctionNameConstants.SceneDomainExpressName.EXCLUDE_TIME_CHECK, desc = "排除时间规则校验")
    public void excludeTimeCheck(EngineContext context) {
//        SceneTools.sceneCheck(context, model -> sceneDomainService.excludeTimeCheck(model));
    }

    /**
     * 场景域--自定义过滤
     *
     * @param context 计算引擎上下文
     */
    @ActivityFuncDefine(code = "com.lucifiere.dme.bef.scene.customCheck",
            macroName = ThemisFunctionNameConstants.SceneDomainExpressName.CUSTOM_CHECK, desc = "自定义规则过滤")
    public void customCheck(EngineContext context) {
//        SceneTools.sceneCheck(context, model -> sceneDomainService.customCheck(model));
    }

    /**
     * 场景域--渠道过滤
     *
     * @param context 计算引擎上下文
     */
    @ActivityFuncDefine(code = "com.lucifiere.dme.bef.scene.channelCheck",
            macroName = ThemisFunctionNameConstants.SceneDomainExpressName.CHANNEL_CHECK, desc = "渠道校验")
    public void channelCheck(EngineContext context) {
//        SceneTools.sceneCheck(context, model -> sceneDomainService.channelCheck(model));
    }

    /**
     * 场景域--商品过滤
     *
     * @param context 计算引擎上下文
     */
    @ActivityFuncDefine(code = "com.lucifiere.dme.bef.scene.itemCheck",
            macroName = ThemisFunctionNameConstants.SceneDomainExpressName.ITEM_CHECK, desc = "商品过滤")
    public void itemCheck(EngineContext context) {
//        SceneTools.sceneCheck(context, model -> sceneDomainService.itemCheck(model));
    }

    /**
     * 场景域--地点过滤
     *
     * @param context 计算引擎上下文
     */
    @ActivityFuncDefine(code = "com.lucifiere.dme.bef.scene.placeCheck",
            macroName = ThemisFunctionNameConstants.SceneDomainExpressName.PLACE_CHECK, desc = "地点过滤")
    public void placeCheck(EngineContext context) {
//        SceneTools.sceneCheck(context, model -> sceneDomainService.placeCheck(model));
    }

    /**
     * 场景域--场景域校验如果失败则终止脚本继续进行，抛出异常
     *
     * @param context 计算引擎上下文
     */
    @ActivityFuncDefine(code = "com.lucifiere.dme.bef.scene.failBlockIfNecessary",
            macroName = ThemisFunctionNameConstants.SceneDomainExpressName.FAIL_BLOCK_IF_NECESSARY, desc = "场景域校验如果失败则终止脚本继续进行")
    public void failBlockIfNecessary(EngineContext context) {
//        SceneResultModel model = SceneConverter.convertToSceneResultModel(context);
//        sceneDomainService.failBlockIfNecessary(model);
    }

}
