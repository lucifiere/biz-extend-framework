package com.lucifiere.biz.domain.ability.impl;

import com.lucifiere.bef.annotation.Ability;
import com.lucifiere.bef.specific.impl.AbstractCustomizableAbility;
import com.lucifiere.biz.activities.constants.DomainConstants;
import com.lucifiere.biz.domain.ability.IAddedPriceAbility;
import com.lucifiere.biz.domain.ext.IPriceBusinessExt;
import com.lucifiere.biz.domain.model.PriceModel;

/**
 * The type Abstract added price ability.
 *
 * @author 忠魂
 */
@Ability(parent = DomainConstants.DOMAIN_PRICE, name = "附加价格能力", code = AbstractAddedPriceAbility.CODE)
public abstract class AbstractAddedPriceAbility extends AbstractCustomizableAbility<PriceModel, IPriceBusinessExt>

    implements IAddedPriceAbility {

    /**
     * The constant CODE.
     */
    public static final String CODE = "AbstractAddedPriceAbility";

}
