package com.szabodev.example.security.web.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${config.protected.api}")
    private String protectedApi;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] generatedFiles = {
                "/**.js",
                "/favicon.ico",
                "/**/**.css",
        };
        String[] unAuthorized = new String[0];
        if ("false".equals(protectedApi)) {
            unAuthorized = new String[]{
                    "/api/v1/**",
                    "/h2-console/**"
            };
        }
        http.cors().and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED)))
                .and()
                .authorizeRequests()
                .antMatchers("/", "/api/auth/**").permitAll()
                .antMatchers(unAuthorized).permitAll()
                .antMatchers(generatedFiles).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginProcessingUrl("/api/auth/login").permitAll()
                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .and()
                .logout().logoutUrl("/api/auth/logout").clearAuthentication(true).invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .addLogoutHandler((request, response, authentication) -> log.info("User successfully logged out, name: {}", authentication.getName()))
                .permitAll();

        // For h2-console
        http.headers().frameOptions().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        log.info("Configure in memory authentication");
        auth
                .inMemoryAuthentication()
                .withUser("test1").password("{noop}" + "test1").authorities("ADMIN");
        auth
                .inMemoryAuthentication()
                .withUser("test2").password("{noop}" + "test2").authorities("USER");
    }

    @Bean
    public CorsFilter corsFilter(@Value("${config.environment}") String environment) {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        final CorsConfiguration config = new CorsConfiguration();
        if ("local".equals(environment)) {
            config.setAllowCredentials(true);
            config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
            config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
            config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        }

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
