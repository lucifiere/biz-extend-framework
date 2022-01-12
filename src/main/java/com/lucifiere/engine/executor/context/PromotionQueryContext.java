package com.lucifiere.engine.executor.context;

import java.util.ArrayList;
import java.util.List;

/**
 * 计算域优惠上下文
 *
 * @author XD.Wang
 * @version 1.0
 * @since 1.0 2019年6月13日
 */
public class PromotionQueryContext implements PromotionExecContext {

    private String instanceCode;

    /**
     * 库中查到的规则
     */
    private List<ThemisExtVO> extList;

    /**
     * 外界查询条件
     */
    private SkuSearchCond searchCond;

    /**
     * 查询可用规则的结果
     */
    private List<PromotionDetailResult> promotionDetailResults = new ArrayList<>();

    public List<ThemisExtVO> getExtList() {
        return extList;
    }

    public void setExtList(List<ThemisExtVO> extList) {
        this.extList = extList;
    }

    public String getInstanceCode() {
        return instanceCode;
    }

    public void setInstanceCode(String instanceCode) {
        this.instanceCode = instanceCode;
    }

    public SkuSearchCond getSearchCond() {
        return searchCond;
    }

    public void setSearchCond(SkuSearchCond searchCond) {
        this.searchCond = searchCond;
    }

    public List<PromotionDetailResult> getPromotionDetailResults() {
        return promotionDetailResults;
    }

    public void setPromotionDetailResults(List<PromotionDetailResult> promotionDetailResults) {
        this.promotionDetailResults = promotionDetailResults;
    }

    public void addPromotionQueryResult(PromotionDetailResult res) {
        this.promotionDetailResults.add(res);
    }
}
