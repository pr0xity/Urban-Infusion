package no.ntnu.appdevapi.security;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


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

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable();
    //TODO: This need to be be updated with correct paths
    http.sessionManagement().sessionCreationPolicy(STATELESS);
    http.authorizeRequests()
            .antMatchers("/signup", "/login", "/products", "/product/**", "/users/authenticate")
            .permitAll();
    http.authorizeRequests().antMatchers(GET, "/user/**").hasAnyAuthority("user");
    http.authorizeRequests().antMatchers("/users").hasAnyAuthority("admin", "owner");
    http.authorizeRequests().antMatchers(DELETE).hasAnyAuthority("owner");
    http.authorizeRequests().anyRequest().authenticated().and().exceptionHandling()
            .authenticationEntryPoint(unauthorizedEntryPoint);
    http.addFilterBefore(authenticationTokenFilterBean(),
            UsernamePasswordAuthenticationFilter.class);
  }


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
  public JwtAuthenticationFilter authenticationTokenFilterBean() {
    return new JwtAuthenticationFilter();
  }

}