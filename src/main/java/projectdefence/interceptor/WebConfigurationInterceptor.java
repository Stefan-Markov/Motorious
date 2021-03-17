package projectdefence.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurationInterceptor implements WebMvcConfigurer {
    private final RequestProcessingTimeInterceptor requestProcessingTimeInterceptor;

    public WebConfigurationInterceptor(RequestProcessingTimeInterceptor requestProcessingTimeInterceptor) {
        this.requestProcessingTimeInterceptor = requestProcessingTimeInterceptor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestProcessingTimeInterceptor);
    }

}
