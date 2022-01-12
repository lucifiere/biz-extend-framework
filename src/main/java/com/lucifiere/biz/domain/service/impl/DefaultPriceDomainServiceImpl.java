package com.lucifiere.biz.domain.service.impl;

import com.lucifiere.biz.constants.ActionBrevityCode;
import com.lucifiere.biz.constants.CalLevel;
import com.lucifiere.biz.constants.CommonCustomConditionKeys;
import com.lucifiere.biz.domain.ability.IAddedPriceAbility;
import com.lucifiere.biz.domain.ability.IPriceAbility;
import com.lucifiere.biz.domain.ability.impl.AbstractAddedPriceAbility;
import com.lucifiere.biz.domain.ability.impl.AbstractPriceAbility;
import com.lucifiere.biz.domain.model.PriceModel;
import com.lucifiere.biz.domain.service.PriceDomainService;
import com.lucifiere.common.exception.BusinessException;
import com.lucifiere.common.utils.TypeConvertUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * The type Default price domain service.
 *
 * @author 忠魂
 */
@Service
public class DefaultPriceDomainServiceImpl implements PriceDomainService {

    @Override
    public void discount(PriceModel model) {
        IPriceAbility priceAbility = getFirstAbility(AbstractPriceAbility.CODE, model);
        //为空则不处理
        if (priceAbility == null) {
            return;
        }
        priceAbility.discount(model);
    }

    @Override
    public void reduce(PriceModel model) {
        IPriceAbility priceAbility = getFirstAbility(AbstractPriceAbility.CODE, model);
        //为空则不处理
        if (priceAbility == null) {
            return;
        }
        Map<String, String> paramMap = model.getParamMap();
        // 计算级别
        String calLevel = TypeConvertUtil.getAsString(paramMap, CommonCustomConditionKeys.CAL_LEVEL, CalLevel.SKU_LEVEL);
        // 单商品
        if (CalLevel.SKU_LEVEL.equals(calLevel)) {
            priceAbility.skuReduce(model);
        } else if (CalLevel.ORDER_LEVEL.equals(calLevel)) {
            // 多商品
            priceAbility.orderReduce(model);
        }
    }

    @Override
    public void reduceTo(PriceModel model) {
        IPriceAbility priceAbility = getFirstAbility(AbstractPriceAbility.CODE, model);
        //为空则不处理
        if (priceAbility == null) {
            return;
        }
        Map<String, String> paramMap = model.getParamMap();
        // 计算级别
        String calLevel = TypeConvertUtil.getAsString(paramMap, CommonCustomConditionKeys.CAL_LEVEL, CalLevel.SKU_LEVEL);
        // 单商品
        if (CalLevel.SKU_LEVEL.equals(calLevel)) {
            priceAbility.skuReduceTo(model);
        } else if (CalLevel.ORDER_LEVEL.equals(calLevel)) {
            // 多商品
            priceAbility.orderReduceTo(model);
        }
    }

    @Override
    public void lowestPlus(PriceModel model) {
        IPriceAbility priceAbility = getFirstAbility(AbstractPriceAbility.CODE, model);
        //为空则不处理
        if (priceAbility == null) {
            return;
        }
        priceAbility.lowestPlus(model);
    }

    @Override
    public void agentFee(PriceModel model) {
        IAddedPriceAbility priceAbility = getFirstAbility(AbstractAddedPriceAbility.CODE, model);
        //为空则不处理
        if (priceAbility == null) {
            return;
        }
        Map<String, String> paramMap = model.getParamMap();
        if (paramMap.isEmpty() || paramMap.get(ActionBrevityCode.AGENTFEE_CAL) == null) {
            throw new BusinessException("");
        }
        priceAbility.agentFee(model);
    }

    @Override
    public void serviceFee(PriceModel model) {
        IAddedPriceAbility priceAbility = getFirstAbility(AbstractAddedPriceAbility.CODE, model);
        //为空则不处理
        if (priceAbility == null) {
            return;
        }
        Map<String, String> paramMap = model.getParamMap();
        if (paramMap.isEmpty() || paramMap.get(ActionBrevityCode.SERVICEFEE_CAL) == null) {
            throw new BusinessException("");
        }
        priceAbility.serviceFee(model);
    }

    @Override
    public void subsidy(PriceModel model) {
        IPriceAbility priceAbility = getFirstAbility(AbstractPriceAbility.CODE, model);
        //为空则不处理
        if (priceAbility == null) {
            return;
        }
        priceAbility.subsidy(model);
    }
}
