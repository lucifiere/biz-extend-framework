package com.lucifiere.biz.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 计算引擎专用-商品SKU计算结果
 *
 * @author 和同
 */
public class EngineSkuResult {

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
     * 商品序列号--卖品为区同一商品因某些不同而区分
     */
    private String serialNum;

    /**
     * 是否命中优惠
     */
    private Boolean canPromotion;

    /**
     * 规则id列表
     */
    private List<String> ruleIdList;

    /**
     * 已经参加过的规则类型
     */
    private List<String> partookRuleType = new ArrayList<>();

    /**
     * sku 扩展属性
     */
    private Map<String, String> attributeMap = new HashMap<>(16);
    /**
     * sku feature属性
     */
    private Map<String, String> featureMap = new HashMap<>(10);

    /**
     * 商品计算优惠列表
     */
    private List<EngineSkuSnapshot> engineSkuSnapshotList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Money getSkuPrice() {
        return skuPrice;
    }

    public void setSkuPrice(Money skuPrice) {
        this.skuPrice = skuPrice;
    }

    public Money getSkuStrandPrice() {
        return skuStrandPrice;
    }

    public void setSkuStrandPrice(Money skuStrandPrice) {
        this.skuStrandPrice = skuStrandPrice;
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

    public Money getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(Money lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public Money getSkuRemainPrice() {
        return skuRemainPrice;
    }

    public void setSkuRemainPrice(Money skuRemainPrice) {
        this.skuRemainPrice = skuRemainPrice;
    }

    public Money getSkuRemainAgentFee() {
        return skuRemainAgentFee;
    }

    public void setSkuRemainAgentFee(Money skuRemainAgentFee) {
        this.skuRemainAgentFee = skuRemainAgentFee;
    }

    public Money getSkuRemainServiceFee() {
        return skuRemainServiceFee;
    }

    public void setSkuRemainServiceFee(Money skuRemainServiceFee) {
        this.skuRemainServiceFee = skuRemainServiceFee;
    }

    public Boolean getCanPromotion() {
        return canPromotion;
    }

    public void setCanPromotion(Boolean canPromotion) {
        this.canPromotion = canPromotion;
    }

    public List<EngineSkuSnapshot> getEngineSkuSnapshotList() {
        return engineSkuSnapshotList;
    }

    public void setEngineSkuSnapshotList(List<EngineSkuSnapshot> engineSkuSnapshotList) {
        this.engineSkuSnapshotList = engineSkuSnapshotList;
    }

    public Money getRemainTotalPrice() {
        return skuRemainPrice.add(skuRemainServiceFee).add(skuRemainAgentFee);
    }

    public Money getOrginalTotalPrice() {
        return skuPrice.add(skuServiceFee).add(skuAgentFee);
    }

    public List<String> getRuleIdList() {
        return ruleIdList;
    }

    public void setRuleIdList(List<String> ruleIdList) {
        this.ruleIdList = ruleIdList;
    }

    public Map<String, String> getAttributeMap() {
        return attributeMap;
    }

    public void setAttributeMap(Map<String, String> attributeMap) {
        this.attributeMap = attributeMap;
    }

    public Map<String, String> getFeatureMap() {
        return featureMap;
    }

    public void setFeatureMap(Map<String, String> featureMap) {
        this.featureMap = featureMap;
    }

    public List<String> getPartookRuleType() {
        return partookRuleType;
    }

    public void setPartookRuleType(List<String> partookRuleType) {
        this.partookRuleType = partookRuleType;
    }

    public void addPartakeType(String ruleType) {
        this.partookRuleType.add(ruleType);
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
}
