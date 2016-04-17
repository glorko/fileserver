package net.punklan.glorfindeil.fileserver;

import net.punklan.glorfindeil.fileserver.translation.SerializableResourceBundleMessageSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * Created by glorfindeil on 15.04.16.
 */

@SpringBootApplication
@Controller
@Configuration
@ComponentScan("net.punklan.glorfindeil")
public class FileServerApplication {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(FileServerApplication.class, args);

    }
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    @Bean
    public SerializableResourceBundleMessageSource messageSource() {
        SerializableResourceBundleMessageSource messageSource = new SerializableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setCacheSeconds(3600);
        return messageSource;
    }

}
