package group.purr.purrbackend.controller.config;

import group.purr.purrbackend.controller.interceptor.SecurityInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public SecurityInterceptor myInterceptor(){
        return new SecurityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.debug("before registry");
        registry.addInterceptor(myInterceptor()).addPathPatterns("/api/**").excludePathPatterns("/api/uninstall", "/api/install", "/api/login");
        log.debug("after registry");
    }
}
