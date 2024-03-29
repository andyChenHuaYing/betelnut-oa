package org.betelnut.examples.showcase.demos.redis.elector;

import org.betelnut.examples.showcase.demos.redis.JedisPoolFactory;
import org.betelnut.modules.nosql.redis.JedisUtils;
import org.betelnut.modules.nosql.redis.elector.MasterElector;
import redis.clients.jedis.JedisPool;

public class MasterElectorDemo {

	public static void main(String[] args) throws Exception {

		JedisPool pool = JedisPoolFactory.createJedisPool(JedisUtils.DEFAULT_HOST, JedisUtils.DEFAULT_PORT,
				JedisUtils.DEFAULT_TIMEOUT, 1);
		try {
			MasterElector masterElector = new MasterElector(pool, 5);

			masterElector.start();

			System.out.println("Hit enter to stop.");
			while (true) {
				char c = (char) System.in.read();
				if (c == '\n') {
					System.out.println("Shuting down");
					masterElector.stop();
					return;
				}
			}
		} finally {
			pool.destroy();
		}
	}

}
