package org.seckill.web;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.dto.SeckillResult;
import org.seckill.entities.Seckill;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by yp on 2017/4/25.
 */
@Controller //@Service @Component
@RequestMapping("/seckill") //url:模块/资源/{id}/细分
public class SeckillController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SeckillService seckillService;
    //name 和 value的区别
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model){
        //System.out.println("*******flag************");
        List<Seckill> seckills = seckillService.getSeckillList();
        model.addAttribute("list", seckills);

        return "seckillList";//  /WEB-INF/jsp/list.jsp
    }

    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model){
        if(seckillId == null){
            //重定向回到列表页
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        if (seckill == null){
            return "forward:/seckill/list";
        }
        //System.out.println("detail:"+seckillId);
        model.addAttribute("seckill", seckill);
        return "detail";
    }

    // ajax json
    @RequestMapping(value = "/{seckillId}/exposer",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId){
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExcution> excute(@PathVariable("seckillId") Long seckillId,
                                                 @PathVariable("md5")String md5,
                                                 //required为false表示cookie不含killphone可以通过、不报错，让下面的程序逻辑进行处理
                                                 @CookieValue(value = "killPhone", required = false) Long phone){
        //springMVC valid 当验证参数很多时才采取

        if(phone == null){
            return new SeckillResult<SeckillExcution>(false, "未注册");
        }
        SeckillResult<SeckillExcution> result = null;
        SeckillExcution excution = null;
        try {
            excution = seckillService.excuteSeckill(seckillId, phone, md5);
            result =  new SeckillResult<SeckillExcution>(true, excution);
        }
        catch (RepeatKillException e1){
            //一下的Success设置为true是为了在浏览器正常响应输出excution带的信息
            excution = new SeckillExcution(seckillId, SeckillStateEnum.REPEAT_KILL);
            result = new SeckillResult<SeckillExcution>(true, excution);
            //System.out.println("RepeatKillException");
        }
        catch (SeckillCloseException e2){
            excution = new SeckillExcution(seckillId, SeckillStateEnum.END);
            result = new SeckillResult<SeckillExcution>(true, excution);
            //System.out.println("SeckillCloseException");
        }
        catch (SeckillException e) {
            logger.error(e.getMessage());
            excution = new SeckillExcution(seckillId, SeckillStateEnum.INNER_ERROR);
            result = new SeckillResult<SeckillExcution>(true, excution);
            //System.out.println("SeckillException");
        }
        finally {
            return result;
        }
    }

    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){
        Date now = new Date();
        return new SeckillResult<Long>(true, now.getTime());
    }

}
