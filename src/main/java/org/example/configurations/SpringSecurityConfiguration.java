package org.example.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.exceptions.errors.AuthorizationError;
import org.example.exceptions.errors.ErrorCode;
import org.example.exceptions.errors.ErrorMessage;
import org.example.managers.TokenManager;
import org.example.services.AuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * This class contains all security related configurations.
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    private static final String LOCAL_PROFILE = "LOCAL";

    private final Environment environment;
    private final AuthenticationService authenticationService;
    private final TokenManager tokenManager;
    private final ObjectMapper objectMapper;

    public SpringSecurityConfiguration(Environment environment, AuthenticationService authenticationService, TokenManager tokenManager, ObjectMapper objectMapper) {
        this.environment = environment;
        this.authenticationService = authenticationService;
        this.tokenManager = tokenManager;
        this.objectMapper = objectMapper;
    }

    /**
     * Configure web security for the API endpoints.
     *
     * @param http the security builder
     * @throws Exception if the configuration could not be applied
     */
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        List<String> profiles = Arrays.asList(environment.getActiveProfiles());

        if (profiles.isEmpty() || profiles.contains(LOCAL_PROFILE)) {
            http.headers().frameOptions().disable();
        } else {
            http.headers().frameOptions().sameOrigin();
        }
        http.cors();

        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeHttpRequests()

                // Allow access to token distribution and validation endpoints
                .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                // Allow access to account creation
                .requestMatchers(HttpMethod.POST, "/account").permitAll()

                // Allow CORS OPTION methods
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // All other requests need to be authenticated
                .anyRequest().authenticated().and()

                // Custom JSON based authentication based on the header previously given to the client
                .addFilterBefore(new JwtAuthenticationFilter(authenticationService, tokenManager, objectMapper), BasicAuthenticationFilter.class)

                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint);
        return http.build();
    }

    private final AuthenticationEntryPoint authEntryPoint = new AuthenticationEntryPoint() {

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
            log.error("Error authenticating user", authException);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            // test that header is not null else an error will be thrown (in integration tests we don't have an ORIGIN header)
            if (request.getHeader(HttpHeaders.ORIGIN) != null)
                response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, request.getHeader(HttpHeaders.ORIGIN));
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ErrorMessage errorMessage;
            // This error happens if the user from the JWT token cannot be found by AuthenticationService#authenticateWithToken
            // This error happens if there is no token in the JwtProperties#getHeaderName header
            if (authException instanceof BadCredentialsException)
                errorMessage = buildErrorMessage(AuthorizationError.JWT_INVALID);
            else
                errorMessage = buildErrorMessage(AuthorizationError.JWT_INVALID);
            objectMapper.writeValue(response.getOutputStream(), errorMessage);
        }

        private ErrorMessage buildErrorMessage(ErrorCode errorCode) {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setCode(errorCode.getCode());
            errorMessage.setDescription(errorCode.getDescription());
            return errorMessage;
        }

    };
}
