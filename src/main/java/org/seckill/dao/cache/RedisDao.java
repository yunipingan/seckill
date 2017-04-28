package org.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.seckill.entities.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by yp on 2017/4/27.
 */
public class RedisDao {

    private JedisPool jedisPool;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public RedisDao(JedisPoolConfig jedisPoolConfig, String ip, int port, String password){

        jedisPool = new JedisPool(jedisPoolConfig, ip, port, 10000, password);

    }

    public RedisDao(JedisPoolConfig jedisPoolConfig, String ip, int port){

        jedisPool = new JedisPool(jedisPoolConfig, ip, port);

    }

    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    public Seckill getSeckill(long seckillId){
        //redis操作逻辑
        Jedis jedis = null;
        Seckill result = null;
        try {
            jedis = jedisPool.getResource();
            String key = "seckill:" + seckillId;
            //并没有实习内部序列化操作
            //get->byte[] ->反序列化 -> Object(Seckill)
            //采用自定义序列化
            byte[] bytes = jedis.get(key.getBytes());
            //缓存获取到
            if(bytes != null){
                //空对象，用来保存序列化后的对象
                Seckill seckill = schema.newMessage();
                ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
                result = seckill;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            if(jedis != null){
                jedis.close();
                jedis = null;
            }
            return result;
        }
    }

    public String putSeckill(Seckill seckill){
        //set Object(Seckill) ->序列化-> byte[]
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String key = "seckill:" + seckill.getSeckillId();
            byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,
                    //缓存器
                    LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
            //超时缓存
            int timeout = 60 * 60;//单位s
            String result = jedis.setex(key.getBytes(), timeout, bytes);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(jedis != null){
                jedis.close();
                jedis = null;
            }
        }
        return null;
    }

}
