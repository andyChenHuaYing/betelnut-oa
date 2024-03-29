package org.betelnut.examples.showcase.demos.redis;

import org.betelnut.modules.mapper.JsonMapper;
import org.betelnut.modules.nosql.redis.JedisTemplate;
import org.betelnut.modules.nosql.redis.JedisTemplate.JedisActionNoResult;
import org.betelnut.modules.nosql.redis.JedisUtils;
import org.betelnut.modules.test.benchmark.BenchmarkTask;
import org.betelnut.modules.test.benchmark.ConcurrentBenchmark;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.security.SecureRandom;

/**
 * 测试Redis用于Session管理的setEx()与get()方法性能, 使用JSON格式存储数据.
 * 
 * 可用系统参数重置变量改变测试规模与连接参数， @see RedisCounterBenchmark
 */
public class RedisSessionBenchmark extends ConcurrentBenchmark {
	private static final int DEFAULT_THREAD_COUNT = 20;
	private static final long DEFAULT_TOTAL_COUNT = 100000;

	private String keyPrefix = "ss.session:";
	private JsonMapper jsonMapper = new JsonMapper();
	private JedisPool pool;
	private JedisTemplate jedisTemplate;

	public static void main(String[] args) throws Exception {
		RedisSessionBenchmark benchmark = new RedisSessionBenchmark();
		benchmark.execute();
	}

	public RedisSessionBenchmark() {
		super(DEFAULT_THREAD_COUNT, DEFAULT_TOTAL_COUNT);
	}

	@Override
	protected void setUp() {
		pool = JedisPoolFactory.createJedisPool(JedisUtils.DEFAULT_HOST, JedisUtils.DEFAULT_PORT,
				JedisUtils.DEFAULT_TIMEOUT, threadCount);
		jedisTemplate = new JedisTemplate(pool);

		// 清空数据库
		jedisTemplate.flushDB();
	}

	@Override
	protected void tearDown() {
		pool.destroy();
	}

	@Override
	protected BenchmarkTask createTask() {
		return new SessionTask();
	}

	public class SessionTask extends BenchmarkTask {
		private SecureRandom random = new SecureRandom();

		@Override
		protected void execute(final int requestSequnce) {

			int randomIndex = random.nextInt((int) loopCount);
			final String key = new StringBuilder().append(keyPrefix).append(taskSequence).append(":")
					.append(randomIndex).toString();

			jedisTemplate.execute(new JedisActionNoResult() {
				@Override
				public void action(Jedis jedis) {
					Session session = new Session(key);
					session.setAttrbute("name", key);
					session.setAttrbute("seq", requestSequnce);
					session.setAttrbute("address", "address:" + requestSequnce);
					session.setAttrbute("tel", "tel:" + requestSequnce);

					// 设置session，超时时间为300秒
					jedis.setex(session.getId(), 300, jsonMapper.toJson(session));

					// 再重新从Redis中取出并反序列化
					String sessionBackString = jedis.get(key);
					Session sessionBack = jsonMapper.fromJson(sessionBackString, Session.class);
				}
			});
		}

	}
}
