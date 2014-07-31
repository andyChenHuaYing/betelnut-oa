package org.betelnut.modules.metrics.exporter;

import org.betelnut.modules.metrics.Counter;
import org.betelnut.modules.metrics.Histogram;
import org.betelnut.modules.metrics.Timer;

public interface MetricRegistryListener {

	void onCounterAdded(String name, Counter counter);

	void onHistogramAdded(String name, Histogram histogram);

	void onTimerAdded(String name, Timer timer);
}
