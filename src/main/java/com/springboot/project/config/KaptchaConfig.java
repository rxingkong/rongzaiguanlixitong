package com.springboot.project.config;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;

import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.Properties;


@Configuration
public class KaptchaConfig {
    @Bean
    public Producer producer(){
        DefaultKaptcha producer = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty(Constants.KAPTCHA_BORDER,"no");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH,"1");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR,"black");
        Config config = new Config(properties);
        producer.setConfig(config);
        return producer;

    }
}
