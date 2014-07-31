package org.betelnut.examples.showcase.demos.metrics;

import org.betelnut.modules.metrics.exporter.JmxExporter;
import org.betelnut.modules.metrics.reporter.GraphiteReporter;
import org.betelnut.modules.metrics.reporter.ReportScheduler;
import org.betelnut.modules.metrics.reporter.Slf4jReporter;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * 注册多个Reporter
 */
public class MetricsManager {

	private ReportScheduler scheduler;

	private JmxExporter exporter;

	private boolean graphiteEnabled = false;

	@PostConstruct
	public void start() {
		Slf4jReporter slf4jReporter = new Slf4jReporter();
		scheduler = new ReportScheduler(MetricRegistry.INSTANCE, slf4jReporter);

		if (graphiteEnabled) {
			GraphiteReporter graphiteReporter = new GraphiteReporter(new InetSocketAddress("localhost", 2003));
			scheduler.addReporter(graphiteReporter);
		}

		scheduler.start(10, TimeUnit.SECONDS);

		exporter = new JmxExporter("metrics", MetricRegistry.INSTANCE);
	}

	@PreDestroy
	public void stop() {
		scheduler.stop();
	}

	public void setGraphiteEnabled(boolean graphiteEnabled) {
		this.graphiteEnabled = graphiteEnabled;
	}
}
