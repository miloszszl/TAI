package com.milosz.tai.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(
            ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/static/**")
                .addResourceLocations("/view/movie_app/build/static/");
        registry.addResourceHandler("/*.js")
                .addResourceLocations("/view/movie_app/build/");
        registry.addResourceHandler("/*.json")
                .addResourceLocations("/view/movie_app/build/");
        registry.addResourceHandler("/*.ico")
                .addResourceLocations("/view/movie_app/build/");
        registry.addResourceHandler("/index.html")
                .addResourceLocations("/view/movie_app/build/index.html");
    }

    private final long MAX_AGE_SECS = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
                .maxAge(MAX_AGE_SECS);
    }
}