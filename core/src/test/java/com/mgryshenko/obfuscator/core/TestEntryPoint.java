package com.mgryshenko.obfuscator.core;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestEntryPoint {

    private static final String HELLO_WORLD = "Hello World!";

    private static TestAppender appender;
    private static Logger testLogger;

    @BeforeClass
    public static void setUp() {
        appender = new TestAppender();
        testLogger = Logger.getLogger(EntryPoint.class);
        testLogger.setAdditivity(false);
        testLogger.addAppender(appender);
    }

    @Test
    public void testHelloWorld() {
        EntryPoint.main();
        Assert.assertEquals(String.format("\"%s\" was expected as first message: ", HELLO_WORLD), HELLO_WORLD, appender.getMessages().get(0));
    }

    @AfterClass
    public static void tearDown() {
        testLogger.removeAppender(appender);
        testLogger.setAdditivity(true);
    }

    /**
     * Saves logged messages and makes them accessible
     * for further assertions and manipulations
     */
    private static class TestAppender extends AppenderSkeleton {

        protected List<String> messages = new ArrayList<>();

        @Override
        public synchronized void doAppend(LoggingEvent event) {
            messages.add(event.getMessage().toString());
        }

        public List<String> getMessages() {
            return messages;
        }

        @Override
        protected void append(LoggingEvent loggingEvent) {

        }

        @Override
        public void close() {

        }

        @Override
        public boolean requiresLayout() {
            return false;
        }
    }

}
