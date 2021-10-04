package group.purr.purrbackend.config;

import group.purr.purrbackend.security.TokenAuthenticateFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final TokenAuthenticateFilter tokenAuthenticateFilter;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .sessionFixation().none()
                .and()
                .authorizeRequests()
                .antMatchers("/getToken/**",
                        "/swagger-ui.html",
                        "/swagger.json",
                        "/swagger-resources/**",
                        "/api/**",
                        "/webjars/**",
                        "/v2/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .addFilterBefore(tokenAuthenticateFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
