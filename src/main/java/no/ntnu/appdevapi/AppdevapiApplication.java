package no.ntnu.appdevapi;

import java.util.Collections;

import no.ntnu.appdevapi.DTO.ProductDto;
import no.ntnu.appdevapi.DTO.UserDto;
import no.ntnu.appdevapi.entities.*;
import no.ntnu.appdevapi.services.*;
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
  CommandLineRunner run(UserService userService, PermissionLevelService permissionLevelService, ProductService productService, RatingServiceImpl ratingService, WishlistService wishlistService, ShoppingSessionService shoppingSessionService) {
    return args -> {
      try {
        permissionLevelService.savePermissionLevel(new PermissionLevel( "user", null));
        permissionLevelService.savePermissionLevel(new PermissionLevel( "admin", null));
        permissionLevelService.savePermissionLevel(new PermissionLevel( "owner", null));
      } catch (Exception ignored) {
      }

      userService.save(new UserDto("Geir", "Otlo", "geo@geo.geo", "1234", "Vika Terrasse 9", "C/O Espen Otlo", "6010","Ålesund", "Norge", "91887754"));
      userService.save(new UserDto("Per", "Person",  "Per@Person.geo", "1234", "Gamle Blindheimsveg 72a", "6012", "Ålesund", "Norge", "46537894"));
      userService.save(new UserDto("user", "user",  "user", "1234"));
      userService.save(new UserDto("admin", "admin",  "admin",  "1234", "admin"));
      userService.save(new UserDto("owner", "owner", "owner", "1234","owner"));

      productService.addProductFromDto(new ProductDto("Heather tea", "From Norwegian mountains. Gathered carefully before the bees to hold the honey taste. Rich in vitamins. Local produce", "Norwegian mountains", 200, "Tea", "Bags of tea"));
      productService.addProductFromDto(new ProductDto("Linden blossom tea", "Classic Latvian tea. Helps against laziness. Use 100C water (not typical for herbal teas). Gathered in summer 2021", "Classic Latvian tea", 200, "Tea"));
      productService.addProductFromDto(new ProductDto("Sencha 50g", "Japanese green tea. Green leaves. Available in Aug-Sep season only", "Japanese green tea", 100, "Tea"));
      productService.addProductFromDto(new ProductDto("Sencha 500g", "Japanese green tea. Green leaves. Available in Aug-Sep season only", "Japanese green tea", 800, "Tea"));
      productService.addProductFromDto(new ProductDto("Mug", "Classic mug. Made from Brazilian clay. Hot-friendly - comfortable to hold even when the water is hot. Handy handle", "Brazilian clay", 120, "Accessories", "Mug"));


//      ratingService.addRating( new Rating(userService.findAll().get(1), product1, 1, "Awful, waste of money") );
//      ratingService.addRating( new Rating(userService.findAll().get(1),"Per", product2, 5, "Great tea") );
//      ratingService.addRating( new Rating(userService.findAll().get(1), null, product3, 2, "This was weird") );
//      ratingService.addRating( new Rating(userService.findAll().get(1), null, product4, 2, "This was a lot of weird") );
//      ratingService.addRating( new Rating(userService.findAll().get(1), null, product5, 5, "Great big mugs") );
//
//      wishlistService.addWishlist( new Wishlist(userService.findAll().get(0)));
//      wishlistService.addWishlist( new Wishlist(userService.findAll().get(1)));
//      wishlistService.addWishlist( new Wishlist(userService.findAll().get(2)));
//      wishlistService.addWishlist( new Wishlist(userService.findAll().get(3)));
//      wishlistService.addWishlist( new Wishlist(userService.findAll().get(4)));
//
//      shoppingSessionService.addShoppingSession( new ShoppingSession(userService.findAll().get(0)));
//      shoppingSessionService.addShoppingSession( new ShoppingSession(userService.findAll().get(1)));
//      shoppingSessionService.addShoppingSession( new ShoppingSession(userService.findAll().get(2)));
//      shoppingSessionService.addShoppingSession( new ShoppingSession(userService.findAll().get(3)));
//      shoppingSessionService.addShoppingSession( new ShoppingSession(userService.findAll().get(4)));
    };
  }
}
