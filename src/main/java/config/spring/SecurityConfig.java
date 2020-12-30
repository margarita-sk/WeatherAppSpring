package config.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import user.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan("user")
@Import(AppBeansConfiguration.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth, UserService service)
      throws Exception {
    service.receiveAll().forEach((user) -> {
      try {
        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
            .withUser(user.getUsername()).password(passwordEncoder().encode(user.getPassword()))
            .roles(user.getAuthorities().stream().findFirst().orElseThrow().getAuthority());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().antMatchers("/outfit/list").authenticated().and().formLogin()
        .loginPage("/loginPage").defaultSuccessUrl("/index").failureUrl("/loginPage?error")
        .usernameParameter("username").passwordParameter("password").and().logout()
        .logoutSuccessUrl("/index");

  }

}
