package com.wormwood.config;

import com.wormwood.config.i18n.MessageSourceService;
import com.wormwood.result.ResultEnum;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import javax.annotation.PostConstruct;
import java.util.Locale;


@Configuration
public class MessageConfig {

    @PostConstruct
    public void initMessageSourceService(){
        ResultEnum.setMessageSourceService(messageSourceService());
    }

    @Bean
    @ConditionalOnMissingBean
    public LocaleResolver localeResolver() {
        return new FixedLocaleResolver(Locale.CHINA);
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("i18n/messages");
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }

    @Bean
    public MessageSourceService messageSourceService(){
        return new MessageSourceService(messageSource());
    }
}
