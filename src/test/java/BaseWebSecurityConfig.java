///**
// * Copyright (c) 2016-2016 All Rights Reserved.
// */
//
//import org.apache.commons.lang3.ArrayUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.env.Environment;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.Filter;
//
///**
// * security
// *
// * @author ylin
// */
//public class BaseWebSecurityConfig extends WebSecurityConfigurerAdapter {
//    public static final String[] PERMIT_PATHS = {
//            "/auth/**", "/swagger-ui.html", "/webjars/**", "/swagger-resources/**", "/v2/api-docs/**"
//    };
//
//    @Autowired
//    protected Environment env;
//
//    @Autowired
//    protected AuthenticationManager authenticationManager;
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    protected Filter authenticationTokenFilter() {
//        JwtAuthenticationTokenFilter filter = new JwtAuthenticationTokenFilter(PERMIT_PATHS);
//        filter.setAuthenticationManager(authenticationManager);
//        return filter;
//    }
//
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//
//                .csrf().disable()
//
//                .exceptionHandling().authenticationEntryPoint(new BaseAuthenticationEntryPoint()).and()
//
//                // 不创建session
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        for (String url : PERMIT_PATHS) {
//            httpSecurity.authorizeRequests().antMatchers(url).permitAll();
//        }
//
//        httpSecurity.authorizeRequests().anyRequest().authenticated();
//
//        //JWT security filter
//        httpSecurity
//                .addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//        boolean enabled = ArrayUtils.isEmpty(env.getActiveProfiles())
//                || !ArrayUtils.contains(env.getActiveProfiles(), "prod");
//        if (enabled) {
//            httpSecurity.cors();
//        }
//    }
//}