package com.lucifiere.bef.specific.impl;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * The type Biz instance id.
 *
 * @param <ID> the type parameter
 * @author 沾清
 */
public class BizInstanceId<ID extends Serializable> implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 1575661569091773079L;

    /**
     * The Hash code.
     */
    private int hashCode;

    /**
     * the business unique code.
     */
    private String bizCode;

    /**
     * The business instance id.
     */
    private ID bizInstanceId;

    /**
     * Instantiates a new Biz instance id.
     *
     * @param bizCode       the biz code
     * @param bizInstanceId the biz instance id
     */
    protected BizInstanceId(String bizCode, ID bizInstanceId) {
        this.bizCode = bizCode;
        this.bizInstanceId = bizInstanceId;
        this.hashCode = this.hashCode();
    }

    /**
     * Of biz instance id.
     *
     * @param <T>           the type parameter
     * @param bizCode       the biz code
     * @param bizInstanceId the biz instance id
     * @return the biz instance id
     */
    public static <T extends Serializable> BizInstanceId<T> of(String bizCode, T bizInstanceId) {
        return new BizInstanceId<>(bizCode, bizInstanceId);
    }

    /**
     * Of id biz instance id.
     *
     * @param <T>           the type parameter
     * @param bizInstanceId the biz instance id
     * @return the biz instance id
     */
    public static <T extends Serializable> BizInstanceId<T> ofId(T bizInstanceId) {
        return new BizInstanceId<>(null, bizInstanceId);
    }

    /**
     * Equals boolean.
     *
     * @param o the o
     * @return the boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof BizInstanceId)) { return false; }

        BizInstanceId that = (BizInstanceId)o;
        if (!StringUtils.equals(bizCode, that.bizCode)) { return false; }
        return null != bizInstanceId && bizInstanceId.equals(that.bizInstanceId);
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        int hashCode = this.hashCode;
        if (hashCode > 0) {
            return hashCode;
        }
        int result = 31;
        if (StringUtils.isNotEmpty(bizCode)) {
            result *= bizCode.hashCode();
        }
        if (null != bizInstanceId) {
            result = 31 * result + bizInstanceId.hashCode();
        }
        this.hashCode = result;
        return result;
    }

    /**
     * Gets bizCode
     *
     * @return the bizCode
     */
    public String getBizCode() {
        return bizCode;
    }

    /**
     * Gets bizInstanceId
     *
     * @return the bizInstanceId
     */
    public ID getBizInstanceId() {
        return bizInstanceId;
    }

}
