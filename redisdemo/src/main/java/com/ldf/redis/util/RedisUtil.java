package com.ldf.redis.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.Set;

/**
 * Created by ldf on 2018/6/26.
 */
public class RedisUtil {

    public static final String KEY_PREFIX = "redisdemo";

    public static Jedis getJedis(String ip, int port){
        return new Jedis(ip, port);
    }

    /**
     * 生成缓存的key值
     * @param className
     * @param methodName
     * @param args
     * @return
     */
    public static String generateKey(String className, String methodName, Object... args){
        StringBuilder sb = new StringBuilder();
        sb.append(className);
        sb.append("_"  + methodName);
        for(Object o1 : args){
            sb.append("_" + o1);
        }
        return sb.toString();
    }

    public static void addCache(String key, Object obj, RedisTemplate redisTemplate){
        ValueOperations operations = redisTemplate.opsForValue();
        operations.set(key, obj);
    }

    public static void removeCache(String key, RedisTemplate template){
        template.delete(key);
    }

    public static void removeCacheBatch(Collections keys, RedisTemplate template){
        template.delete(keys);
    }

    /**
     * 打印redis中的全部key值
     * @param jedis
     */
    public static Set getKeys(Jedis jedis, String expre){
        Set<String> keys;
        try{
            // 获取数据并输出
            keys = jedis.keys(expre);
        }finally {
            jedis.close();
        }
        return keys;
    }

    /**
     * 获取数据
     * @param jedis
     * @param key
     */
    public static String getStringValue(Jedis jedis, String key){
        String value;
        try{
            value = jedis.get(key);
        }finally {
            jedis.close();
        }
        return value;
    }

    /**
     * 获取数据
     * @param template
     * @param key
     */
    public static Object getValue(RedisTemplate template, String key){
        ValueOperations operations = template.opsForValue();
        return operations.get(key);
    }



}
