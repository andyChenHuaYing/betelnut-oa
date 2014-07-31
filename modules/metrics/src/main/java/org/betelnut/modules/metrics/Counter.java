package org.betelnut.modules.metrics;

import org.betelnut.modules.metrics.utils.Clock;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Counter类型, 主要用于计算TPS，
 * 报告Report间隔时间内的Counter变化值及平均TPS，以及服务从启动到现在的Counter总值和平均TPS.
 */
public class Counter {
	public static Clock clock = Clock.DEFAULT;

	public CounterMetric snapshot = new CounterMetric();

	private AtomicLong counter = new AtomicLong(0);

	private long totalCount = 0L;
	private long startTime;
	private long lastReportTime;

	public Counter() {
		lastReportTime = clock.getCurrentTime();
	}

	public void inc() {
		counter.incrementAndGet();
	}

	public void inc(long n) {
		counter.addAndGet(n);
	}

	public void dec() {
		counter.decrementAndGet();
	}

	public void dec(long n) {
		counter.addAndGet(-n);
	}

	public CounterMetric calculateMetric() {
		long lastCount = counter.getAndSet(0);
		long currentTime = clock.getCurrentTime();

		CounterMetric metric = new CounterMetric();

		totalCount += lastCount;
		long totalElapsed = currentTime - startTime;
		metric.meanRate = (totalCount * 1000) / totalElapsed;

		metric.lastCount = lastCount;
		metric.totalCount = totalCount;

		long elapsed = currentTime - lastReportTime;
		if (elapsed > 0) {
			metric.lastRate = (lastCount * 1000) / elapsed;
		}

		lastReportTime = currentTime;

		snapshot = metric;

		return metric;
	}

	@Override
	public String toString() {
		return "Counter [counter=" + counter + ", totalCount=" + totalCount + ", startTime=" + startTime
				+ ", lastReportTime=" + lastReportTime + "]";
	}
}
