package org.betelnut.modules.metrics.reporter;

import org.betelnut.modules.metrics.Counter;
import org.betelnut.modules.metrics.Histogram;
import org.betelnut.modules.metrics.Timer;

import java.util.Map;

/**
 * Reporter的公共接口，被ReportScheduler定时调用。
 *
 */
public interface Reporter {
	void report(Map<String, Counter> counters, Map<String, Histogram> histograms, Map<String, Timer> timers);
}
