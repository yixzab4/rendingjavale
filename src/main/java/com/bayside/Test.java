package com.bayside;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import com.bayside.app.util.RedisUtil;

import redis.clients.jedis.ShardedJedis;

public class Test {
public static void main(String[] args) {
	ShardedJedis shardedJedis = RedisUtil.initialShardedPool("59.110.15.12",6111,14,"bayside801");
	Date start = new Date();
	Map<String, String> map = shardedJedis.hgetAll("0100:getSimArticle");
	for (Entry<String, String> entry : map.entrySet()) {
		System.out.println(entry.getKey());
	}
	shardedJedis.disconnect();
	Date end = new Date();
	
	System.out.println(start+"'t"+end);
}
}
