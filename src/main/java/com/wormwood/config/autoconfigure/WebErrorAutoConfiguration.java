package com.wormwood.config.autoconfigure;


import com.wormwood.config.autoconfigure.controller.CommonErrorController;
import com.wormwood.response.Response;
import com.wormwood.result.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@ConditionalOnWebApplication
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
@ComponentScan(basePackageClasses = CommonErrorController.class)
public class WebErrorAutoConfiguration {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(
                    RequestAttributes requestAttributes,
                    boolean includeStackTrace) {
                LinkedHashMap errorAttributes = new LinkedHashMap();
                Throwable error = getError(requestAttributes);
                Object message = getAttribute(requestAttributes, "javax.servlet.error.message");
                if (error != null) {
                    logger.error("Exception type:" + error.getClass().getName());
                    logger.error("ERROR---" + error.getLocalizedMessage(), error);
                }
                logger.error("Error message from requestAttributes:" + message);
                Response response = new Response(ResultEnum.FAIL);
                errorAttributes.put("code", response.getCode());
                errorAttributes.put("msg", response.getMsg());
                errorAttributes.put("data", null);
                return errorAttributes;
            }

            private <T> T getAttribute(RequestAttributes requestAttributes, String name) {
                return (T) requestAttributes.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
            }

        };
    }

    @Bean
    @ConditionalOnMissingBean(
            value = {ErrorController.class}
    )
    public ErrorController errorController() {
        return new CommonErrorController();
    }
}
