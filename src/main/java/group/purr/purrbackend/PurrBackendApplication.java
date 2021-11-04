package group.purr.purrbackend;

import com.spring4all.swagger.EnableSwagger2Doc;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableSwagger2Doc
@SpringBootApplication
@Slf4j
public class PurrBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PurrBackendApplication.class, args);
    }

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+0"));
        log.info("运行了init方法");
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
