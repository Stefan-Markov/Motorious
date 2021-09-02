package projectdefence.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import projectdefence.security.UrlAuthenticationSuccessHandler;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableCaching
@EnableAsync
@EnableScheduling
public class ApplicationConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder encoder() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[20];
        random.nextBytes(salt);

        random.generateSeed(20);

        return new BCryptPasswordEncoder(16, random);
    }
//    @Bean
//    public PasswordEncoder sCryptEncoder() {
//        return new SCryptPasswordEncoder(16384, 8, 1, 32, 64);
//    }

//    @Bean
//    public PasswordEncoder pbkdf2Encoder() {
//
//        return new Pbkdf2PasswordEncoder
//                ("MotoriousSecret", 1000, 64);
//    }


    @Bean
    public InitializingBean initializingBean() {
        return () -> SecurityContextHolder
                .setStrategyName(SecurityContextHolder.MODE_THREADLOCAL);
    }
}
