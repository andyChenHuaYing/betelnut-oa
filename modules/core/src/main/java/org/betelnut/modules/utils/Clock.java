package org.betelnut.modules.utils;

import java.util.Date;

/**
 *
 * Date provider, this util class is not for direct get the date value,
 * we use it for test.
 *
 * @author James
 * @version 1.0-SNAPSHOT
 * @since 2014-04-12
 */
public interface Clock {

    /**
     * Default time tool.
     */
    static final Clock DEFAULT = new DefaultClock();

    /**
     * Get current time.
     *
     * @return java.util.Date type current time
     */
    Date getCurrentDate();

    /**
     * Get current time.
     *
     * @return long type time value
     */
    long getCurrentTimeMillis();

    public static class DefaultClock implements Clock {

        @Override
        public Date getCurrentDate() {
            return new Date();
        }

        @Override
        public long getCurrentTimeMillis() {
            return System.currentTimeMillis();
        }
    }

    /**
     * A time provide tools which can be configuration.
     *
     */
    public static class MockClock implements Clock {

        /**
         * now time value
         */
        private long time;

        public MockClock() {
            this(0);
        }

        public MockClock(Date date) {
            this.time = date.getTime();
        }

        public MockClock(long time) {
            this.time = time;
        }

        @Override
        public Date getCurrentDate() {
            return new Date(time);
        }

        @Override
        public long getCurrentTimeMillis() {
            return time;
        }

        /**
         * update date
         *
         * @param newDate new date
         */
        public void update(Date newDate) {
            time = newDate.getTime();
        }

        /**
         * update time
         *
         * @param newTime new time
         */
        public void update(long newTime) {
            this.time = newTime;
        }

        /**
         * increase time
         *
         * @param millis how much time increase
         */
        public void increaseTime(int millis) {
            time += millis;
        }

        /**
         * decrease time
         *
         * @param millis how much time decrease
         */
        public void decreaseTime(int millis) {
            time -= millis;
        }
    }
}
