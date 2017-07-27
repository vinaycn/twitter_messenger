package org.twitter.messenger.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyConfiguration {

	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                //registry.addMapping("/**");
                registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080","https://twitter-ui.herokuapp.com/")
                .allowedMethods("PUT", "DELETE","GET","POST","OPTIONS")
                .allowedHeaders("Content-Type", "Access-Control-Allow-Origin", "Access-Control-Allow-Headers", "Authorization", "X-Requested-With", "requestId", "Correlation-Id")
                .exposedHeaders("Access-Control-Allow-Origin")
                .allowCredentials(false).maxAge(3600);
            }
        };
    }
}