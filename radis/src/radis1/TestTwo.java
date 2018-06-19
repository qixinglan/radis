package radis1;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/*
 * 使用连接池
 * 1切片连接池（多台jedis服务器，分布式存储，解决数据量大和速度问题）
 * 2非切片连接处
 */
public class TestTwo {
	 private Jedis jedis;//非切片额客户端连接
	    private JedisPool jedisPool;//非切片连接池
	    private ShardedJedis shardedJedis;//切片额客户端连接
	    private ShardedJedisPool shardedJedisPool;//切片连接池
	    
	    public TestTwo() 
	    { 
	        initialPool(); 
	        initialShardedPool(); 
	        shardedJedis = shardedJedisPool.getResource(); 
	        
	        jedis = jedisPool.getResource(); 
	        jedis.auth("1234");//密码
	    } 
	 
	    /**
	     * 初始化非切片池
	     */
	    private void initialPool() 
	    { 
	        // 池基本配置 
	        JedisPoolConfig config = new JedisPoolConfig(); 
	        config.setMaxTotal(20); 
	        config.setMaxIdle(5); 
	        config.setMaxWaitMillis(1000l); 
	        config.setTestOnBorrow(false); 
	        
	        jedisPool = new JedisPool(config,"127.0.0.1",6379);
	        

	    }
	    
	    /** 
	     * 初始化切片池 
	     */ 
	    private void initialShardedPool() 
	    { 
	        // 池基本配置 
	        JedisPoolConfig config = new JedisPoolConfig(); 
	        config.setMaxTotal(20); 
	        config.setMaxIdle(5); 
	        config.setMaxWaitMillis(1000l); 
	        config.setTestOnBorrow(false); 
	        // slave链接 
	        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>(); 
	        JedisShardInfo jedisShardInfo=new JedisShardInfo("127.0.0.1", 6379);//一个服务器可以继续加
	        //JedisShardInfo jedisShardInfo1=new JedisShardInfo("192.168.1.2", 6379);
	        jedisShardInfo.setPassword("1234");
	        //jedisShardInfo1.setPassword("1234");
	        shards.add(jedisShardInfo); 
	        //shards.add(jedisShardInfo1); 

	        // 构造池 
	        shardedJedisPool = new ShardedJedisPool(config, shards); 
	    } 
	    @Test
	   public void test(){
	    	System.out.println(shardedJedis.get("myKey"));
	        System.out.println(jedis.get("myKey"));
	   }
}
