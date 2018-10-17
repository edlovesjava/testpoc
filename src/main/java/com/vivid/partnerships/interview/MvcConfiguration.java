package com.vivid.partnerships.interview;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/"  };

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/static/js/**")) {
            registry.addResourceHandler("/static/js/**").addResourceLocations(
                    "classpath:/static/build/static/js/");
        }

        if (!registry.hasMappingForPattern("/static/css/**")) {
            registry.addResourceHandler("/static/css/**").addResourceLocations(
                    "classpath:/static/build/static/css/");
        }
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/events").allowedOrigins("http://localhost:3000");
    }

}
