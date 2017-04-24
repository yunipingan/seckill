package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.entities.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * 业务接口：站在“使用者”的角度设计接口
 * 三个方面：方法定义粒度、参数、返回类型
 * Created by yp on 2017/4/19.
 */
public interface SeckillService {
    /**
     * 查询所有秒杀的记录
     * @return
     */
    List<Seckill>getSeckillList();

    /**
     * 查询单条秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开始时，输出秒杀接口的URL地址
     * 否则输出系统时间和秒杀时间
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExcution excuteSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException;

}
