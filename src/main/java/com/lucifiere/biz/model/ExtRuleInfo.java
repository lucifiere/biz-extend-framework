package com.lucifiere.biz.model;

import java.util.Map;

/**
 * 外部规则对象
 *
 * @author 和同
 */
public class ExtRuleInfo {

    /**
     * 规则id--一期可以按照system_source+卡/券/营销规则id+商品类型来唯一确定这个规则id
     */
    private String ruleId;

    /**
     * 外部编码(卡号/券号/活动编码)
     */
    private String extNo;

    /**
     * 外部编码对应名称
     */
    private String extName;

    /**
     * 业务编码
     *
     * @see BusinessLineCode
     */
    private Integer businessLineCode;

    /**
     * 系统来源
     * 营销 > 卡 > 券
     *
     * @see SystemSource
     */
    private String systemSource;

    /**
     * 适用商品类型
     *
     * @see ProductType
     */
    private String productType;

    /**
     * 规则类型--对应产品码
     * eg:储值卡，权益卡，代金券，折扣券，影票活动等
     *
     * @see ActivityType
     * @see ThemisCardType
     * @see ThemisCouponType
     */
    private String ruleType;

    /**
     * 计算级别
     *
     * @see CalLevel
     * 其中，所有类型的会员卡都是单品SkuLevel,折扣券，重新定价券，影票活动，卖品立减、减至活动、套餐都是SkuLevel
     * 目前存在的OrderLevel的只有卖品满减活动和代金券
     */
    private String calLevel;

    /**
     * 规则下的保护价设置
     * 影票即对应的最低票价
     * 卖品即对应保护价，默认是0
     */
    private Long lowestPrice;

    /**
     * 外部规则映射
     * 主要是规则及条件的映射关系
     *
     * @see CommonSkuPropertyKeys
     * @see CardRuleBrevityCode
     * @see CouponRuleCode
     * @see MarketingRuleBrevityCode
     * @see ActionBrevityCode
     * @see CardActionBrevityCode
     * @see CouponActionBrevityCode
     * @see MarketingActionBrevityCode
     */
    private Map<String, String> extRuleMap;

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public int getBusinessLineCode() {
        return businessLineCode;
    }

    public void setBusinessLineCode(int businessLineCode) {
        this.businessLineCode = businessLineCode;
    }

    public String getSystemSource() {
        return systemSource;
    }

    public void setSystemSource(String systemSource) {
        this.systemSource = systemSource;
    }

    public Map<String, String> getExtRuleMap() {
        return extRuleMap;
    }

    public void setExtRuleMap(Map<String, String> extRuleMap) {
        this.extRuleMap = extRuleMap;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getExtNo() {
        return extNo;
    }

    public void setExtNo(String extNo) {
        this.extNo = extNo;
    }

    public String getCalLevel() {
        return calLevel;
    }

    public void setCalLevel(String calLevel) {
        this.calLevel = calLevel;
    }

    public Long getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(Long lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public String getExtName() {
        return extName;
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }
}
