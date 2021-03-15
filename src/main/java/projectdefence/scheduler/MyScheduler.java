package projectdefence.scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import projectdefence.repositories.UserRepository;
import java.text.SimpleDateFormat;
import java.util.Date;
@Component
public class MyScheduler {

    private final UserRepository userRepository;
    private static final Logger log =  LoggerFactory.getLogger(MyScheduler.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public MyScheduler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Scheduled(cron = "*/10 * * * * *")
    public void onEach20Second() {
        System.out.println("Tic tac");
    }

    @Scheduled(fixedRate = 20000)
    public void reportCurrentTime() {
        log.info("The time is now " + dateFormat.format(new Date()));
    }

    @Scheduled(cron = "*/10 * * * * *")
    @CachePut("users")
    public void updateAllUsers() {
        this.userRepository.findAllOrderByDate();
        log.info("Create cache of all user at: time is now " + dateFormat.format(new Date()));
    }

    
}
