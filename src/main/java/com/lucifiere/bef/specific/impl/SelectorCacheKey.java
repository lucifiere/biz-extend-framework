package com.lucifiere.bef.specific.impl;

/**
 * 缓存key
 *
 * @author 沾清
 */
public final class SelectorCacheKey {
    /**
     * The Biz code.
     */
    private String bizCode;
    /**
     * The Ability code.
     */
    private String abilityCode;

    /**
     * 暂存的hashcode，默认为0;
     */
    private int hash;

    /**
     * Instantiates a new Selector cache key.
     */
    private SelectorCacheKey() {
    }

    /**
     * Instantiates a new Selector cache key.
     *
     * @param bizCode     the biz code
     * @param abilityCode the ability code
     */
    public SelectorCacheKey(String bizCode, String abilityCode) {
        this.bizCode = bizCode;
        this.abilityCode = abilityCode;
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
        if (o == null || getClass() != o.getClass()) { return false; }

        SelectorCacheKey that = (SelectorCacheKey)o;

        if (bizCode != null ? !bizCode.equals(that.bizCode) : that.bizCode != null) { return false; }
        return abilityCode != null ? abilityCode.equals(that.abilityCode) : that.abilityCode == null;
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        if (hash == 0) {
            int result = bizCode != null ? bizCode.hashCode() : 0;
            hash = 31 * result + (abilityCode != null ? abilityCode.hashCode() : 0);
        }
        return hash;
    }
}
