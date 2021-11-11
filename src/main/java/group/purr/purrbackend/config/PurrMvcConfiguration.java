package group.purr.purrbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.FileSystems;

@Configuration
public class PurrMvcConfiguration implements WebMvcConfigurer {

    private final Environment env;

    public PurrMvcConfiguration(Environment env) {
        this.env = env;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String mediaPath = env.getProperty("purr.media.path");
        assert mediaPath != null;
        String workDir = FileSystems.getDefault().getPath(mediaPath).normalize().toAbsolutePath() + "/";

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/public/");

        registry.addResourceHandler("/media/**")
                .addResourceLocations("file://" + workDir);

        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

}
