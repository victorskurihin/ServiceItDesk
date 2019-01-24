package su.svn.utils;

import java.net.MalformedURLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

public class HelloWorldTest
{
    private static Logger LOGGER = null;

    @BeforeClass
    public static void setLogger() throws MalformedURLException
    {
        System.setProperty("log4j.configurationFile", "log42-test.xml");
        LOGGER = LogManager.getLogger();
    }

    @Test
    public void testOne()
    {
        LOGGER.debug("Debug Message Logged !!!");
        LOGGER.info("Info Message Logged !!!");
        LOGGER.error("Error Message Logged !!!", new NullPointerException("NullError"));
    }
}
