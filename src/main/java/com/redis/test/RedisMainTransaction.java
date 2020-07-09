package com.redis.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * @author Qirrei
 * @version 1.0
 * @date 2020/7/9 11:54
 */
public class RedisMainTransaction {
    public static boolean transMethod() throws InterruptedException {
        Jedis jedis = new Jedis("172.23.5.156",6379);
        jedis.select(2);
        int balance; // 可用余额
        int debt; // 欠额
        int amtToSubtract = 10; // 消费额度

        jedis.watch("balance"); // 监听balance
        Thread.sleep(7000);
        balance = Integer.parseInt(jedis.get("balance"));
        if(balance < amtToSubtract){
            jedis.unwatch();
            System.out.println("余额不足");
            return false;
        }else{
            System.out.println("----------------Transaction");
            Transaction transcation = jedis.multi(); // 开启事务
            transcation.decrBy("balance",amtToSubtract);
            transcation.incrBy("debt",amtToSubtract);
            transcation.exec(); // 提交事务

            balance = Integer.parseInt(jedis.get("balance"));
            debt = Integer.parseInt(jedis.get("debt"));
            System.out.println("可用余额:" + balance);
            System.out.println("欠额:" + debt);
            return true;
        }
    }
    /**
     * 通俗点讲，watch命令就是标记一个键，如果标记了一个键， 在提交事务前如果该键被别人修改过，那事务就会失败，这种情况通常可以在程序中
     * 重新再尝试一次。
     * 首先标记了键balance，然后检查余额是否足够，不足就取消标记，并不做扣减； 足够的话，就启动事务进行更新操作，
     * 如果在此期间键balance被其它人修改， 那在提交事务（执行exec）时就会报错， 程序中通常可以捕获这类错误再重新执行一次，直到成功。
     */
    public static void main(String[] args) throws InterruptedException {
        boolean flag = transMethod();
        System.out.println("事务成功是否:" + flag);
    }
}
