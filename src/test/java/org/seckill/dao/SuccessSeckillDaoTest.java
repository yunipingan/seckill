package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entities.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by yp on 2017/4/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-Dao.xml"})
public class SuccessSeckillDaoTest {
    @Resource
    SuccessSeckillDao successSeckillDao;

    @Test
    public void testInsertSuccessKilled(){
        System.out.print(successSeckillDao.insertSuccessKilled(1000L, 15011664514L));
    }

    @Test
    public void testQueryByIdWithSeckill(){
        SuccessKilled successKilled =  successSeckillDao.queryByIdWithSeckill(1000L, 15011664514L);
        System.out.print(successKilled.toString());
    }
}
