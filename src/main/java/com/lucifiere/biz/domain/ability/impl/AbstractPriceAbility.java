package com.lucifiere.biz.domain.ability.impl;

import com.lucifiere.bef.annotation.Ability;
import com.lucifiere.bef.specific.impl.AbstractCustomizableAbility;
import com.lucifiere.biz.activities.constants.DomainConstants;
import com.lucifiere.biz.domain.ability.IPriceAbility;
import com.lucifiere.biz.domain.ext.IPriceBusinessExt;
import com.lucifiere.biz.domain.model.PriceModel;

/**
 * The type Abstract price ability.
 *
 * @author 忠魂
 */
@Ability(parent = DomainConstants.DOMAIN_PRICE, name = "价格能力", code = AbstractPriceAbility.CODE)
public abstract class AbstractPriceAbility extends AbstractCustomizableAbility<PriceModel, IPriceBusinessExt>
    implements IPriceAbility {
    /**
     * The constant CODE.
     */
    public static final String CODE = "AbstractPriceAbility";


}
