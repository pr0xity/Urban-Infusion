package no.ntnu.appdevapi.security;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;


/**
 * Creates AuthenticationManager - set up authentication type
 * The @EnableWebSecurity tells that this ia a class for configuring web security
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsService userDetailsService;
  @Autowired
  private UnauthorizedEntryPoint unauthorizedEntryPoint;


  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
  }


  /**
   * Configure the authorization rules
   *
   * @param http HTTP Security object
   * @throws Exception
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.cors(Customizer.withDefaults());
    //TODO: This need to be be updated with correct paths
    http.sessionManagement().sessionCreationPolicy(STATELESS);
    http.authorizeRequests()
            //.antMatchers("/resources/**").permitAll()
            // NB: Deleting product from wishlist does not work with this:
            //.antMatchers(DELETE).hasAuthority("owner")
            .antMatchers("API/admin/?*").hasAnyAuthority("admin", "owner")
            .antMatchers(GET, "API/users").hasAnyAuthority("admin", "owner")
            .antMatchers(PUT, "API/users/**").hasAnyAuthority("admin", "owner")
            .antMatchers(PUT, "API/users/**").authenticated()
            .antMatchers(POST, "API/wishlists").hasAnyAuthority("user", "admin", "owner")
            .antMatchers(DELETE, "API/wishlists").hasAnyAuthority("user", "admin", "owner")
            .antMatchers(GET, "API/wishlists").authenticated()
            .antMatchers("/wishlist").hasAnyAuthority("user", "admin", "owner")
            .antMatchers("/checkout").hasAnyAuthority("user", "admin", "owner")
            .antMatchers("/account").hasAnyAuthority("user", "admin", "owner")
            .antMatchers(DELETE, "API/ratings/**").hasAnyAuthority("admin", "owner")
            .antMatchers(DELETE, "API/ratings/**").authenticated()
            .antMatchers(POST,"API/ratings/**").hasAnyAuthority("user", "admin", "owner")
            .antMatchers(GET,"API/ratings").permitAll()
            .antMatchers(DELETE, "API/carts/**").hasAnyAuthority("user", "admin", "owner")
            .antMatchers(PUT, "API/carts/**").hasAnyAuthority("user", "admin", "owner")
            .antMatchers(GET, "API/carts/**").hasAnyAuthority("user", "admin", "owner")
            .antMatchers(GET, "API/cart").hasAnyAuthority("user", "admin", "owner")
            .antMatchers(POST, "API/products").hasAnyAuthority("admin", "owner")
            .antMatchers(GET, "API/products").permitAll()
            .antMatchers(DELETE, "API/products/**").hasAnyAuthority("admin", "owner")
            .antMatchers(PUT, "API/products/**").hasAnyAuthority("admin", "owner")
            .antMatchers(POST, "API/products/images/**").hasAnyAuthority("admin", "owner")
            .antMatchers(PUT, "API/products/images/**").hasAnyAuthority("admin", "owner")
            .antMatchers(DELETE, "API/products/images/**").hasAnyAuthority("admin", "owner")
            .antMatchers(GET, "API/products/images/**").permitAll()
            .antMatchers(GET, "API/product-categories/**").hasAnyAuthority("admin","owner")
            .antMatchers(PUT, "API/product-categories/**").hasAnyAuthority("admin","owner")
            .antMatchers(DELETE, "API/product-categories/**").hasAnyAuthority("admin","owner")
            .antMatchers(DELETE, "API/orders/**").hasAnyAuthority("admin", "owner")
            .antMatchers(GET, "API/orders/**").hasAnyAuthority("admin", "owner")
            .antMatchers(GET, "API/orders").hasAnyAuthority("admin", "owner")
            .antMatchers(POST, "API/orders").hasAnyAuthority("user", "admin", "owner")
            .antMatchers(GET,"API/user").authenticated()
            .antMatchers(POST,"API/users").permitAll()
            .antMatchers("/","API/login", "API/logout", "API/register", "/products/**" ).permitAll();
    http.authorizeRequests().and().exceptionHandling()
            .authenticationEntryPoint(unauthorizedEntryPoint);
    http.addFilterBefore(authenticationTokenFilterBean(),
            UsernamePasswordAuthenticationFilter.class);
  }

  /**
   * Configures Spring application to allow requests from another origin.
   */
  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("https://gr03.appdev.cloudns.ph/"));
    configuration.setAllowedMethods(Arrays.asList("HEAD", "GET","POST", "DELETE", "PUT"));
    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type", "Set-Cookie"));
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  /**
   * This method is called to decide what encryption to use for password checking
   *
   * @return The password encryptor
   */
  @Bean
  public BCryptPasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public SecurityServletFilter authenticationTokenFilterBean() {
    return new SecurityServletFilter();
  }

}
