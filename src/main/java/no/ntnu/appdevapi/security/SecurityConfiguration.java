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
   * @throws Exception
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable();
    //TODO: This need to be be updated with correct paths
    http.sessionManagement().sessionCreationPolicy(STATELESS);
    http.authorizeRequests()
            //.antMatchers("/resources/**").permitAll()
            // NB: Deleting product from wishlist does not work with this:
            //.antMatchers(DELETE).hasAuthority("owner")
            .antMatchers("/admin/?*").hasAnyAuthority("admin", "owner")
            .antMatchers(GET, "/users").hasAnyAuthority("admin", "owner")
            .antMatchers(PUT, "/users/**").hasAnyAuthority("admin", "owner")
            .antMatchers(PUT, "/users/**").authenticated()
            .antMatchers("/wishlist").hasAnyAuthority("user", "admin", "owner")
            .antMatchers("/checkout").hasAnyAuthority("user", "admin", "owner")
            .antMatchers("/account").hasAnyAuthority("user", "admin", "owner")
            .antMatchers(DELETE, "/ratings/**").hasAnyAuthority("admin", "owner")
            .antMatchers(DELETE, "/ratings/**").authenticated()
            .antMatchers(POST,"/ratings/**").hasAnyAuthority("user", "admin", "owner")
            .antMatchers(GET,"/ratings").permitAll()
            .antMatchers(DELETE, "/cart/**").hasAnyAuthority("user", "admin", "owner")
            .antMatchers(PUT, "/cart/**").hasAnyAuthority("user", "admin", "owner")
            .antMatchers(GET, "/cart/**").hasAnyAuthority("user", "admin", "owner")
            .antMatchers(GET, "/cart").hasAnyAuthority("user", "admin", "owner")
            .antMatchers(POST, "/products").hasAnyAuthority("admin", "owner")
            .antMatchers(GET, "/products").permitAll()
            .antMatchers(DELETE, "/products/**").hasAnyAuthority("admin", "owner")
            .antMatchers(PUT, "/products/**").hasAnyAuthority("admin", "owner")
            .antMatchers(POST, "/products/images/**").hasAnyAuthority("admin", "owner")
            .antMatchers(PUT, "/products/images/**").hasAnyAuthority("admin", "owner")
            .antMatchers(DELETE, "/products/images/**").hasAnyAuthority("admin", "owner")
            .antMatchers(GET, "/products/images/**").permitAll()
            .antMatchers(GET, "/productCategory/**").hasAnyAuthority("admin","owner")
            .antMatchers(PUT, "/productCategory/**").hasAnyAuthority("admin","owner")
            .antMatchers(DELETE, "/productCategory/**").hasAnyAuthority("admin","owner")
            .antMatchers(DELETE, "/orders/**").hasAnyAuthority("admin", "owner")
            .antMatchers(GET, "/orders/**").hasAnyAuthority("admin", "owner")
            .antMatchers(GET, "/orders").hasAnyAuthority("admin", "owner")
            .antMatchers(POST, "/orders").hasAnyAuthority("user", "admin", "owner")
            .antMatchers(GET,"/user").authenticated()
            .antMatchers(POST,"/users").permitAll()
            .antMatchers("/","/login","/register", "/products/**" ).permitAll()
            .and().logout().logoutUrl("/logout").logoutSuccessUrl("/").deleteCookies("token").clearAuthentication(true).invalidateHttpSession(true);
    http.authorizeRequests().and().exceptionHandling()
            .authenticationEntryPoint(unauthorizedEntryPoint);
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
