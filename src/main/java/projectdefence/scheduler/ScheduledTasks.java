package projectdefence.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import projectdefence.service.UserService;

import java.io.*;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private final UserService userService;

    public ScheduledTasks(UserService userService) {
        this.userService = userService;

    }

    // at 01:00
    @Scheduled(cron = "* 0 1 * * *")
    @CachePut("users")
    public void updateAllUsers() {
        this.userService.findAllUsers();
        log.info("Create cache of all user at: time is now " + dateFormat.format(new Date()));
    }

    // “At every 59th minute past every 12th hour in every 6th month.”
    @Scheduled(cron = "* */59 */12 * */6 *")
    public void emptyRegisterDoc() {
        // short path
        File file =
                new File(String.valueOf(Path.of("src/main/java/projectdefence/event/UserRegisterLog.txt")));
        boolean delete = file.delete();
        if (delete) {
            log.info("Successful cleared file: UserRegisterLog.txt. at " + dateFormat.format(new Date()));
        }
    }

    // Every night at 23:59
    @Scheduled(cron = "* 59 23 * * *")
    public void emptyDeniedLogs() {
        try {
            FileOutputStream writer = new FileOutputStream("src/main/java/projectdefence/exceptions/UserDeniedLogs.txt");
            writer.write(("").getBytes());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.info("Everything is cool with finally.");
        }
    }
}
