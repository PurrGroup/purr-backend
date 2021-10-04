package group.purr.purrbackend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesConfig {

    @Bean
    @ConfigurationProperties(prefix = "token")
    public TokenProperties tokenProperties(){
        return new TokenProperties();
    }
}
