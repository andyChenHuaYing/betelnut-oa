package org.betelnut.examples.showcase.demos.redis.job.dispatcher;

import org.betelnut.examples.showcase.demos.redis.JedisPoolFactory;
import org.betelnut.modules.nosql.redis.JedisUtils;
import org.betelnut.modules.nosql.redis.scheduler.JobDispatcher;
import org.betelnut.modules.nosql.redis.scheduler.JobStatistics;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 运行JobDispatcher，每秒将Job从"job:ss:scheduled" sorted set 发布到"job:ss:ready" list.
 * 如果有任务已被领取而长期没有被执行，会从"job:ss:locked" sorted set取回并重新发布到"job:ss:ready" list.
 */
public class ReliableJobDispatcherDemo {
	public static final int EXPECT_TPS = 5000;
	public static final int DELAY_SECONDS = 10;

	private static ScheduledFuture statisticsTask;

	public static void main(String[] args) throws Exception {

		JedisPool pool = JedisPoolFactory.createJedisPool(JedisUtils.DEFAULT_HOST, JedisUtils.DEFAULT_PORT,
				JedisUtils.DEFAULT_TIMEOUT, 1);
		try {
			JobDispatcher dispatcher = new JobDispatcher("ss", pool);
			dispatcher.setReliable(true);

			JobStatistics statistics = new JobStatistics("ss", pool);

			startPrintStatistics(statistics);
			dispatcher.start();

			System.out.println("Hit enter to stop.");
			while (true) {
				char c = (char) System.in.read();
				if (c == '\n') {
					System.out.println("Shuting down");
					dispatcher.stop();
					stopPrintStatistics();
					return;
				}
			}
		} finally {
			pool.destroy();
		}
	}

	public static void startPrintStatistics(final JobStatistics statistics) {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		statisticsTask = scheduler.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				System.out.printf(
						"Scheduled job %d, Ready Job %d, Lock Job %d, Dispatch Counter %d, Retry Counter %d %n",
						statistics.getScheduledJobNumber(), statistics.getReadyJobNumber(),
						statistics.getLockJobNumber(), statistics.getDispatchCounter(), statistics.getRetryCounter());
			}
		}, 0, 10, TimeUnit.SECONDS);
	}

	public static void stopPrintStatistics() {
		statisticsTask.cancel(true);
	}
}
