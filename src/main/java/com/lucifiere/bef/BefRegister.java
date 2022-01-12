package com.lucifiere.bef;

/**
 * Bef注册器
 *
 * @author 沾清
 */
public class BefRegister {

    /**
     * The Register domain.
     */
    private String[] registerDomain;

    /**
     * The Register product.
     */
    private String[] registerProduct;

    /**
     * 注册功能域、功能点到运营平台
     */
    public void register() {
        com.lucifiere.bef.FunctionManager functionManager = com.lucifiere.bef.FunctionManager.getInstance();
        //注册平台领域
        functionManager.registerDomains(registerDomain);
        //注册平台产品
        functionManager.registerProducts(registerProduct);
    }

    /**
     * Sets registerDomain
     *
     * @param registerDomain the registerDomain
     */
    public void setRegisterDomain(String[] registerDomain) {
        this.registerDomain = registerDomain;
    }

    /**
     * Sets registerProduct
     *
     * @param registerProduct the registerProduct
     */
    public void setRegisterProduct(String[] registerProduct) {
        this.registerProduct = registerProduct;
    }

}
