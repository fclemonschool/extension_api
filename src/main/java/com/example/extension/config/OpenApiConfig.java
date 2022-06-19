package com.example.extension.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger Open Api 설정.
 */
@Configuration
public class OpenApiConfig {

  /**
   * Swagger UI에 표시되는 타이틀, 버전 및 설명 설정.
   *
   * @return OpenAPI
   */
  @Bean
  public OpenAPI springOpenApi() {
    return new OpenAPI().info(
        new Info().title("Extension API").description("확장자 API 명세서입니다.").version("v0.0.1"));
  }
}
