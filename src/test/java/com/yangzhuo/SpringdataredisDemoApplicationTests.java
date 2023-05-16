package com.yangzhuo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringdataredisDemoApplicationTests {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 操作String类型数据
     */
    @Test
    public void testString() {
        redisTemplate.opsForValue().set("city","beijing");
        Object value = redisTemplate.opsForValue().get("city");
        System.out.println(value);

        redisTemplate.opsForValue().set("key1","value1",10l, TimeUnit.SECONDS);
    }

    /**
     * 操作Hash数据
     */
    @Test
    public void testHash(){
        HashOperations hashOperations = redisTemplate.opsForHash();
        //存储值
        hashOperations.put("002","name","zhangsan");
        hashOperations.put("002","age","20");
        hashOperations.put("002","address","bj");
        //取值
        Object age = hashOperations.get("002", "age");
        System.out.println(age);
        //获取hash中的所有字段
        Set keys = hashOperations.keys("002");
        for (Object key : keys) {
            System.out.println(key);
        }

        //获取hash结构中的所有值
        List values = hashOperations.values("002");
        for (Object value : values) {
            System.out.println(value);
        }
    }

    /*
    操作List类型数据
     */
    @Test
    public void testList(){
        ListOperations listOperations = redisTemplate.opsForList();
        //存储
        listOperations.leftPush("mylist2","a");
        listOperations.leftPushAll("mylist2","b","c","d","e");

        //取值
        List mylist = listOperations.range("mylist2", 0, -1);
        for (Object o : mylist) {
            System.out.println(o);
        }

        //获取列表长度len
        Long size = listOperations.size("mylist2");

        int l = size.intValue();
        for (int i = 0; i < l; i++) {
            //出队列
            Object mylist2 = listOperations.rightPop("mylist2");
            System.out.println(mylist2);
        }
    }

    /**
     * 操作set类型数据
     */
    @Test
    public void testSet(){
        SetOperations setOperations = redisTemplate.opsForSet();
        //存值
        setOperations.add("myset","a","b","a","c");
        //取值
        Set myset = setOperations.members("myset");
        for (Object o : myset) {
            System.out.println(o);
        }
        //删除成员
        setOperations.remove("myset","a","b");
        Set myset2 = setOperations.members("myset");
        for (Object o : myset2) {
            System.out.println(o);
        }
    }

    /**
     * 操作Zset
     */
    @Test
    public void testZset(){
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        //存储
        zSetOperations.add("myZset","a",10.0);
        zSetOperations.add("myZset","b",15.0);
        zSetOperations.add("myZset","c",4.5);
        zSetOperations.add("myZset","d",30.0);
        //取值
        Set myZset = zSetOperations.range("myZset", 0, -1);
        for (Object o : myZset) {
            System.out.println(o);
        }
        //修改分数
        zSetOperations.incrementScore("myZset","c",20.0);

        System.out.println("==========================================");
        Set myZset2 = zSetOperations.range("myZset", 0, -1);
        for (Object o : myZset2) {
            System.out.println(o);
        }
        //删除成员
        zSetOperations.remove("myZset","a","b");
        System.out.println("==========================================");
        Set myZset3 = zSetOperations.range("myZset", 0, -1);
        for (Object o : myZset3) {
            System.out.println(o);
        }
    }

    /**
     * 通用操作
     */
    @Test
    public void testCommon(){
        //获取redis中所有key
        Set keys = redisTemplate.keys("*");
        for (Object key : keys) {
            System.out.println(key);
        }
        //判断某个key是否存在
        Boolean itcast = redisTemplate.hasKey("itcast");
        System.out.println(itcast);
        //删除指定key
        redisTemplate.delete("myZset");
        //获取指定key对应的value的数据类型
        DataType myset = redisTemplate.type("myset");
        System.out.println(myset.name());
    }


}
