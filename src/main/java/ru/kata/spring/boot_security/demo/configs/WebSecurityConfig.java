package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import ru.kata.spring.boot_security.demo.services.MyDetailsService;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SuccessUserHandler successUserHandler;
    private final MyDetailsService myDetailsService;

    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler, MyDetailsService myDetailsService) {
        this.successUserHandler = successUserHandler;
        this.myDetailsService = myDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/").permitAll()
                .antMatchers("/user/").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.DELETE,"api/users/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler(successUserHandler)
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/auth/login");

    }

//    шифрование пароля
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //настройкa секьюрности
@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}