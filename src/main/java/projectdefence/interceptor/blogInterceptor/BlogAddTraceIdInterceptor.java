package projectdefence.interceptor.blogInterceptor;

import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class BlogAddTraceIdInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory
            .getLogger(BlogAddTraceIdInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        MDC.put("trace-id", UUID.randomUUID().toString());

        if (request.getRequestURL().toString().equals("http://localhost:8080/blog/add-blog")) {
            Object id = MDC.get("trace-id");
            logger.info("Request URL::" + request.getRequestURL().toString() + " with id: " + id.toString());
        }
        return true;
    }
}







