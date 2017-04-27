--创建数据库
CREATE DATABASE seckill;

USE seckill;
--创建秒杀库存表
CREATE TABLE seckill(
seckill_id bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
name VARCHAR (20) NOT NULL COMMENT '商品名称',
number INT NOT NULL COMMENT '库存数量',
start_time TIMESTAMP NOT NULL COMMENT '秒杀开启时间',
end_time TIMESTAMP  NOT NULL  COMMENT '秒杀结束时间',
create_time TIMESTAMP NOT NULL COMMENT '创建时间',
PRIMARY KEY (seckill_id),
KEY idx_start_time (start_time),
KEY idx_end_time (end_time),
KEY idx_create_time (create_time)
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

--插入库存数据
INSERT INTO seckill(name, number, start_time, end_time, create_time)
VALUES ('50元抢小米2手环', 50, '2017-04-29 10:00:00', '2017-03-30 10:00:00', CURRENT_TIMESTAMP ),
        ('1000元抢小米5s', 50, '2017-03-29 10:00:00', '2017-04-30 10:00:00', CURRENT_TIMESTAMP),
        ('1元抢衬衫', 50, '2017-04-27 20:00:00', '2017-04-30 10:00:00', CURRENT_TIMESTAMP),
        ('11元抢牛仔裤', 50, '2017-04-27 10:00:00', '2017-04-30 10:00:00', CURRENT_TIMESTAMP),
        ('111元抢小米体重计', 50, '2017-03-29 10:00:00', '2017-03-30 10:00:00', CURRENT_TIMESTAMP);

--创建秒杀成功明细
CREATE TABLE success_killed(
seckill_id bigint NOT NULL COMMENT '秒杀商品id',
user_phone bigint NOT NULL COMMENT '用户手机号',
state tinyint NOT NULL DEFAULT -1 COMMENT '状态:-1，无效；0：成功；1：已付款',
create_time TIMESTAMP NOT NULL COMMENT '创建时间',
PRIMARY KEY (seckill_id, user_phone),/*联合主键*/
KEY idx_create_time (create_time)
)ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细';

mysql -uroot -proot