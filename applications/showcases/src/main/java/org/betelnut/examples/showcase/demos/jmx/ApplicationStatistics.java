package org.betelnut.examples.showcase.demos.jmx;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 应用的运行情况统计bean, 通过JMX可被外部监控系统获取数据.
 */
@ManagedResource(objectName = ApplicationStatistics.MBEAN_NAME, description = "Application Statistics Management Bean")
public class ApplicationStatistics {

	public static final String MBEAN_NAME = "showcase:name=ApplicationStatistics";

	private final AtomicInteger listUserTimes = new AtomicInteger();
	private final AtomicInteger updateUserTimes = new AtomicInteger();

	public void incrListUserTimes() {
		listUserTimes.incrementAndGet();
	}

	public void incrUpdateUserTimes() {
		updateUserTimes.incrementAndGet();
	}

	@ManagedAttribute(description = "Times of users be listed")
	public int getListUserTimes() {
		return listUserTimes.get();
	}

	@ManagedAttribute(description = "Times of users be updated")
	public int getUpdateUserTimes() {
		return updateUserTimes.get();
	}

	@ManagedOperation(description = "Reset all statistics")
	public void resetStatistics() {
		listUserTimes.set(0);
		updateUserTimes.set(0);
	}
}
