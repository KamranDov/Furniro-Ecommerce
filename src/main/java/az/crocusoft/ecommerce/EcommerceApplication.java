package az.crocusoft.ecommerce;



import az.crocusoft.ecommerce.dto.UserDto;
import az.crocusoft.ecommerce.model.User;
import az.crocusoft.ecommerce.repository.UserRepository;
import az.crocusoft.ecommerce.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static az.crocusoft.ecommerce.enums.Role.ADMIN;
import static az.crocusoft.ecommerce.enums.Role.MANAGER;


@SpringBootApplication
public class EcommerceApplication  implements CommandLineRunner   {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}


	@Autowired
	UserRepository userRepository;
	@Override
	public void run(String... args) throws Exception {



	}






/*

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service
	) {
		return args -> {
			var admin = UserDto.builder()
					.name("Admin")
					.surname("Admin")
					.username("AdminA")
					.password("password")
					.email("admin@gmail.com")
					.role(ADMIN)

					.build();


			System.out.println("Admin token: " + service.save(admin).getAccessToken());

			var manager = UserDto.builder()
					.name("Admin")
					.surname("Admin")
					.username("ManagerM")
					.password("password")
					.email("manager@gmail.com")
					.role(MANAGER)
					.build();
			System.out.println("Manager token: " + service.save(manager).getAccessToken());

		};
	}*/



}
