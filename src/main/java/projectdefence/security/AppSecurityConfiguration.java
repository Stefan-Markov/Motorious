package projectdefence.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import projectdefence.service.impl.MotoriousUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfiguration extends WebSecurityConfigurerAdapter {


    private final MotoriousUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public AppSecurityConfiguration(MotoriousUserDetailsService userDetailsService,
                                    PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/index", "/blog", "/", "/info/**", "/user/registration", "/user/login").permitAll()
//                .antMatchers("/js/**", "/css/**", "/img/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .exceptionHandling().accessDeniedPage("/user/login")
//                .and()
//                .formLogin()
//                .loginPage("/user/login")
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .defaultSuccessUrl("/index")
//                .and()
//                .logout()
//                .logoutSuccessUrl("/index");


        http.
                authorizeRequests().
                // allow access to static resources to anyone
                        requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().
                // allow access to index, user login and registration to anyone
                        antMatchers("/", "/info/**", "/user/login", "/user/registration").permitAll().
                // protect all other pages
                        antMatchers("/**").authenticated().
                and().
                // configure login with HTML form
                        formLogin().
                // our login page will be served by the controller with mapping /users/login
                        loginPage("/user/login").
                // the name of the user name input field in OUR login form is username (optional)
                        usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).
                // the name of the user password input field in OUR login form is password (optional)
                        passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).
                // on login success redirect here
                        defaultSuccessUrl("/home").
                // on login failure redirect here
                        failureForwardUrl("/user/login-error").
                and().
                logout().
                // which endpoint performs logout, e.g. http://localhost:8080/logout (!this should be POST request)
                        logoutUrl("/logout").
                // where to land after logout
                        logoutSuccessUrl("/").
                // remove the session from the server
                        invalidateHttpSession(true).
                // delete the session cookie
                        deleteCookies("JSESSIONID");
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                userDetailsService(userDetailsService).
                passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/vendor/**", "/fonts/**");
    }
}
