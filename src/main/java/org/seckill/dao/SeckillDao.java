package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entities.Seckill;

import java.util.Date;
import java.util.List;

/**
 * Created by yp on 2017/3/29.
 */
public interface SeckillDao {

    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return 返回减库存成功数
     */
    int reduceNumber(@Param("seckillId")long seckillId, @Param("killTime")Date killTime);

    /**
     * 根据秒杀商品id查询单条记录
     * @param seckillId
     * @return
     */

    Seckill queryById(long seckillId);

    /**
     * 根据偏移量查询秒杀列表
     * @Param("offset")的使用是因為Java語言不能识别参数名称，默认使用arg0,arg1去代表形参名称
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset")int offset, @Param("limit")int limit);
}
