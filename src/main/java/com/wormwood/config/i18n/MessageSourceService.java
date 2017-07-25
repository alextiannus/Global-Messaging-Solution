package com.wormwood.config.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;


public class MessageSourceService {

    private MessageSource messageSource;

    public MessageSourceService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * @param code ：对应messages配置的key.
     * @return
     */
    public String getMessage(String code) {
        return getMessage(code, "");
    }

    /**
     * 带默认值参数
     *
     * @param code
     * @param defaultMessage
     * @return
     */
    public String getMessage(String code, String defaultMessage) {
        return getMessage(code, null, defaultMessage);
    }

    /**
     * @param code ：对应messages配置的key.
     * @param args : 数组参数.
     * @return
     */
    public String getMessage(String code, Object[] args) {
        return getMessage(code, args, "");
    }


    /**
     * @param code           ：对应messages配置的key.
     * @param args           : 数组参数.
     * @param defaultMessage : 没有设置key的时候的默认值.
     * @return
     */
    public String getMessage(String code, Object[] args, String defaultMessage) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }
}
