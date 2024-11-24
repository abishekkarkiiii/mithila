package Tracker.Mensuration.Authentication;

import Tracker.Mensuration.Repositry.UserRepo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserRepo userRepo;

    // Bean to configure Spring Security's HTTP security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {  // Declare throws Exception here
        http
                .csrf().disable()
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/Admin.html", "/Admin/**").hasRole("Admin") // Only Admin can access admin routes
                        .requestMatchers("/Animated-Login-and-Signup-Form-Leaf/**", "/useraccount/**", "/login").permitAll() // Open to all
                        .anyRequest().authenticated() // All other routes require authentication
                )
                .formLogin(form -> form
                        .loginPage("/Animated-Login-and-Signup-Form-Leaf/index.html")
                        .loginProcessingUrl("/login")
                        .successHandler((request, response, authentication) -> {
                            // Check if the user has an admin role
                            boolean isAdmin = authentication.getAuthorities().stream()
                                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_Admin"));

                            if (isAdmin) {
                                // Admin success handler
                                String jsonResponse = "{\"message\": \"Admin\", \"role\": \"Admin\"}";
                                response.getWriter().write(jsonResponse);
                                response.setContentType("application/json");
                                response.setStatus(200);
                            } else {
                                // User success handler
                                String username = authentication.getName();
                                String id = userRepo.findByUsername(username).getId().toString();

                                HttpSession session = request.getSession();
                                session.setAttribute("userId", id);

                                // Create and set cookie
                                Cookie cookie = new Cookie("userId", id);
                                cookie.setMaxAge(7 * 24 * 60 * 60); // Set for 7 days
//                                cookie.setHttpOnly(true); // Make sure this is false to allow JavaScript to access the cookie
//                                cookie.setPath("/"); // Make the cookie available across the domain
//                                cookie.setSecure(false);// Use true if your site uses HTTPS
//                                cookie.setDomain("localhost");
                                response.addCookie(cookie);

                                response.setContentType("application/json");
                                String jsonResponse = String.format("{\"message\": \"Logged in successfully\", \"id\": \"%s\"}", id);
                                response.getWriter().write(jsonResponse);
                            }
                        })
                        .failureHandler((request, response, exception) -> {
                            response.setStatus(401);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\": \"Authentication failed. Invalid credentials.\"}");
                        })
                        .permitAll()
                )
                .rememberMe(rememberMeConfigurer ->
                        rememberMeConfigurer
                                .tokenRepository(persistentTokenRepository())
                                .tokenValiditySeconds(1209600) // Token validity for 14 days
                                .key("uniqueAndSecret") // Key for hashing tokens
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login.html?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "remember-me")
                        .deleteCookies("userId") // Clear custom userId cookie on logout
                        .permitAll()
                )
                .headers()
                .frameOptions().sameOrigin(); // Allow iframe embedding for the same origin

        return http.build();
    }

    // In-memory token repository for 'remember-me' functionality (for development)
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        return new InMemoryTokenRepositoryImpl(); // Use JdbcTokenRepositoryImpl for production
    }

    // Custom authentication provider (you can implement your custom logic here)
    @Bean
    public AuthenticationProvider customAuthenticationProvider() {
        return new Authentication(); // Your custom Authentication implementation
    }

    // Web configuration to serve static resources
    @Configuration
    public class WebConfig implements WebMvcConfigurer {

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/static/**")
                    .addResourceLocations("classpath:/static/");
        }
    }
}
