package projectdefence;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import projectdefence.web.HomeController;

@SpringBootTest
public class HomeControllerTest {

    @Autowired
    private HomeController controller;

    @Test
    public void homePageTest() {
        assertThat(controller).isNotNull();
    }
}
