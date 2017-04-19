package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entities.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by yp on 2017/3/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-config.xml"})
public class SeckillDaoTest {
    @Resource
    private SeckillDao seckillDao;
    @Test
    public void testQueryById(){
        System.out.println(seckillDao.queryById(1000));
    }


    @Test
    public void testReduceNumber(){
        seckillDao.reduceNumber(1005L, new Date());
    }

    @Test
    public void testQueryAll(){
        List<Seckill> seckills = seckillDao.queryAll(0, 3);
        for(Seckill seckill : seckills){
            System.out.print(seckill);
        }
    }
}
