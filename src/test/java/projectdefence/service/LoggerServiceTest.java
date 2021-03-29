package projectdefence.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import projectdefence.event.userDeleteEvent.UserDeleteEvent;
import projectdefence.event.userRegisterEvent.UserRegisterEvent;
import projectdefence.service.impl.LoggerServiceImpl;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class LoggerServiceTest {

    private LoggerService loggerService;
    private UserRegisterEvent userRegisterEvent;
    private UserDeleteEvent userDeleteEvent;

    @Before
    public void init() {
        userRegisterEvent = new UserRegisterEvent("firstName", "lastName", "username");
        userDeleteEvent = new UserDeleteEvent("firstName", "lastName", "username");
        loggerService = new LoggerServiceImpl();
    }

    @Test
    public void testLogRegister() throws IOException {

        Logger logger = Logger.getLogger(userRegisterEvent.getClass().getName());
        Handler fileHandler = new FileHandler();
        logger.addHandler(fileHandler);
        fileHandler.close();
        loggerService.logRegister(userRegisterEvent);


        Assert.assertNotNull(fileHandler);
        boolean name = logger.getName().contains("userRegisterEvent");
        Assert.assertTrue(name);
        Assert.assertNotNull(userRegisterEvent);
    }

    @Test
    public void testLogDelete() throws IOException {

        Logger logger = Logger.getLogger(userDeleteEvent.getClass().getName());
        Handler fileHandler = new FileHandler();
        logger.addHandler(fileHandler);
        fileHandler.close();
        loggerService.logDelete(userDeleteEvent);


        Assert.assertNotNull(fileHandler);
        boolean name = logger.getName().contains("userDeleteEvent");
        Assert.assertTrue(name);
        Assert.assertNotNull(userDeleteEvent);

    }
}
