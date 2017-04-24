package org.seckill.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.entities.Seckill;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yp on 2017/4/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-service.xml", "classpath:spring/spring-Dao.xml"})
public class SeckillServiceImplTest {

    @Autowired
    SeckillService seckillService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("seckillList={}", list);
    }

    @Test
    public void getById() throws Exception {
        long seckillId = 1000L;//md5:f3299ff349a2a38062108775baf59a81
        Seckill seckill = seckillService.getById(seckillId);
        logger.info("seckll={}", seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long seckillId = 1000L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        logger.info("exposer{}", exposer);
    }

    @Test
    public void excuteSeckill() throws Exception {
        long seckillId = 1000L;
        long phone = 15112174514L;
        try {
            String md5 = "f3299ff349a2a38062108775baf59a81";
            SeckillExcution seckillExcution = seckillService.excuteSeckill(seckillId, phone, md5);

        } catch (SeckillException e) {
            logger.error(e.getMessage());
        }
    }

    //集成测试代码完成逻辑：是否开启秒杀的整个过程
    @Test
    public void seckillLogic(){
        long seckillId = 1000L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if(exposer.isExposed()){
            long phone = 15112174514L;
            try {
                String md5 = "f3299ff349a2a38062108775baf59a81";
                SeckillExcution seckillExcution = seckillService.excuteSeckill(seckillId, phone, md5);

            } catch (SeckillException e) {
                logger.error(e.getMessage());
            }
        }
        else {
            //秒杀未开启
            logger.warn("expser{}", exposer);
        }
    }

}