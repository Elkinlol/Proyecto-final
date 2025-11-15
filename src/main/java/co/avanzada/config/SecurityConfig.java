package co.avanzada.config;


import co.avanzada.security.JWTAuthenticationEntryPoint;
import co.avanzada.security.JWTFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Configura la seguridad HTTP para la aplicación
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/api/auth/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml",
                                "/swagger-resources/**",
                                "/webjars/**","/api/enums", "/api/listings/search**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/user").hasAuthority("GUEST")
                        .requestMatchers(HttpMethod.PUT, "/api/user/host").hasAuthority("HOST")
                        .requestMatchers(HttpMethod.GET, "/api/user").hasAuthority("GUEST")
                        .requestMatchers(HttpMethod.GET, "/api/user/host").hasAuthority("HOST")
                        .requestMatchers(HttpMethod.PATCH, "/api/user/host").hasAuthority("GUEST")
                        .requestMatchers(HttpMethod.PATCH, "/api/user/guest").hasAuthority("HOST")
                        .requestMatchers(HttpMethod.POST, "/api/listings").hasAuthority("HOST")
                        .requestMatchers(HttpMethod.PATCH, "/api/listings/{id}").hasAuthority("HOST")
                        .requestMatchers(HttpMethod.DELETE, "/api/listings/{id}").hasAuthority("HOST")
                        .requestMatchers(HttpMethod.GET, "/api/listings/{id}/metrics").hasAuthority("HOST")
                        .requestMatchers(HttpMethod.GET, "/api/listings").hasAuthority("HOST")
                        .requestMatchers("api/listings/{id}/images/**").hasAuthority("HOST")
                        .requestMatchers(HttpMethod.POST, "/api/reserves/{listingId}").hasAuthority("GUEST")
                        .requestMatchers(HttpMethod.GET, "/api/reserves").hasAuthority("GUEST")
                        .requestMatchers(HttpMethod.DELETE, "/api/reserves/{listingId}").hasAuthority("GUEST")
                        .requestMatchers(HttpMethod.GET, "/api/reserves/{listingId}/host").hasAuthority("HOST")
                        .requestMatchers(HttpMethod.POST, "/api/review/{listingId}").hasAuthority("GUEST")
                        .requestMatchers(HttpMethod.GET, "/api/reviews/host/{reviewId}").hasAuthority("HOST")
                        .requestMatchers(HttpMethod.GET, "/api/promotion/{listingId}").hasAuthority("HOST")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(new JWTAuthenticationEntryPoint()))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // Configura las políticas de CORS para permitir solicitudes desde el frontend
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Permite codificar y verificar contraseñas utilizando BCrypt
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        // Proporciona un AuthenticationManager para la autenticación de usuarios
        return configuration.getAuthenticationManager();
    }
}
