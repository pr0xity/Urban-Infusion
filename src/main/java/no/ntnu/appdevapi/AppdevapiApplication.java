package no.ntnu.appdevapi;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Collections;

import no.ntnu.appdevapi.DTO.ProductDto;
import no.ntnu.appdevapi.DTO.UserDto;
import no.ntnu.appdevapi.entities.*;
import no.ntnu.appdevapi.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
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
  CommandLineRunner run(UserService userService, PermissionLevelService permissionLevelService, ProductService productService, ProductImageService productImageService, RatingServiceImpl ratingService, WishlistService wishlistService, ShoppingSessionService shoppingSessionService, OrderDetailsService orderDetailsService, OrderItemService orderItemService) {
    return args -> {
      if (orderItemService.getAllOrderItems().size() < 1) {
        permissionLevelService.savePermissionLevel(new PermissionLevel("user", null));
        permissionLevelService.savePermissionLevel(new PermissionLevel("admin", null));
        permissionLevelService.savePermissionLevel(new PermissionLevel("owner", null));

        userService.save(new UserDto("Arne", "Arnesen", "arne@arnesen.arn", "1234", "Arneveien 13", "C/O Anette Anettessen", "1440", "Bergen", "Norge", "13647980"));
        userService.save(new UserDto("Per", "Person", "per@person.per", "1234", "Gamle Persgate 72a", "6011", "Ålesund", "Norge", "46537894"));
        userService.save(new UserDto("user", "user", "user", "1234"));
        userService.save(new UserDto("admin", "admin", "admin", "1234", "admin"));
        userService.save(new UserDto("owner", "owner", "owner", "1234", "owner"));

        //Saving by user dto sets enable = false so new user's need to verify. Enabling test data here:
        userService.findAll().forEach(user -> {
          user.setEnabled(true);
          userService.saveUserObject(user);
        });

        productService.addProductFromDto(new ProductDto("Heather tea", "From Norwegian mountains. Gathered carefully before the bees to hold the honey taste. Rich in vitamins. Local produce", "Norwegian mountains", 200, "Tea"));
        productService.addProductFromDto(new ProductDto("Linden blossom tea", "Classic Latvian tea. Helps against laziness. Use 100C water (not typical for herbal teas). Gathered in summer 2021", "Classic Latvian tea", 200, "Tea"));
        productService.addProductFromDto(new ProductDto("Sencha 50g", "Japanese green tea. Green leaves. Available in Aug-Sep season only", "Japanese green tea", 100, "Tea"));
        productService.addProductFromDto(new ProductDto("Sencha 500g", "Japanese green tea. Green leaves. Available in Aug-Sep season only", "Japanese green tea", 800, "Tea"));
        productService.addProductFromDto(new ProductDto("Mug", "Classic mug. Made from Brazilian clay. Hot-friendly - comfortable to hold even when the water is hot. Handy handle", "Brazilian clay", 120, "Accessories"));

        //Adding image to products
        int index = 1;
        for (Product product : productService.getAllProducts()) {
          String imagePath = "static/img/products/" + index + ".jpeg";
          URL imageResource = getClass().getClassLoader().getResource(imagePath);
          if (imageResource != null) {
            File image = new File(imageResource.toURI());
            FileInputStream imageStream = new FileInputStream(image);
            MultipartFile multipartFile = new MockMultipartFile("product"+index, index + ".jpeg", "image/jpeg", imageStream);
            productImageService.addImage(multipartFile, product);
          }
          index++;
        }

        ratingService.addRating(new Rating(userService.findAll().get(0), "Geir", productService.getProductByName("Heather tea"), 4, "ok"));
        ratingService.addRating(new Rating(userService.findAll().get(0), "Geir", productService.getProductByName("Linden blossom tea"), 5, "Best tea ever"));
        ratingService.addRating(new Rating(userService.findAll().get(0), "Geir", productService.getProductByName("Sencha 50g"), 2, "yum"));
        ratingService.addRating(new Rating(userService.findAll().get(0), "Geir", productService.getProductByName("Sencha 50g"), 2, "more quantity of yum"));
        ratingService.addRating(new Rating(userService.findAll().get(0), "Geir", productService.getProductByName("Mug"), 5, "Nice mugs"));
        ratingService.addRating(new Rating(userService.findAll().get(1), "Per", productService.getProductByName("Linden blossom tea"), 5, "Great tea"));
        ratingService.addRating(new Rating(userService.findAll().get(1), "Per", productService.getProductByName("Sencha 50g"), 2, "This was weird"));
        ratingService.addRating(new Rating(userService.findAll().get(1), "Per", productService.getProductByName("Sencha 50g"), 2, "This was a lot of weird"));
        ratingService.addRating(new Rating(userService.findAll().get(1), "Per", productService.getProductByName("Mug"), 5, "Great big mugs"));

        wishlistService.addWishlist(new Wishlist(userService.findAll().get(0)));
        wishlistService.addWishlist(new Wishlist(userService.findAll().get(1)));
        wishlistService.addWishlist(new Wishlist(userService.findAll().get(2)));
        wishlistService.addWishlist(new Wishlist(userService.findAll().get(3)));
        wishlistService.addWishlist(new Wishlist(userService.findAll().get(4)));

        shoppingSessionService.addShoppingSession(new ShoppingSession(userService.findAll().get(0)));
        shoppingSessionService.addShoppingSession(new ShoppingSession(userService.findAll().get(1)));
        shoppingSessionService.addShoppingSession(new ShoppingSession(userService.findAll().get(2)));
        shoppingSessionService.addShoppingSession(new ShoppingSession(userService.findAll().get(3)));
        shoppingSessionService.addShoppingSession(new ShoppingSession(userService.findAll().get(4)));

        OrderDetails od1 = new OrderDetails();
        od1.setUser(userService.findOneByEmail("arne@arnesen.arn"));
        OrderItem oi1 = new OrderItem(od1, productService.getProductByName("Heather tea"), 2);
        od1.addOrderItem(oi1);
        orderDetailsService.addOrderDetails(od1);
        orderItemService.addOrderItem(oi1);

        OrderDetails od2 = new OrderDetails();
        User user = userService.findOneByEmail("per@person.per");
        od2.setUser(user);
        od2.setProcessed(true);
        OrderItem oi2 = new OrderItem(od2, productService.getProductByName("Sencha 50g"), 2);
        OrderItem oi3 = new OrderItem(od2, productService.getProductByName("Mug"), 1);
        od2.addOrderItem(oi2);
        od2.addOrderItem(oi3);
        orderDetailsService.addOrderDetails(od2);
        orderItemService.addOrderItem(oi2);
        orderItemService.addOrderItem(oi3);
      }
    };
  }
}
