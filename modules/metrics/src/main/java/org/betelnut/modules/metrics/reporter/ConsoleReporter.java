package org.betelnut.modules.metrics.reporter;

import org.betelnut.modules.metrics.*;

import java.io.PrintStream;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

public class ConsoleReporter implements Reporter {

	private static final int CONSOLE_WIDTH = 80;
	private PrintStream output = System.out;

	@Override
	public void report(Map<String, Counter> counters, Map<String, Histogram> histograms, Map<String, Timer> timers) {

		printWithBanner(new Date().toString(), '=');
		output.println();

		if (!counters.isEmpty()) {
			printWithBanner("-- Counters", '-');
			for (Entry<String, Counter> entry : counters.entrySet()) {
				output.println(entry.getKey());
				printCounter(entry.getValue().snapshot);
			}
			output.println();
		}

		if (!histograms.isEmpty()) {
			printWithBanner("-- Histograms", '-');
			for (Entry<String, Histogram> entry : histograms.entrySet()) {
				output.println(entry.getKey());
				printHistogram(entry.getValue().snapshot);
			}
			output.println();
		}

		if (!timers.isEmpty()) {
			printWithBanner("-- Timers", '-');
			for (Entry<String, Timer> entry : timers.entrySet()) {
				output.println(entry.getKey());
				printTimer(entry.getValue().snapshot);
			}
			output.println();
		}
	}

	private void printWithBanner(String s, char c) {
		output.print(s);
		output.print(' ');
		for (int i = 0; i < (CONSOLE_WIDTH - s.length() - 1); i++) {
			output.print(c);
		}
		output.println();
	}

	private void printCounter(CounterMetric counter) {
		output.printf("             count = %d%n", counter.totalCount);
		output.printf("         last rate = %2.2f/s%n", counter.lastRate);
		output.printf("         mean rate = %2.2f/s%n", counter.meanRate);
	}

	private void printHistogram(HistogramMetric histogram) {
		output.printf("               min = %d%n", histogram.min);
		output.printf("               max = %d%n", histogram.max);
		output.printf("              mean = %2.2f%n", histogram.mean);
		for (Entry<Double, Long> pct : histogram.pcts.entrySet()) {
			output.printf("           %2.2f%% <= %d %n", pct.getKey(), pct.getValue());
		}
	}

	private void printTimer(TimerMetric timer) {
		output.printf("             count = %d%n", timer.counterMetric.totalCount);
		output.printf("         last rate = %2.2f/s%n", timer.counterMetric.lastRate);
		output.printf("         mean rate = %2.2f/s%n", timer.counterMetric.meanRate);
		output.printf("               min = %d ms%n", timer.histogramMetric.min);
		output.printf("               max = %d ms%n", timer.histogramMetric.max);
		output.printf("              mean = %2.2f ms%n", timer.histogramMetric.mean);
		for (Entry<Double, Long> pct : timer.histogramMetric.pcts.entrySet()) {
			output.printf("           %2.2f%% <= %d ms%n", pct.getKey(), pct.getValue());
		}
	}
}
