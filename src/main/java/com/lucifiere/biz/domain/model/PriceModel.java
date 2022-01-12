package com.lucifiere.biz.domain.model;

import com.lucifiere.dme.bcp.ump.bef.specific.IBizInstance;
import com.lucifiere.dme.bcp.ump.bef.specific.impl.BizInstanceId;
import com.lucifiere.dme.bcp.ump.biz.cal.model.EngineSku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Price model.
 *
 * @author 忠魂
 */
public class PriceModel implements IBizInstance {

    /**
     * The Biz instance id.
     */
    private BizInstanceId bizInstanceId;
    /**
     * 优惠执行参数
     */
    private Map<String, String> paramMap = new HashMap<>(0);

    /**
     * The Engine sku list.
     */
    private List<EngineSku> engineSkuList = new ArrayList<>();

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

    /**
     * Gets paramMap
     *
     * @return the paramMap
     */
    public Map<String, String> getParamMap() {
        return paramMap;
    }

    /**
     * Sets paramMap
     *
     * @param paramMap the paramMap
     */
    public void setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }

    /**
     * Gets engineSkuList
     *
     * @return the engineSkuList
     */
    public List<EngineSku> getEngineSkuList() {
        return engineSkuList;
    }

    /**
     * Sets engineSkuList
     *
     * @param engineSkuList the engineSkuList
     */
    public void setEngineSkuList(List<EngineSku> engineSkuList) {
        this.engineSkuList = engineSkuList;
    }
}
