package group.purr.purrbackend;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableSwagger2Doc
@SpringBootApplication
public class PurrBackendApplication {

    @PostConstruct
    void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+0"));
    }

    public static void main(String[] args) {
        SpringApplication.run(PurrBackendApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
