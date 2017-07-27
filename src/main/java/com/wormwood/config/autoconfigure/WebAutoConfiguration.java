package com.wormwood.config.autoconfigure;

import com.wormwood.config.BaseRestExceptionHandler;
import com.wormwood.service.ProjectService;
import com.wormwood.service.WechatService;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by Dean on 2016/12/30.
 */
@ConditionalOnWebApplication
@AutoConfigureBefore(WebMvcAutoConfiguration.class)
@Import({BaseRestExceptionHandler.class})
public class WebAutoConfiguration {

    @Bean
    public ProjectService projectService() {
        return new ProjectService();
    }

    @Bean
    public WechatService wechatService() {
        return new WechatService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
