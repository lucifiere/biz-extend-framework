package com.lucifiere.biz.domain;

import com.lucifiere.dme.bcp.ump.bef.annotation.Domain;
import com.lucifiere.dme.bcp.ump.bef.specific.IDomainNs;
import com.lucifiere.dme.bcp.ump.biz.activities.constants.DomainConstants;

/**
 * The type Price domain ns.
 *
 * @author 忠魂
 */
@Domain(code = DomainConstants.DOMAIN_PRICE, name = DomainConstants.DOMAIN_PRICE_NAME)
public class PriceDomainNs implements IDomainNs {

    /**
     * Gets code.
     *
     * @return the code
     */
    @Override
    public String getCode() {
        return DomainConstants.DOMAIN_PRICE;
    }
}
