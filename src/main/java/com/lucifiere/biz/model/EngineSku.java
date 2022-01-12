package com.lucifiere.biz.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 计算引擎专用-商品SKU模型
 *
 * @author 忠魂
 */
public class EngineSku implements Serializable {

    private static final long serialVersionUID = 7536530681555621118L;

    /**
     * 唯一标识
     */
    private String id;

    /**
     * sku编码
     */
    private String skuCode;
    /**
     * sku类型
     */
    private String productType;

    /**
     * sku原始价格
     */
    private Money skuPrice;

    /**
     * sku标准价
     */
    private Money skuStrandPrice;

    /**
     * sku原始服务费
     */
    private Money skuServiceFee;

    /**
     * sku原始代收费
     */
    private Money skuAgentFee;

    /**
     * sku原始最低保护价
     */
    private Money skuLowestPrice;

    /**
     * sku最低保护价格
     */
    private Money lowestPrice;
    /**
     * sku 扩展属性
     */
    private Map<String, String> attributeMap = new HashMap<>(16);
    /**
     * sku feature属性
     */
    private Map<String, String> featureMap = new HashMap<>(10);
    /**
     * sku当前剩余价
     */
    private Money skuRemainPrice;
    /**
     * sku当前剩余渠道代收费
     */
    private Money skuRemainAgentFee;
    /**
     * sku当前剩余服务费
     */
    private Money skuRemainServiceFee;

    /**
     * 是否命中优惠
     */
    private Boolean canPromotion;

    /**
     * 占用的优惠
     */
    private long surplusPromCredit;

    /**
     * 规则EXT-NO列表（注意这个不是ruleId @和同）
     */
    private List<String> ruleIdList;

    /**
     * 商品序列号--卖品为区同一商品因某些不同而区分
     */
    private String serialNum;

    /**
     * 优惠快照
     */
    private EngineSkuSnapshot engineSkuSnapshot = new EngineSkuSnapshot();

    private boolean isOriginal = false;

    /**
     * 获取当前实付总金额
     *
     * @return the current money
     */
    public Money getCurrentMoney() {
        return new Money().addTo(skuRemainPrice).addTo(skuRemainAgentFee).addTo(skuRemainServiceFee);
    }

    /**
     * 获取当前可优惠总金额
     *
     * @return the current money
     */
    public Money getCurrentDiscountMoney(boolean agentFeeFlag, boolean serviceFeeFlag) {
        Money money = new Money();
        money.addTo(skuRemainPrice);
        if (agentFeeFlag) {
            money.addTo(skuRemainAgentFee);
        }
        if (serviceFeeFlag) {
            money.addTo(skuRemainServiceFee);
        }
        return money;
    }

    /**
     * Gets skuCode
     *
     * @return the skuCode
     */
    public String getSkuCode() {
        return skuCode;
    }

    /**
     * Sets skuCode
     *
     * @param skuCode the skuCode
     */
    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    /**
     * Gets productType
     *
     * @return the productType
     */
    public String getProductType() {
        return productType;
    }

    /**
     * Sets productType
     *
     * @param productType the productType
     */
    public void setProductType(String productType) {
        this.productType = productType;
    }

    /**
     * Gets skuPrice
     *
     * @return the skuPrice
     */
    public Money getSkuPrice() {
        return skuPrice;
    }

    /**
     * Sets skuPrice
     *
     * @param skuPrice the skuPrice
     */
    public void setSkuPrice(Money skuPrice) {
        this.skuPrice = skuPrice;
    }

    /**
     * Gets lowestPrice
     *
     * @return the lowestPrice
     */
    public Money getLowestPrice() {
        return lowestPrice;
    }

    /**
     * Sets lowestPrice
     *
     * @param lowestPrice the lowestPrice
     */
    public void setLowestPrice(Money lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    /**
     * Gets attributeMap
     *
     * @return the attributeMap
     */
    public Map<String, String> getAttributeMap() {
        return attributeMap;
    }

    /**
     * Sets attributeMap
     *
     * @param attributeMap the attributeMap
     */
    public void setAttributeMap(Map<String, String> attributeMap) {
        this.attributeMap = attributeMap;
    }

    /**
     * Gets featureMap
     *
     * @return the featureMap
     */
    public Map<String, String> getFeatureMap() {
        return featureMap;
    }

    /**
     * Sets featureMap
     *
     * @param featureMap the featureMap
     */
    public void setFeatureMap(Map<String, String> featureMap) {
        this.featureMap = featureMap;
    }

    /**
     * Gets skuRemainPrice
     *
     * @return the skuRemainPrice
     */
    public Money getSkuRemainPrice() {
        return skuRemainPrice;
    }

    /**
     * Sets skuRemainPrice
     *
     * @param skuRemainPrice the skuRemainPrice
     */
    public void setSkuRemainPrice(Money skuRemainPrice) {
        this.skuRemainPrice = skuRemainPrice;
    }

    /**
     * Gets skuRemainAgentFee
     *
     * @return the skuRemainAgentFee
     */
    public Money getSkuRemainAgentFee() {
        return skuRemainAgentFee;
    }

    /**
     * Sets skuRemainAgentFee
     *
     * @param skuRemainAgentFee the skuRemainAgentFee
     */
    public void setSkuRemainAgentFee(Money skuRemainAgentFee) {
        this.skuRemainAgentFee = skuRemainAgentFee;
    }

    /**
     * Gets skuRemainServiceFee
     *
     * @return the skuRemainServiceFee
     */
    public Money getSkuRemainServiceFee() {
        return skuRemainServiceFee;
    }

    /**
     * Sets skuRemainServiceFee
     *
     * @param skuRemainServiceFee the skuRemainServiceFee
     */
    public void setSkuRemainServiceFee(Money skuRemainServiceFee) {
        this.skuRemainServiceFee = skuRemainServiceFee;
    }

    /**
     * Gets engineSkuSnapshot
     *
     * @return the engineSkuSnapshot
     */
    public EngineSkuSnapshot getEngineSkuSnapshot() {
        return engineSkuSnapshot;
    }

    /**
     * Sets engineSkuSnapshot
     *
     * @param engineSkuSnapshot the engineSkuSnapshot
     */
    public void setEngineSkuSnapshot(EngineSkuSnapshot engineSkuSnapshot) {
        this.engineSkuSnapshot = engineSkuSnapshot;
    }

    /**
     * Gets surplusPromCredit
     *
     * @return the surplusPromCredit
     */
    public long getSurplusPromCredit() {
        return surplusPromCredit;
    }

    /**
     * Sets surplusPromCredit
     *
     * @param surplusPromCredit the surplusPromCredit
     */
    public void setSurplusPromCredit(long surplusPromCredit) {
        this.surplusPromCredit = surplusPromCredit;
    }

    /**
     * Gets canPromotion
     *
     * @return the canPromotion
     */
    public Boolean getCanPromotion() {
        return canPromotion;
    }

    /**
     * Sets canPromotion
     *
     * @param canPromotion the canPromotion
     */
    public void setCanPromotion(Boolean canPromotion) {
        this.canPromotion = canPromotion;
    }

    public Money getSkuServiceFee() {
        return skuServiceFee;
    }

    public void setSkuServiceFee(Money skuServiceFee) {
        this.skuServiceFee = skuServiceFee;
    }

    public Money getSkuAgentFee() {
        return skuAgentFee;
    }

    public void setSkuAgentFee(Money skuAgentFee) {
        this.skuAgentFee = skuAgentFee;
    }

    public Money getRemainTotalPrice() {
        return skuRemainPrice.add(skuRemainServiceFee).add(skuRemainAgentFee);
    }

    public Money getOrginalTotalPrice() {
        return skuPrice.add(skuServiceFee).add(skuAgentFee);
    }

    public Money getSkuStrandPrice() {
        return skuStrandPrice;
    }

    public void setSkuStrandPrice(Money skuStrandPrice) {
        this.skuStrandPrice = skuStrandPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getRuleIdList() {
        return ruleIdList;
    }

    public void setRuleIdList(List<String> ruleIdList) {
        this.ruleIdList = ruleIdList;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public Money getSkuLowestPrice() {
        return skuLowestPrice;
    }

    public void setSkuLowestPrice(Money skuLowestPrice) {
        this.skuLowestPrice = skuLowestPrice;
    }

    public boolean isOriginal() {
        return isOriginal;
    }

    public void setOriginal(boolean original) {
        isOriginal = original;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EngineSku engineSku = (EngineSku) o;
        return Objects.equals(id, engineSku.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
