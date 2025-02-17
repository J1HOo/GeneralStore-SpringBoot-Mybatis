package edu.store.kh.GeneralStore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**") -> 3000 port 애서 모든 url api 요청을 허용
                registry.addMapping("/api/**") // -> 3000 port 애서 /api/ url api 요청을 허용
//                        .allowedOrigins("http://localhost:3000", "http://localhost:3001") // -> react 서버 3000 ~ 3001 port 애서 SpringBoot 요청을 허용
                        .allowedOrigins("http://localhost:3000") // -> react 서버 3000  port 애서 SpringBoot 요청을 허용
                        .allowCredentials(true)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
}

