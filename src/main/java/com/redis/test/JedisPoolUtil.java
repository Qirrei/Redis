package com.redis.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author Qirrei
 * @version 1.0
 * @date 2020/7/9 17:21
 */
public class JedisPoolUtil {

        private static volatile JedisPool jedisPool = null;// 被volatile修饰的变量不会被本地线程缓存，对该变量的读写都是直接操作共享内存。

        private JedisPoolUtil() {
        }

        public static JedisPool getJedisPoolInstance() {
            if (null == jedisPool) {
                synchronized (JedisPoolUtil.class) {
                    if (null == jedisPool) {
                        JedisPoolConfig poolConfig = new JedisPoolConfig();
                        poolConfig.setMaxTotal(1000);
                        poolConfig.setMaxIdle(32);
                        poolConfig.setMaxWaitMillis(100 * 1000);
                        poolConfig.setTestOnBorrow(true);

                        jedisPool = new JedisPool(poolConfig, "172.23.5.156", 6380);
                    }
                }
            }
            return jedisPool;
        }

        public static void release(JedisPool jedisPool, Jedis jedis) {
            if (null != jedis) {
                Jedis jedis2 = null;
                try {
                    jedis2 = jedisPool.getResource();
                } finally {
                    jedis2.close();
                }
            }
        }

}
