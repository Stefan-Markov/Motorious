package projectdefence.service.impl;

import org.springframework.stereotype.Service;
import projectdefence.event.LogFormatter;
import projectdefence.event.userDeleteEvent.UserDeleteEvent;
import projectdefence.event.userRegisterEvent.UserRegisterEvent;
import projectdefence.service.LoggerService;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class LoggerServiceImpl implements LoggerService {

    @Override
    public void logRegister(UserRegisterEvent applicationEvent) throws IOException {
        Logger logger = Logger.getLogger(applicationEvent.getClass().getName());
        logger.setLevel(Level.FINE);
        logger.addHandler(new FileHandler());

        try {
            Handler fileHandler = new FileHandler("src/main/java/projectdefence/event/UserRegisterLog.txt", true);
//            Handler fileHandler = new FileHandler("src/main/java/projectdefence/event/UserRegisterLog.log", true);

            fileHandler.setFormatter(new LogFormatter());
            logger.addHandler(fileHandler);
            logger.log(Level.INFO, applicationEvent.toString());
            fileHandler.close();
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void logDelete(UserDeleteEvent userDeleteEvent) throws IOException {
        Logger logger = Logger.getLogger(userDeleteEvent.getClass().getName());
        logger.setLevel(Level.FINE);
        logger.addHandler(new FileHandler());
        try {
            Handler fileHandler = new FileHandler("src/main/java/projectdefence/event/UserDeleteLog.txt", true);
//            Handler fileHandler = new FileHandler("src/main/java/projectdefence/event/UserDeleteLog.log", true);

            fileHandler.setFormatter(new LogFormatter());
            logger.addHandler(fileHandler);
            logger.log(Level.INFO, userDeleteEvent.toString());
            fileHandler.close();
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }
}


