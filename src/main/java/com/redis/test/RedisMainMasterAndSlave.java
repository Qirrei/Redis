package com.redis.test;

import redis.clients.jedis.Jedis;

/**
 * @author Qirrei
 * @version 1.0
 * @date 2020/7/9 14:30
 */
public class RedisMainMasterAndSlave {
    public static void main(String[] args) {
        Jedis jedisMaster = new Jedis("172.23.5.156",6380);
        Jedis jedisSlave = new Jedis("172.23.5.156",6381);

        jedisSlave.slaveof("172.23.5.156",6380);
        jedisMaster.set("class","000000");
        String result = jedisSlave.get("class");
        System.out.println(result);
    }
}
