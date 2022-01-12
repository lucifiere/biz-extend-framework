package com.lucifiere.engine;

import com.lucifiere.bef.specific.IBizInstance;
import com.lucifiere.bef.specific.impl.BizInstanceId;

/**
 * 优惠计算上下文
 *
 * @author 沾清
 * @version 1.0
 * @since 1.0 2019年7月9日
 */
public class EngineContext implements IBizInstance {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 2552549189093254809L;

    /**
     * 优惠计算执行上下文
     */
    private PromotionContext promotionContext;

    /**
     * 场景域执行上下文
     */
    private SceneContext sceneContext;

    /**
     * 规则域执行上下文
     */
    private RuleContext ruleContext;

    /**
     * 执行上下文的BizInstanceId决定了各域采用哪个产品的扩展点
     */
    private BizInstanceId bizInstanceId;

    /**
     * Ext Rule Meta Info
     */
    private PromotionIdentity promotionIdentity;

    /**
     * Instantiates a new Engine context.
     */
    public EngineContext() {
        this.promotionContext = new PromotionContext();
        this.sceneContext = new SceneContext();
        this.ruleContext = new RuleContext();
    }

    /**
     * Gets promotionContext
     *
     * @return the promotionContext
     */
    public PromotionContext getPromotionContext() {
        return promotionContext;
    }

    /**
     * Gets promotionContext
     *
     * @return the sceneContext
     */
    public SceneContext getSceneContext() {
        return sceneContext;
    }

    /**
     * Gets ruleContext
     *
     * @return the sceneContext
     */
    public RuleContext getRuleContext() {
        return ruleContext;
    }

    /**
     * Gets biz instance id.
     *
     * @return the biz instance id
     */
    @Override
    public BizInstanceId getBizInstanceId() {
        return bizInstanceId;
    }

    /**
     * Sets bizInstanceId
     *
     * @param bizInstanceId the bizInstanceId
     */
    public void setBizInstanceId(BizInstanceId bizInstanceId) {
        this.bizInstanceId = bizInstanceId;
    }

    public PromotionIdentity getPromotionIdentity() {
        return promotionIdentity;
    }

    public void setPromotionIdentity(PromotionIdentity promotionIdentity) {
        this.promotionIdentity = promotionIdentity;
    }
}
