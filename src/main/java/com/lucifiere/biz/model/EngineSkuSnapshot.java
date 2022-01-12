package com.lucifiere.biz.model;

import java.io.Serializable;

/**
 * 计算引擎专用-商品SKU模型优惠快照
 * @author 和同
 */
public class EngineSkuSnapshot implements Serializable {

    private static final long serialVersionUID = -5829714818745663205L;
    /**
     * 计算前商品价格
     */
    private long beforeSkuPrice;
    /**
     * 计算后商品价格
     */
    private long afterSkuPrice;
    /**
     * 计算前代收费
     */
    private long beforeAgentFee;
    /**
     * 计算后代收费
     */
    private long afterAgentFee;
    /**
     * 计算前服务费
     */
    private long beforeServiceFee;
    /**
     * 计算后服务费
     */
    private long afterServiceFee;
    /**
     * 优惠类型
     */
    private String promotionType;
    /**
     * 优惠编码
     */
    private String promotionCode;

    /**
     * 优惠编码对应的名称
     */
    private String promotionName;

    /**
     * 优惠总金额
     */
    private Long discountAmount;
    /**
     * 支付方式
     */
    private String paymentMethod;

    /**
     * 支付金额
     */
    private long paymentAmount;

    /**
     * 支付方式
     * @see CompensateParty
     */
    private String payType;

    /**
     * 谁支付的
     */
    private String payCode;

    /**
     * 支付名称
     */
    private String payName;

    /**
     * 系统来源
     */
    private String systemSource;

    /**
     * Gets beforeSkuPrice
     *
     * @return the beforeSkuPrice
     */
    public long getBeforeSkuPrice() {
        return beforeSkuPrice;
    }

    /**
     * Sets beforeSkuPrice
     *
     * @param beforeSkuPrice the beforeSkuPrice
     */
    public void setBeforeSkuPrice(long beforeSkuPrice) {
        this.beforeSkuPrice = beforeSkuPrice;
    }

    /**
     * Gets afterSkuPrice
     *
     * @return the afterSkuPrice
     */
    public long getAfterSkuPrice() {
        return afterSkuPrice;
    }

    /**
     * Sets afterSkuPrice
     *
     * @param afterSkuPrice the afterSkuPrice
     */
    public void setAfterSkuPrice(long afterSkuPrice) {
        this.afterSkuPrice = afterSkuPrice;
    }

    /**
     * Gets beforeServiceFee
     *
     * @return the beforeServiceFee
     */
    public long getBeforeServiceFee() {
        return beforeServiceFee;
    }

    /**
     * Sets beforeServiceFee
     *
     * @param beforeServiceFee the beforeServiceFee
     */
    public void setBeforeServiceFee(long beforeServiceFee) {
        this.beforeServiceFee = beforeServiceFee;
    }

    /**
     * Gets afterServiceFee
     *
     * @return the afterServiceFee
     */
    public long getAfterServiceFee() {
        return afterServiceFee;
    }

    /**
     * Sets afterServiceFee
     *
     * @param afterServiceFee the afterServiceFee
     */
    public void setAfterServiceFee(long afterServiceFee) {
        this.afterServiceFee = afterServiceFee;
    }

    /**
     * Gets beforeAgentFee
     *
     * @return the beforeAgentFee
     */
    public long getBeforeAgentFee() {
        return beforeAgentFee;
    }

    /**
     * Sets beforeAgentFee
     *
     * @param beforeAgentFee the beforeAgentFee
     */
    public void setBeforeAgentFee(long beforeAgentFee) {
        this.beforeAgentFee = beforeAgentFee;
    }

    /**
     * Gets afterAgentFee
     *
     * @return the afterAgentFee
     */
    public long getAfterAgentFee() {
        return afterAgentFee;
    }

    /**
     * Sets afterAgentFee
     *
     * @param afterAgentFee the afterAgentFee
     */
    public void setAfterAgentFee(long afterAgentFee) {
        this.afterAgentFee = afterAgentFee;
    }

    /**
     * Gets promotionType
     *
     * @return the promotionType
     */
    public String getPromotionType() {
        return promotionType;
    }

    /**
     * Sets promotionType
     *
     * @param promotionType the promotionType
     */
    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    /**
     * Gets promotionCode
     *
     * @return the promotionCode
     */
    public String getPromotionCode() {
        return promotionCode;
    }

    /**
     * Sets promotionCode
     *
     * @param promotionCode the promotionCode
     */
    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    /**
     * Gets discountAmount
     *
     * @return the discountAmount
     */
    public Long getDiscountAmount() {
        return discountAmount;
    }

    /**
     * Sets discountAmount
     *
     * @param discountAmount the discountAmount
     */
    public void setDiscountAmount(Long discountAmount) {
        this.discountAmount = discountAmount;
    }

    /**
     * Gets paymentMethod
     *
     * @return the paymentMethod
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Sets paymentMethod
     *
     * @param paymentMethod the paymentMethod
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Gets paymentAmount
     *
     * @return the paymentAmount
     */
    public long getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * Sets paymentAmount
     *
     * @param paymentAmount the paymentAmount
     */
    public void setPaymentAmount(long paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    /**
     * Gets payCode
     *
     * @return the payCode
     */
    public String getPayCode() {
        return payCode;
    }

    /**
     * Sets payCode
     *
     * @param payCode the payCode
     */
    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getSystemSource() {
        return systemSource;
    }

    public void setSystemSource(String systemSource) {
        this.systemSource = systemSource;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }
}
