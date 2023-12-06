package az.crocusoft.ecommerce.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class ApiConfig {

    @Bean
    public JavaMailSender javaMailSender(){
        return new JavaMailSenderImpl();
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
