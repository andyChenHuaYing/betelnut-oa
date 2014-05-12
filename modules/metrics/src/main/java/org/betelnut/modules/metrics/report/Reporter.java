package org.betelnut.modules.metrics.report;

import org.betelnut.modules.metrics.CounterMetric;
import org.betelnut.modules.metrics.ExecutionMetric;
import org.betelnut.modules.metrics.HistogramMetric;

import java.util.Map;

public interface Reporter {
	void report(Map<String, CounterMetric> counters, Map<String, HistogramMetric> histograms,
                Map<String, ExecutionMetric> executions);
}
