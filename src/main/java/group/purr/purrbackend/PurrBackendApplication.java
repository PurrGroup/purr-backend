package group.purr.purrbackend;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@EnableSwagger2Doc
@SpringBootApplication
public class PurrBackendApplication {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(PurrBackendApplication.class, args);
    }

}
