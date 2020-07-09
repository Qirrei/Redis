package com.redis.test;

import redis.clients.jedis.Jedis;

/**
 * @author Qirrei
 * @version 1.0
 * @date 2020/7/8 19:53
 */
public class RedisMainPing {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("172.23.5.156",6379);
        System.out.println(jedis.ping());
    }
}
