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
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.statics.Urls;

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
                .antMatchers(Urls.RESOURCES_FOLDER_ACCESS_ALL,
                        Urls.STATIC_FOLDER_ACCESS_ALL,
                        Urls.WEBJARS_FOLDER_ACCESS_ALL,
                        Urls.CSS_FOLDER_ACCESS_ALL,
                        Urls.JS_FOLDER_ACCESS_ALL,
                        Urls.IMAGES_FOLDER_ACCESS_ALL,
                        Urls.TEMPLATES_FOLDER_ACCESS_ALL,
                        Urls.HOME_PAGE,
                        Urls.REGISTRATION_PAGE,
                        Urls.LOGIN_PAGE).permitAll()
                .antMatchers(Urls.JOBS_LIST_PAGE_ACCESS_ALL, Urls.JOBS_PAGE_ACCESS_ALL).authenticated()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                .formLogin()
                .loginPage(Urls.LOGIN_PAGE)
                .failureUrl("/login?error=true")
                .defaultSuccessUrl(Urls.HOME_PAGE, true)
                .usernameParameter("user_name")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher(Urls.LOGOUT_PAGE))
                .logoutSuccessUrl(Urls.HOME_PAGE).and().exceptionHandling();
    }
}