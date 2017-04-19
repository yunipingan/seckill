# seckill
Java高并发秒杀SpringMVC+Spring+MyBatis

DAO层工作演变为：接口设计+SQL编写
代码和SQL的分离，方便review
DAO拼接等逻辑应该放在Service层完成，而我们通常在编码时会犯下在DAO层编写了逻辑代码的错误
