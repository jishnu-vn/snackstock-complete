package com.srm.inventory.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.*;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Lets a deployment use Neon’s single postgresql:// connection string without
 * exposing its separate username or password in source code.
 */
public class NeonConnectionEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {
  @Override
  public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
    String connection = environment.getProperty("NEON_DATABASE_URL");
    if (connection == null || !connection.startsWith("postgresql://")) return;
    try {
      URI uri = URI.create(connection);
      String userInfo = uri.getRawUserInfo();
      if (userInfo == null || !userInfo.contains(":")) return;
      String[] credentials = userInfo.split(":", 2);
      String host = uri.getHost();
      String port = uri.getPort() == -1 ? "5432" : ":" + uri.getPort();
      String query = uri.getRawQuery() == null ? "" : "?" + uri.getRawQuery();
      Map<String, Object> values = new LinkedHashMap<>();
      values.put("spring.datasource.url", "jdbc:postgresql://" + host + port + uri.getRawPath() + query);
      values.put("spring.datasource.username", decode(credentials[0]));
      values.put("spring.datasource.password", decode(credentials[1]));
      environment.getPropertySources().addFirst(new MapPropertySource("neonConnection", values));
    } catch (IllegalArgumentException ignored) {
      // Spring Boot will use its normal datasource settings and show a useful error if they are invalid.
    }
  }

  private String decode(String value) { return URLDecoder.decode(value, StandardCharsets.UTF_8); }
  @Override public int getOrder() { return Ordered.HIGHEST_PRECEDENCE; }
}
