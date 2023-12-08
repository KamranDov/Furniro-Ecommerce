package az.crocusoft.ecommerce.config;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CrosConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*"); // Tüm origin'lere izin ver
        config.addAllowedHeader("*"); // Allow any header
        config.addAllowedMethod("*"); // Allow any method
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter();
    }
}
