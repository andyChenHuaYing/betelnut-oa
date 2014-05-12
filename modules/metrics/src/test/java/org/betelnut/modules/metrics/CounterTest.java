package org.betelnut.modules.metrics;

import org.betelnut.modules.metrics.utils.Clock;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CounterTest {

	private Clock.MockClock clock = new Clock.MockClock();

	@Before
	public void setup() {
		Counter.clock = clock;
	}

	@Test
	public void normal() {

		Counter counter = new Counter();
		counter.inc(10);
		counter.inc(20);
		counter.inc(30);
		clock.increaseTime(1000);

		CounterMetric metric = counter.calculateMetric();
		assertThat(metric.totalCount).isEqualTo(60);
		assertThat(metric.lastCount).isEqualTo(60);
		assertThat(metric.lastRate).isEqualTo(60);

		counter.inc(20);
		clock.increaseTime(1000);
		metric = counter.calculateMetric();

		assertThat(metric.totalCount).isEqualTo(80);
		assertThat(metric.lastCount).isEqualTo(20);
		assertThat(metric.lastRate).isEqualTo(20);
	}

	@Test
	public void incAndDec() {
		Counter counter = new Counter();

		counter.inc(20);
		counter.inc();
		counter.inc();
		counter.dec(10);
		counter.dec();
		clock.increaseTime(1000);

		CounterMetric metric = counter.calculateMetric();
		assertThat(metric.totalCount).isEqualTo(11);
	}

	@Test
	public void empty() {
		Counter counter = new Counter();
		clock.increaseTime(1000);

		CounterMetric metric = counter.calculateMetric();
		assertThat(metric.totalCount).isEqualTo(0);
		assertThat(metric.lastRate).isEqualTo(0);
	}
}
