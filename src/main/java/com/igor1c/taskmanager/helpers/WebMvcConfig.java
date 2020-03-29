package com.igor1c.taskmanager.helpers;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/jquery/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/jquery/3.4.1/");

        registry.addResourceHandler("/popper/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/popper.js/1.14.3/umd/");

        registry.addResourceHandler("/bootstrap/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/bootstrap/4.4.1/");

        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/js/");

        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/css/");

    }

}
