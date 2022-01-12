package com.lucifiere.bef.model;

/**
 * 领域服务定义
 *
 * @author 沾清
 */
public class DomainServiceSpec {

    /**
     * domain service's unique code.
     */
    private String serviceCode;

    /**
     * domain service's name for display.
     */
    private String serviceName;

    /**
     * domain service's description.
     */
    private String description;

    /**
     * create a DomainServiceSpec from code and name.
     *
     * @param serviceCode service code.
     * @param serviceName service name.
     * @return the created DomainServiceSpec.
     */
    public static DomainServiceSpec of(String serviceCode, String serviceName) {
        DomainServiceSpec domainServiceSpec = new DomainServiceSpec();
        domainServiceSpec.serviceCode = serviceCode;
        domainServiceSpec.serviceName = serviceName;
        return domainServiceSpec;
    }

    /**
     * Gets serviceCode
     *
     * @return the serviceCode
     */
    public String getServiceCode() {
        return serviceCode;
    }

    /**
     * Sets serviceCode
     *
     * @param serviceCode the serviceCode
     */
    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    /**
     * Gets serviceName
     *
     * @return the serviceName
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * Sets serviceName
     *
     * @param serviceName the serviceName
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * Gets description
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
