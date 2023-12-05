package az.crocusoft.ecommerce;


import az.crocusoft.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class EcommerceApplication  {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}



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




