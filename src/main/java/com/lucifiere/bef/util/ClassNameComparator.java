package com.lucifiere.bef.util;

import java.util.Comparator;

/**
 * 类名字符串排序比较器
 *
 * @author 沾清
 */
public class ClassNameComparator implements Comparator<Class<?>> {
    /**
     * Compare int.
     *
     * @param o1 the o 1
     * @param o2 the o 2
     * @return the int
     */
    @Override
    public int compare(Class<?> o1, Class<?> o2) {
        if (o1 == null) { return -1; }
        if (o2 == null) { return 1; }
        return o1.getName().compareToIgnoreCase(o2.getName());
    }
}
