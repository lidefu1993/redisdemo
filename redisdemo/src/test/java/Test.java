import com.alibaba.fastjson.JSON;
import com.ldf.redis.App;
import com.ldf.redis.domain.UserInfo;
import com.ldf.redis.mapper.ITestMapper;
import com.ldf.redis.util.RedisUtil;
import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;

/**
 * Created by ldf on 2018/6/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class Test {
    private Logger logger = Logger.getLogger(Test.class);
    @Autowired
    private ITestMapper testMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JedisPool jedisPool;

//    Jedis jedis = RedisUtil.getJedis("192.168.6.128", 6379);
    //获取所有key支持正则
    @org.junit.Test
    public void tesRedis(){
        Jedis jedis = jedisPool.getResource();
        Set keys = RedisUtil.getKeys(jedis, "*");
        logger.debug("----------" + JSON.toJSONString(keys));
    }
    //根据key获取value
    @org.junit.Test
    public void getRedisDataByKey(){
        Object test = RedisUtil.getValue(redisTemplate, "test");
        System.out.println(test.getClass() + " - " + (test instanceof UserInfo) );
        UserInfo userInfo = (UserInfo) test;
        logger.debug("------------------------------");
    }

    //测试缓存
    @org.junit.Test
    public void testCache(){
        List<UserInfo> ldf = testMapper.selectUserList("ldf");
        System.out.println("--------------------" + ldf.size() + " : " + JSON.toJSONString(ldf));
    }
    //更新数据后 找到相关的key 删除
    @org.junit.Test
    public void testUpdateCache(){
        Jedis jedis = jedisPool.getResource();
        UserInfo userInfo = UserInfo.builder()
                .id("1")
                .age(24)
                .build();
        testMapper.updateUser(userInfo);
        Set<String> keys = jedis.keys("*selectUserList*");
        redisTemplate.delete(keys);
    }
    //删除缓存
    @org.junit.Test
    public void delCache(){
        Jedis jedis = jedisPool.getResource();
        Set<String> keys = jedis.keys("*");
        redisTemplate.delete(keys);
    }
    //添加缓存
    @org.junit.Test
    public void addCache(){
        UserInfo userInfo = UserInfo.builder()
                .id("1")
                .age(24)
                .build();
        RedisUtil.addCache("test", userInfo, redisTemplate);
    }

}
