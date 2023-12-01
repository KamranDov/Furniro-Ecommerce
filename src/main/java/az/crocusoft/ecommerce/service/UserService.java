package az.crocusoft.ecommerce.service;


import az.crocusoft.ecommerce.dto.UserDto;
import az.crocusoft.ecommerce.dto.UserResponse;
import az.crocusoft.ecommerce.enums.Role;
import az.crocusoft.ecommerce.model.User;
import az.crocusoft.ecommerce.model.product.Product;
import az.crocusoft.ecommerce.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {



   private   final UserRepository userRepository;

    private final AuthenticationService service;

    private final PasswordEncoder passwordEncoder;

    public UserResponse getUser(Long id){

        User u= userRepository.findById(id).orElseThrow(()->new RuntimeException("not found"));

        UserResponse user = UserResponse.builder()
                .username(u.getUsername())
                .name(u.getName())
                .email(u.getEmail())
                .surName(u.getUsername())
                .role(u.getRole()).build();

        return user;

    }
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User is currently not available"));
    }

    public void createUser(UserDto userDto){


         service.save(userDto);


    }


    public void updateUser( Long id,UserDto userDto){

        getUser(id);

        User user = User.builder()
                .id(id)
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .name(userDto.getName())
                .email(userDto.getEmail())
                .surname(userDto.getSurname())
                .role(Role.USER).build();



        userRepository.save(user);



    }


    public void deleteUser( Long id){

        userRepository.deleteById(id);


    }


}
