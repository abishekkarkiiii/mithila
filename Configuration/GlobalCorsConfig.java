//package Tracker.Mensuration.Configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class GlobalCorsConfig {
//
//    @Bean
//    public WebMvcConfigurer configurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**") // Allow CORS for all paths
//                        .allowedOrigins("http://localhost:3000") // Specify your frontend origin
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow specific HTTP methods
//                        .allowedHeaders("*") // Allow all headers
//                        .allowCredentials(true) // Allow credentials if necessary
//                        .maxAge(3600); // Cache preflight response for 1 hour
//            }
//        };
//    }
//}
