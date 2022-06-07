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

  private String admin = "admin";
  private String user = "user";
  private String owner = "owner";


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
    http.sessionManagement().sessionCreationPolicy(STATELESS);
    http.authorizeRequests()

            .antMatchers("/admin").hasAnyAuthority(admin, owner)
            .antMatchers("/admin/**").hasAnyAuthority(admin, owner)
            .antMatchers("/api/admin/*").hasAnyAuthority(admin, owner)
            .antMatchers(GET, "/api/users").hasAnyAuthority(admin, owner)
            .antMatchers(PUT, "/api/users/**").hasAnyAuthority(admin, owner)
            .antMatchers(POST, "/api/products").hasAnyAuthority(admin, owner)
            .antMatchers(PUT, "/api/products/**").hasAnyAuthority(admin, owner)
            .antMatchers(DELETE, "/api/products/**").hasAnyAuthority(admin, owner)
            .antMatchers(POST, "/api/products/images/**").hasAnyAuthority(admin, owner)
            .antMatchers(PUT, "/api/products/images/**").hasAnyAuthority(admin, owner)
            .antMatchers(DELETE, "/api/products/images/**").hasAnyAuthority(admin, owner)
            .antMatchers(GET, "/api/product-categories/**").hasAnyAuthority(admin,owner)
            .antMatchers(PUT, "/api/product-categories/**").hasAnyAuthority(admin,owner)
            .antMatchers(DELETE, "/api/product-categories/**").hasAnyAuthority(admin,owner)
            .antMatchers(GET, "/api/orders").hasAnyAuthority(admin, owner)
            .antMatchers(GET, "/api/orders/**").hasAnyAuthority(admin, owner)
            .antMatchers(DELETE, "/api/orders/**").hasAnyAuthority(admin, owner)
            .antMatchers("/wishlist").authenticated()
            .antMatchers("/checkout").authenticated()
            .antMatchers("/account").authenticated()
            .antMatchers( "/api/carts/**").authenticated()
            .antMatchers(GET, "/api/carts").authenticated()
            .antMatchers(POST, "/api/orders").authenticated()
            .antMatchers(POST,"/api/ratings/**").authenticated()
            .antMatchers(PUT,"/api/ratings/**").authenticated()
            .antMatchers(DELETE, "/api/ratings/**").authenticated()
            .antMatchers(GET, "/api/wishlists").authenticated()
            .antMatchers(PUT, "/api/users/**").authenticated()
            .antMatchers(GET,"/api/user").authenticated()
            .antMatchers(GET, "/wishlist/shared/**").permitAll()
            .antMatchers(GET,"/api/ratings").permitAll()
            .antMatchers(GET, "/api/products").permitAll()
            .antMatchers(GET, "/api/products/images/**").permitAll()
            .antMatchers(POST,"/api/users").permitAll()
            .antMatchers("/", "/products/**", "/api/logout", "/api/register", "/api/login" ).permitAll();
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
