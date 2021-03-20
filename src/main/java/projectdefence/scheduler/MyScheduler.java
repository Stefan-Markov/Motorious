package projectdefence.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import projectdefence.service.UserService;

import java.awt.print.Pageable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class MyScheduler {

    private static final Logger log = LoggerFactory.getLogger(MyScheduler.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private final UserService userService;

    public MyScheduler(UserService userService) {
        this.userService = userService;

    }

    @Scheduled(cron = "10 * * * * *")
    public void onSeconds() {
        System.out.println("Tic tac");
    }

    @Scheduled(fixedRate = 1000000)
    public void reportCurrentTime() {
        log.info("The time is now " + dateFormat.format(new Date()));
    }

    @Scheduled(cron = "30 * * * * *")
    @CachePut("users")
    public void updateAllUsers() {

        this.userService.findAllUsers();
        log.info("Create cache of all user at: time is now " + dateFormat.format(new Date()));
    }
}
