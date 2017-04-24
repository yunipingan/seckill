package org.seckill.dto;

import org.seckill.entities.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;

/**
 * 封装执行秒杀后返回的结果
 * Created by yp on 2017/4/19.
 */
public class SeckillExcution {

    private long seckillId;

    /**
     * 由于默认json转化的类库对枚举的转化有些问题，
     * 所以这里不直接设置枚举类型为成员变量
     * 秒杀状态
     */
    private int state;

    //状态描述
    private String stateInfo;

    private SuccessKilled successKilled;

    public SeckillExcution(long seckillId, SeckillStateEnum stateEnum, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.successKilled = successKilled;
    }

    public SeckillExcution(long seckillId, SeckillStateEnum stateEnum) {
        this.seckillId = seckillId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }
}
