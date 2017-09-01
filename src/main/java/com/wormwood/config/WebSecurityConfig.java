package com.wormwood.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

/**
 * Created by kasimodo on 2016-07-27.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/css/**","/images/**","/js/**","/AUTH/**","/test/**","/auth/**","/wechat/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .csrf().disable()
                .formLogin()
                .loginPage("/login").defaultSuccessUrl("/index/m", true).permitAll()
                    .usernameParameter("ssoId").passwordParameter("ssoPassword")//.successHandler(new LoginSuccessHandler())
                    .permitAll()
                    .and()
                .logout()
                    .permitAll().logoutUrl("/logout").logoutSuccessUrl("/login")
//                    .logoutSuccessHandler(logoutSuccessHandler)
//                    .invalidateHttpSession(true).addLogoutHandler(logoutHandler)
                .deleteCookies()
                    .and()
                .rememberMe();
    }

    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        DataSourceBuilder builder = new DataSourceBuilder(this.getClass().getClassLoader());
        auth.jdbcAuthentication().dataSource(dataSource())//.passwordEncoder()
                .authoritiesByUsernameQuery("select username, 1 from user where username=?")//全部用户avilable
                .usersByUsernameQuery("select username, password, 1 from user where username=?");//全部有权限
    }



}