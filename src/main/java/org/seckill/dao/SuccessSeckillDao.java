package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entities.SuccessKilled;

/**
 * Created by yp on 2017/3/29.
 */
public interface SuccessSeckillDao {
    /**
     * 插入秒杀记录
     * @param seckillId
     * @param userPhone
     * @return 返回的是影响函数，返回的是0则表示不成功
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

}
