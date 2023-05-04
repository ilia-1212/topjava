package ru.javawebinar.topjava;

import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TimingRules {
    private static final Logger log = LoggerFactory.getLogger("result");
    private static final StringBuilder results = new StringBuilder();
    private static final String DELIMITER = "=============";

    public static final Stopwatch STOPWATCH = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String result = String.format("%s %10d", description.getDisplayName(), TimeUnit.NANOSECONDS.toMicros(nanos));
            results.append(result).append("\n");
            log.info(result + " (ms)\n");
        }
    };

    public static final ExternalResource TOTALY = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            results.setLength(0);
        }

        @Override
        protected void after() {
            log.info("\n" + DELIMITER +
                    "\nTest         Duration, ms" +
                    "\n" + DELIMITER + "\n" + results + DELIMITER + "\n");
        }
    };
}
