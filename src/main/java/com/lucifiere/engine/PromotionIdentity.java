package com.lucifiere.engine;

/**
 * 优惠身份上下文
 *
 * @author 沾清
 * @version 1.0
 * @since 1.0 2019年7月9日
 */
public class PromotionIdentity {

    /**
     * 优惠对应的外部ID
     */
    private String extId;

    /**
     * 优惠对应的规则ID
     */
    private Long ruleId;

    /**
     * 优惠对应的细节ID
     */
    private Long detailId;

    /**
     * 产品类型
     */
    private String ruleType;

    public PromotionIdentity() {
    }

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public static PromotionIdentity of(String extId, Long ruleId, Long detailId, String product) {
        PromotionIdentity identity = new PromotionIdentity();
        identity.setDetailId(detailId);
        identity.setExtId(extId);
        identity.setRuleId(ruleId);
        identity.setRuleType(product);
        return identity;
    }

    @Override
    public String toString() {
        return "规则 -> {" +
                "extId='" + extId + '\'' +
                ", ruleId=" + ruleId +
                ", detailId=" + detailId +
                ", ruleType='" + ruleType + '\'' +
                '}';
    }

}
