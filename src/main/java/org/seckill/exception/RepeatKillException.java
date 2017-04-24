package org.seckill.exception;

/**
 *重复秒杀异常（运行期异常），Spring只会回滚执行期异常
 * Created by yp on 2017/4/19.
 */
public class RepeatKillException extends SeckillException{

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
