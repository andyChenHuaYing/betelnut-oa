package org.betelnut.modules.metrics;

import org.betelnut.modules.metrics.utils.Clock;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExecutionTest {

	@Test
	public void normal() {
		Clock.MockClock clock = new Clock.MockClock();
		Execution.clock = clock;
		Counter.clock = clock;
		Execution execution = new Execution(new Double[] { 90d });

		Execution.ExecutionTimer timer = execution.start();
		clock.increaseTime(200);
		timer.stop();

		Execution.ExecutionTimer timer2 = execution.start();
		clock.increaseTime(300);
		timer2.stop();

		ExecutionMetric metric = execution.calculateMetric();

		assertThat(metric.counterMetric.totalCount).isEqualTo(2);
		assertThat(metric.counterMetric.lastRate).isEqualTo(4);

		assertThat(metric.histogramMetric.min).isEqualTo(200);
		assertThat(metric.histogramMetric.mean).isEqualTo(250);
		assertThat(metric.histogramMetric.pcts.get(90d)).isEqualTo(300);
	}
}
