package az.crocusoft.ecommerce;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class EcommerceApplication  {

	public static void main(String[] args) {


		SpringApplication.run(EcommerceApplication.class, args);
	}


//	@Bean
//	public CommandLineRunner commandLineRunner(
//			AuthenticationService service
//	) {
//		return args -> {
//			var admin = UserDto.builder()
//					.name("Shemsi")
//					.surname("Azizaliyev")
//					.username("admin")
//					.password("12345")
//					.email("admin@gmail.com")
//					.build();
//
//
//			System.out.println("Admin token: " + service.saveAdmin(admin).getAccessToken());
//
//
//
//		};
//	}





}
