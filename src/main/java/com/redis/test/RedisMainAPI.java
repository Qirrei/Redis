package com.redis.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Qirrei
 * @version 1.0
 * @date 2020/7/9 10:06
 */
public class RedisMainAPI {
    private static final Jedis jedis = new Jedis("172.23.5.156",6379);
    public static void main(String[] args) {
        jedis.select(1);
        getRedisAllString();
        System.out.println("------------------------");
        getRedisList();
        System.out.println("------------------------");
        getRedisHash();
        System.out.println("------------------------");
        getRedisSet();
        System.out.println("------------------------");
        getRedisSortSet();
    }
    public static void getRedisAllString(){
        jedis.set("k1","v1");
        jedis.set("k2","v2");
        jedis.set("k3","v3");
        List<String> redisStringList = jedis.mget("k1", "k2", "k3");
        for (String s : redisStringList) {
            System.out.println(s);
        }
    }
    public static void getRedisList(){
        jedis.rpush("list","1","2","3","4","5");
        List<String> list = jedis.lrange("list", 0, -1);
        for (String s : list) {
            System.out.println(s);
        }
    }
    public static void getRedisHash(){
        Map map = new HashMap<String,Object>();
        map.put("name","z3");
        map.put("age","23");
        map.put("email","8023@qq.com");
        jedis.hmset("hash",map);
        Map<String, String> hash = jedis.hgetAll("hash");
        System.out.println(hash);
    }
    public static void getRedisSet(){
        jedis.sadd("set","5","6","7","8","9");
        Set<String> set = jedis.smembers("set");
        for (String s : set) {
            System.out.println(s);
        }
    }
    public static void getRedisSortSet(){
        Map map = new HashMap<Double,String>();
        map.put("redis",1.0);
        map.put("java",2.0);
        map.put("mysql",2.0);
        map.put("oracle",3.0);
       jedis.zadd("sortSet",map);
        Set<String> sortSet = jedis.zrange("sortSet", 0, -1);
        for (String s : sortSet) {
            System.out.println(s);
        }
    }
}
