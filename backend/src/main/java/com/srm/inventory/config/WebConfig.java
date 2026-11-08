package com.srm.inventory.config;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Value("${app.cors-origin}") String origin;
  @Value("${app.upload-dir}") String uploadDir;

  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/**").allowedOrigins(origin.split(","))
        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE").allowedHeaders("*");
  }

  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    String location = Path.of(uploadDir).toAbsolutePath().normalize().toUri().toString();
    registry.addResourceHandler("/uploads/**").addResourceLocations(location.endsWith("/") ? location : location + "/");
  }
}
