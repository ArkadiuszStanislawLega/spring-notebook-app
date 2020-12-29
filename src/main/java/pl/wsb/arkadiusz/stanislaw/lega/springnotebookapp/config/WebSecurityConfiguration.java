package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.service.MyUserDetailsService;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.stat.Roles;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.stat.url;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired(required = true)
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MyUserDetailsService userDetailsService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.
                authorizeRequests()
                .antMatchers(url.RESOURCES_FOLDER_ACCES_ALL,
                        url.STATIC_FOLDER_ACCESS_ALL,
                        url.WEBJARS_FOLDER_ACCESS_ALL,
                        url.CSS_FOLDER_ACCESS_ALL,
                        url.JS_FOLDER_ACCESS_ALL,
                        url.IMAGES_FOLDER_ACCESS_ALL,
                        url.TEMPLATES_FOLDER_ACCESS_ALL,
                        url.HOME_PAGE,
                        url.REGISTRATION_PAGE,
                        url.LOGIN_PAGE).permitAll()
                .antMatchers(url.JOBS_LIST_PAGE_ACCESS_ALL, url.JOBS_PAGE_ACCESS_ALL).authenticated()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                .formLogin()
                .loginPage(url.LOGIN_PAGE)
                .failureUrl("/login?error=true")
                .defaultSuccessUrl(url.HOME_PAGE, true)
                .usernameParameter("user_name")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher(url.LOGOUT_PAGE))
                .logoutSuccessUrl(url.HOME_PAGE).and().exceptionHandling();
    }
}