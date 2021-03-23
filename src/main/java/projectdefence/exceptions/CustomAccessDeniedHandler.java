package projectdefence.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.logging.FileHandler;
import java.util.logging.LogRecord;


public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);
    private static File file = new File("src/main/java/projectdefence/exceptions/UserDeniedLogs.txt");

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            LOG.info("User '" + authentication.getName() +
                    "' attempted to access the URL: " +
                    request.getRequestURI());

            String log = "User >>> " + authentication.getName() +
                    " <<< attempted to access the URL: " +
                    request.getRequestURI() + "  Datetime: "
                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            try {
                FileWriter myWriter = new FileWriter(file, true);
                myWriter.write(log + "\n");
                myWriter.flush();

            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        response.sendRedirect(request.getContextPath() + "/access-denied");
    }
}
