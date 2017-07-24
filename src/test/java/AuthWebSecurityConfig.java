///**
// * Copyright (c) 2016-2016 All Rights Reserved.
// */
//
//import com.mobilityexchange.common.security.BaseWebSecurityConfig;
//import com.mobilityexchange.common.security.JwtAuthenticationProvider;
//import com.mobilityexchange.common.security.JwtAuthenticationTokenFilter;
//import com.mobilityexchange.common.security.api.BaseUserService;
//import com.mobilityexchange.common.security.api.TokenService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//import javax.servlet.Filter;
//
///**
// * security
// *
// * @author ylin
// */
//@SuppressWarnings("SpringJavaAutowiringInspection")
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class AuthWebSecurityConfig extends BaseWebSecurityConfig {
//    @Autowired
//    protected BaseUserService baseUserService;
//
//    @Autowired
//    protected TokenService tokenService;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(this.baseUserService)
//                .passwordEncoder(passwordEncoder());
//        auth.authenticationProvider(new JwtAuthenticationProvider(baseUserService, tokenService));
//    }
//
//}