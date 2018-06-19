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
 * ʹ�����ӳ�
 * 1��Ƭ���ӳأ���̨jedis���������ֲ�ʽ�洢���������������ٶ����⣩
 * 2����Ƭ���Ӵ�
 */
public class TestTwo {
	 private Jedis jedis;//����Ƭ��ͻ�������
	    private JedisPool jedisPool;//����Ƭ���ӳ�
	    private ShardedJedis shardedJedis;//��Ƭ��ͻ�������
	    private ShardedJedisPool shardedJedisPool;//��Ƭ���ӳ�
	    
	    public TestTwo() 
	    { 
	        initialPool(); 
	        initialShardedPool(); 
	        shardedJedis = shardedJedisPool.getResource(); 
	        
	        jedis = jedisPool.getResource(); 
	        jedis.auth("1234");//����
	    } 
	 
	    /**
	     * ��ʼ������Ƭ��
	     */
	    private void initialPool() 
	    { 
	        // �ػ������� 
	        JedisPoolConfig config = new JedisPoolConfig(); 
	        config.setMaxTotal(20); 
	        config.setMaxIdle(5); 
	        config.setMaxWaitMillis(1000l); 
	        config.setTestOnBorrow(false); 
	        
	        jedisPool = new JedisPool(config,"127.0.0.1",6379);
	        

	    }
	    
	    /** 
	     * ��ʼ����Ƭ�� 
	     */ 
	    private void initialShardedPool() 
	    { 
	        // �ػ������� 
	        JedisPoolConfig config = new JedisPoolConfig(); 
	        config.setMaxTotal(20); 
	        config.setMaxIdle(5); 
	        config.setMaxWaitMillis(1000l); 
	        config.setTestOnBorrow(false); 
	        // slave���� 
	        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>(); 
	        JedisShardInfo jedisShardInfo=new JedisShardInfo("127.0.0.1", 6379);//һ�����������Լ�����
	        //JedisShardInfo jedisShardInfo1=new JedisShardInfo("192.168.1.2", 6379);
	        jedisShardInfo.setPassword("1234");
	        //jedisShardInfo1.setPassword("1234");
	        shards.add(jedisShardInfo); 
	        //shards.add(jedisShardInfo1); 

	        // ����� 
	        shardedJedisPool = new ShardedJedisPool(config, shards); 
	    } 
	    @Test
	   public void test(){
	    	System.out.println(shardedJedis.get("myKey"));
	        System.out.println(jedis.get("myKey"));
	   }
}
