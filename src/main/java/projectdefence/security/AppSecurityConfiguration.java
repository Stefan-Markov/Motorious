package projectdefence.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import projectdefence.exceptions.CustomAccessDeniedHandler;
import projectdefence.security.filters.RequestValidationFilter;
import projectdefence.service.impl.MotoriousUserDetailsService;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
public class AppSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final int _30_DAYS = 84600 * 30;
    private final MotoriousUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final OAuth2UserAuthSuccessHandler oAuth2UserAuthSuccessHandler;


    public AppSecurityConfiguration(MotoriousUserDetailsService userDetailsService,
                                    PasswordEncoder passwordEncoder, OAuth2UserAuthSuccessHandler oAuth2UserAuthSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.oAuth2UserAuthSuccessHandler = oAuth2UserAuthSuccessHandler;
    }


    private static void customizeCors(CorsConfigurer<HttpSecurity> c) {
        CorsConfigurationSource source = request -> {
            // CORS allowed urls
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(
                    List.of("example.com", "example.org"));
            config.setAllowedMethods(
                    List.of("GET"));
            return config;
        };
        c.configurationSource(source);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .permitAll()
                .antMatchers("/", "/blogs/**", "/info/**", "/user/login", "/user/registration")
                .permitAll()
                .antMatchers("/**").authenticated()
                .and()
                .formLogin()
                .loginPage("/user/login")
                .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
//                .defaultSuccessUrl("/home")
                .successHandler(myAuthenticationSuccessHandler())
                .failureForwardUrl("/user/login-error")
                .and()
                .addFilterBefore(new RequestValidationFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and()
                .logout().clearAuthentication(true)
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .rememberMe()
                .key("secretKeyMotorious")
                .rememberMeParameter("remember")
                .tokenValiditySeconds(_30_DAYS)
                .and()
                .oauth2Login()
                .loginPage("/user/login")
                .successHandler(oAuth2UserAuthSuccessHandler);


        http.sessionManagement().maximumSessions(1).expiredUrl("/");

        http.cors(AppSecurityConfiguration::customizeCors);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/vendor/**", "/fonts/**");
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public UrlAuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new UrlAuthenticationSuccessHandler();
    }

//    private ClientRegistration clientRegistration() {
//        return CommonOAuth2Provider.GITHUB.getBuilder("github")
//                .clientId("***")
//                .clientSecret(GITHUB_KEY)
//                .build();
//    }

//    private ClientRegistrationRepository clientRepository() {
//        var c = clientRegistration();
//        return new InMemoryClientRegistrationRepository(c);
//    }
}
