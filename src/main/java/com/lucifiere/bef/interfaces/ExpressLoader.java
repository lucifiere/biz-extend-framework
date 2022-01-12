package com.lucifiere.bef.interfaces;

/**
 * 表达式加载器
 *
 * @author 沾清
 * @version 1.0
 * @since 1.0 2019年6月3日
 */
public interface ExpressLoader {

    /**
     * 根据表达式名称加载表达式
     *
     * @param expressName 表达式名称
     * @return 表达式内容
     * @throws Exception ex
     */
    String loadExpress(String expressName) throws Exception;

}
