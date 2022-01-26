package no.ntnu.appdevapi;

import java.util.Collections;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
public class AppdevapiApplication {

  public static void main(String[] args) {
    SpringApplication.run(AppdevapiApplication.class, args);
  }

  /**
   * Create configuration for Swagger - turn off some Spring-default APIs etc
   *
   * @return Swagger config
   */
  @Bean
  public Docket getSwaggerConfiguration() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("no.ntnu"))
        .build()
        .apiInfo(describeApi());
  }

  private ApiInfo describeApi() {
    return new ApiInfo(
        "AppDev Project app",
        "Web app for the project." +
            "Used in course IDATA2306 Application development and IDATA2301 Web Technologies ",
        "0.2",
        "https://github.com/pr0xity/Urban-Infusion",
        new Contact("Sakarias S", "https://github.com/pr0xity", null),
        null,
        null,
        Collections.emptyList()
    );
  }

}
