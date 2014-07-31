package org.betelnut.modules.metrics;

public class TimerMetric {
	public CounterMetric counterMetric;
	public HistogramMetric histogramMetric;

	@Override
	public String toString() {
		return "IimerMetric [counterMetric=" + counterMetric + ", histogramMetric=" + histogramMetric + "]";
	}
}
