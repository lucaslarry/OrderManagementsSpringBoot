package com.course.springboot.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigurations {
    private final TokenService tokenService;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
            httpSecurity.headers().frameOptions().disable()
                    .and()
                    .cors().and()
                    .csrf().disable()
                    .authorizeHttpRequests((authz) -> authz

                            //Liberado
                            .antMatchers("/auth", "/auth/login", "/").permitAll()
                            .antMatchers("/h2-console/**").permitAll()
                            .antMatchers(HttpMethod.POST, "/users/register").permitAll()

                            //Apenas ADMIN
                            .antMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                            .antMatchers(HttpMethod.GET, "/users/{id}").hasRole("ADMIN")
                            .antMatchers(HttpMethod.DELETE, "/users/{id}").hasRole("ADMIN")
                            .antMatchers(HttpMethod.POST, "/auth/register").hasRole("ADMIN")
                            .antMatchers(HttpMethod.POST, "/products").hasRole("ADMIN")
                            .antMatchers(HttpMethod.PUT, "/products/{id}").hasRole("ADMIN")
                            .antMatchers(HttpMethod.DELETE, "/products/{id}").hasRole("ADMIN")
                            .antMatchers(HttpMethod.POST, "/categories").hasRole("ADMIN")
                            .antMatchers(HttpMethod.PUT, "/categories/{id}").hasRole("ADMIN")
                            .antMatchers(HttpMethod.DELETE, "/categories/{id}").hasRole("ADMIN")
                            .antMatchers(HttpMethod.PUT, "/orders/{id}").hasRole("ADMIN")
                            .antMatchers(HttpMethod.DELETE, "/orders/{id}").hasRole("ADMIN")

                            //Para USER e ADMIN
                            .antMatchers(HttpMethod.PUT, "/users/{id}").hasAnyRole("ADMIN", "USER")
                            .antMatchers(HttpMethod.GET, "/products").hasAnyRole("ADMIN", "USER")
                            .antMatchers(HttpMethod.GET, "/categories").hasAnyRole("ADMIN", "USER")
                            .antMatchers(HttpMethod.GET, "/orders").hasAnyRole("ADMIN", "USER")
                            .antMatchers(HttpMethod.POST, "/orders").hasAnyRole("ADMIN", "USER")
                            .antMatchers(HttpMethod.POST, "/payments").hasAnyRole("ADMIN", "USER")
                            .antMatchers(HttpMethod.GET, "/payments/{id}").hasAnyRole("ADMIN", "USER")

                            // Qualquer outra requisição precisa de autenticação
                            .anyRequest().authenticated()
                    );

            httpSecurity.addFilterBefore(new TokenAuthenticationFilter(tokenService), UsernamePasswordAuthenticationFilter.class);

            return httpSecurity.build();


        }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/swagger-ui/**",
        "/swagger-ui/index.html#");
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        // configura as regras de cors
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .exposedHeaders("Authorization");
            }
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
            return new Pbkdf2PasswordEncoder();
        }

}
