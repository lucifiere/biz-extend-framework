package com.lucifiere.biz.product.platform;

import com.lucifiere.bef.annotation.Product;
import com.lucifiere.bef.constant.BefConstants;
import com.lucifiere.bef.template.AbstractTemplate;

/**
 * 平台产品
 *
 * @author 忠魂
 */
@Product(name = "商业能力-Themis平台", code = PlatFormProduct.CODE)
public class PlatFormProduct extends AbstractTemplate<PlatFormExtFacade> {

    public static final String CODE = BefConstants.PLATFORM_PRODUCT_CODE;

}
