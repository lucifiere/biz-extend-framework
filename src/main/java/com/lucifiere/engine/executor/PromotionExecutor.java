package com.lucifiere.engine.executor;

/**
 * 优惠执行器，基于QL脚本实现
 *
 * @author XD.Wang
 * @version 1.0
 * @since 1.0 2019年6月13日
 */
public interface PromotionExecutor<T> {

    /**
     * 调用QLExpress去执行QL脚本
     *
     * @param request 执行参数
     * @return 每个商品的优惠结果
     */
    PromotionExecResp<T> exec(PromotionExecReq request);

}
