package org.betelnut.modules.utils;

import org.betelnut.modules.test.category.UnStable;
import org.betelnut.modules.test.log.LogbackListAppender;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Threads Test
 *
 * @author James
 * @version 1.0-SNAPSHOT
 * @since 2014-04-14
 */
@Category(UnStable.class)
public class ThreadsTest {
    @Test
    public void gracefulShutdown() throws InterruptedException {

        Logger logger = LoggerFactory.getLogger("test");
        LogbackListAppender appender = new LogbackListAppender();
        appender.addToLogger("test");

        // time enough to shutdown
        ExecutorService pool = Executors.newSingleThreadExecutor();
        Runnable task = new Task(logger, 500, 0);
        pool.execute(task);
        Threads.gracefulShutdown(pool, 1000, 1000, TimeUnit.MILLISECONDS);
        assertThat(pool.isTerminated()).isTrue();
        assertThat(appender.getFirstLog()).isNull();

        // time not enough to shutdown,call shutdownNow
        appender.clearLogs();
        pool = Executors.newSingleThreadExecutor();
        task = new Task(logger, 1000, 0);
        pool.execute(task);
        Threads.gracefulShutdown(pool, 500, 1000, TimeUnit.MILLISECONDS);
        assertThat(pool.isTerminated()).isTrue();
        assertThat(appender.getFirstLog().getMessage()).isEqualTo("InterruptedException");

        // self thread interrupt while calling gracefulShutdown
        appender.clearLogs();

        final ExecutorService self = Executors.newSingleThreadExecutor();
        task = new Task(logger, 100000, 0);
        self.execute(task);

        final CountDownLatch lock = new CountDownLatch(1);
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                lock.countDown();
                Threads.gracefulShutdown(self, 200000, 200000, TimeUnit.MILLISECONDS);
            }
        });
        thread.start();
        lock.await();
        thread.interrupt();
        Threads.sleep(500);
        assertThat(appender.getFirstLog().getMessage()).isEqualTo("InterruptedException");
    }

    @Test
    public void normalShutdown() throws InterruptedException {

        Logger logger = LoggerFactory.getLogger("test");
        LogbackListAppender appender = new LogbackListAppender();
        appender.addToLogger("test");

        // time not enough to shutdown,write error log.
        appender.clearLogs();
        ExecutorService pool = Executors.newSingleThreadExecutor();
        Runnable task = new Task(logger, 1000, 0);
        pool.execute(task);
        Threads.normalShutdown(pool, 500, TimeUnit.MILLISECONDS);
        assertThat(pool.isTerminated()).isTrue();
        assertThat(appender.getFirstMessage()).isEqualTo("InterruptedException");
    }

    static class Task implements Runnable {
        private final Logger logger;

        private int runTime = 0;

        private final int sleepTime;

        Task(Logger logger, int sleepTime, int runTime) {
            this.logger = logger;
            this.sleepTime = sleepTime;
            this.runTime = runTime;
        }

        @Override
        public void run() {
            System.out.println("start task");
            if (runTime > 0) {
                long start = System.currentTimeMillis();
                while ((System.currentTimeMillis() - start) < runTime) {
                }
            }

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                logger.warn("InterruptedException");
            }
        }
    }
}
