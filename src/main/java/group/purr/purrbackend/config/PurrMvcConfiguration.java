package group.purr.purrbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.nio.file.FileSystems;

@Configuration
public class PurrMvcConfiguration implements WebMvcConfigurer {

    private final Environment env;
    private static final String FILE_PROTOCOL = "file:///";
    private static final String WORK_DIR = "/usr/share/nginx/html";
    private static final String FILE_SEPARATOR = File.separator;

    public PurrMvcConfiguration(Environment env) {
        this.env = env;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String mediaPath = env.getProperty("purr.media.path");
        assert mediaPath != null;
        String mediaDir = FileSystems.getDefault().getPath(mediaPath).normalize().toAbsolutePath() + FILE_SEPARATOR;
        String workDir = FileSystems.getDefault().getPath(WORK_DIR).normalize().toAbsolutePath() + FILE_SEPARATOR;

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/public/")
                .addResourceLocations(FILE_PROTOCOL + workDir);

        registry.addResourceHandler("/media/**")
                .addResourceLocations(FILE_PROTOCOL + mediaDir);

        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

}
