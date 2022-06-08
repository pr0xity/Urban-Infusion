package no.ntnu.appdevapi.security;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    final String admin = "admin";
    final String owner = "owner";

    http.cors().and().csrf().disable();
    http.sessionManagement().sessionCreationPolicy(STATELESS);
    http.authorizeRequests()
            .antMatchers("/admin", "/admin/**", "/api/admin/*", "/api/product-categories/**").hasAnyAuthority(admin, owner)
            .antMatchers(GET, "/api/users").hasAnyAuthority(admin, owner)
            .antMatchers(PUT, "/api/users/**").hasAnyAuthority(admin, owner)
            .antMatchers(POST, "/api/products").hasAnyAuthority(admin, owner)
            .antMatchers(PUT, "/api/products/**").hasAnyAuthority(admin, owner)
            .antMatchers(DELETE, "/api/products/**").hasAnyAuthority(admin, owner)
            .antMatchers(POST, "/api/products/images/**").hasAnyAuthority(admin, owner)
            .antMatchers(PUT, "/api/products/images/**").hasAnyAuthority(admin, owner)
            .antMatchers(DELETE, "/api/products/images/**").hasAnyAuthority(admin, owner)
            .antMatchers(PUT, "/api/orders").hasAnyAuthority(admin, owner)
            .antMatchers(DELETE, "/api/orders").hasAnyAuthority(admin, owner)
            .antMatchers(GET, "/api/orders").hasAnyAuthority(admin, owner)
            .antMatchers(GET, "/api/orders/**").hasAnyAuthority(admin, owner)
            .antMatchers(PUT, "/api/orders/**").hasAnyAuthority(admin, owner)
            .antMatchers(DELETE, "/api/orders/**").hasAnyAuthority(admin, owner)
            .antMatchers("/wishlist", "/account", "/checkout").authenticated()
            .antMatchers( "/api/carts/**").authenticated()
            .antMatchers(GET, "/api/carts").authenticated()
            .antMatchers(POST, "/api/orders").authenticated()
            .antMatchers(POST, "/api/orders/**").authenticated()
            .antMatchers(POST,"/api/ratings/**").authenticated()
            .antMatchers(PUT,"/api/ratings/**").authenticated()
            .antMatchers(DELETE, "/api/ratings/**").authenticated()
            .antMatchers(GET, "/api/wishlists").authenticated()
            .antMatchers(PUT, "/api/users/**").authenticated()
            .antMatchers(GET,"/api/user").authenticated()
            .antMatchers(GET, "/wishlist/shared/**").permitAll()
            .antMatchers(GET,"/api/ratings").permitAll()
            .antMatchers(GET, "/api/products").permitAll()
            .antMatchers(GET, "/api/product-categories").permitAll()
            .antMatchers(GET, "/api/products/images/**").permitAll()
            .antMatchers(POST,"/api/users").permitAll()
            .antMatchers("/", "/products/**", "/api/logout", "/api/register", "/api/login" ).permitAll();
    http.exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint);
    http.addFilterBefore(authenticationTokenFilterBean(),
            UsernamePasswordAuthenticationFilter.class);
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
