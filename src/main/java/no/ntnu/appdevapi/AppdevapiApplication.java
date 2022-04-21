package no.ntnu.appdevapi;

import java.util.ArrayList;
import java.util.Collections;
import no.ntnu.appdevapi.DTO.UserDto;
import no.ntnu.appdevapi.entities.PermissionLevel;
import no.ntnu.appdevapi.services.PermissionLevelService;
import no.ntnu.appdevapi.services.UserService;
import org.springframework.boot.CommandLineRunner;
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

  /**
   * Describe the API (for Swagger)
   * @return API description
   */
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





  @Bean
  CommandLineRunner run(UserService userService, PermissionLevelService permissionLevelService) {
    return args -> {
      try {
        permissionLevelService.savePermissionLevel(new PermissionLevel(1, "user", 1, null));
        permissionLevelService.savePermissionLevel(new PermissionLevel(2, "admin", 2, null));
        permissionLevelService.savePermissionLevel(new PermissionLevel(3, "owner", 3, null));
      } catch (Exception e) {
      }
      userService.save(new UserDto("Geir", "Otlo", "geo@geo.geo", "1234"));
      userService.save(new UserDto("Per", "Person",  "Per@Person.geo", "1234"));
      userService.save(new UserDto("user", "user",  "user", "1234"));
      userService.save(new UserDto("admin", "admin",  "admin",  "1234", "admin"));
      userService.save(new UserDto("owner", "owner", "owner", "1234","owner"));


    };
  }
}
