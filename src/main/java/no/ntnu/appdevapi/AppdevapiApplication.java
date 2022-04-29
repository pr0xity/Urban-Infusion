package no.ntnu.appdevapi;

import java.util.Collections;
import no.ntnu.appdevapi.DTO.UserDto;
import no.ntnu.appdevapi.entities.PermissionLevel;
import no.ntnu.appdevapi.entities.Product;
import no.ntnu.appdevapi.entities.Rating;
import no.ntnu.appdevapi.services.PermissionLevelService;
import no.ntnu.appdevapi.services.ProductServiceImpl;
import no.ntnu.appdevapi.services.RatingServiceImpl;
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
  CommandLineRunner run(UserService userService, PermissionLevelService permissionLevelService, ProductServiceImpl productService, RatingServiceImpl ratingService) {
    return args -> {
      try {
        permissionLevelService.savePermissionLevel(new PermissionLevel(1, "user", 1, null));
        permissionLevelService.savePermissionLevel(new PermissionLevel(2, "admin", 2, null));
        permissionLevelService.savePermissionLevel(new PermissionLevel(3, "owner", 3, null));
      } catch (Exception ignored) {
      }

      userService.save(new UserDto("Geir", "Otlo", "geo@geo.geo", "1234", "Vika Terrasse 9", "C/O Espen Otlo", "6010","Ålesund", "Norge", "91887754"));
      userService.save(new UserDto("Per", "Person",  "Per@Person.geo", "1234", "Gamle Blindheimsveg 72a", "6012", "Ålesund", "Norge", "46537894"));
      userService.save(new UserDto("user", "user",  "user", "1234"));
      userService.save(new UserDto("admin", "admin",  "admin",  "1234", "admin"));
      userService.save(new UserDto("owner", "owner", "owner", "1234","owner"));

      Product product1 = new Product("Heather tea", "From Norwegian mountains. Gathered carefully before the bees to hold the honey taste. Rich in vitamins. Local produce", "Norwegian mountains", 200, 3, 1);
      Product product2 = new Product("Linden blossom tea", "Classic Latvian tea. Helps against laziness. Use 100C water (not typical for herbal teas). Gathered in summer 2021", "Classic Latvian tea", 200, 2, 2);
      Product product3 = new Product("Sencha 50g", "Japanese green tea. Green leaves. Available in Aug-Sep season only", "Japanese green tea", 100, 1, 3);
      Product product4 = new Product("Sencha 500g", "Japanese green tea. Green leaves. Available in Aug-Sep season only", "Japanese green tea", 800, 1, 4);
      Product product5 = new Product("Mug", "Classic mug. Made from Brazilian clay. Hot-friendly - comfortable to hold even when the water is hot. Handy handle", "Brazilian clay", 120, 4, 5);

      productService.addProduct(product1);
      productService.addProduct(product2);
      productService.addProduct(product3);
      productService.addProduct(product4);
      productService.addProduct(product5);

      ratingService.addRating( new Rating(userService.findAll().get(0), product1, 3, "Not the worst but not the best") );
      ratingService.addRating( new Rating(userService.findAll().get(0), product2, 5, "A taste of heaven") );
      ratingService.addRating( new Rating(userService.findAll().get(0), product3, 4, "Good tea") );
      ratingService.addRating( new Rating(userService.findAll().get(0), product4, 4, "Good tea in larger sizes") );
      ratingService.addRating( new Rating(userService.findAll().get(0), product5, 5, "Nice mugs") );

      ratingService.addRating( new Rating(userService.findAll().get(1), product1, 1, "Awful, waste of money") );
      ratingService.addRating( new Rating(userService.findAll().get(1), product2, 5, "Great tea") );
      ratingService.addRating( new Rating(userService.findAll().get(1), product3, 2, "This was weird") );
      ratingService.addRating( new Rating(userService.findAll().get(1), product4, 2, "This was a lot of weird") );
      ratingService.addRating( new Rating(userService.findAll().get(1), product5, 5, "Great big mugs") );
    };
  }
}
