package az.crocusoft.ecommerce.service;


import az.crocusoft.ecommerce.dto.UserDto;
import az.crocusoft.ecommerce.dto.UserRequest;
import az.crocusoft.ecommerce.dto.UserRespons;
import az.crocusoft.ecommerce.dto.UserResponse;
import az.crocusoft.ecommerce.enums.Role;
import az.crocusoft.ecommerce.model.User;
import az.crocusoft.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {



   private   final UserRepository userRepository;

    private final AuthenticationService service;

    private final PasswordEncoder passwordEncoder;

    public UserRespons getUser(Long id){


        User u= userRepository.findById(id).orElseThrow(()->new RuntimeException("not found"));




        UserRespons user = UserRespons.builder()
                .username(u.getUsername())
                .name(u.getName())
                .email(u.getEmail())
                .surName(u.getSurName())
                .role(u.getRole()).build();

        return user;




    }



    public UserResponse createUser(UserDto userDto){


        return service.save(userDto);



    }


    public UserRespons updateUser( Long id,UserDto userDto){

        getUser(id);

        User user = User.builder()
                .id(id)
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .name(userDto.getName())
                .email(userDto.getEmail())
                .surName(userDto.getSurName())
                .role(Role.USER).build();



        userRepository.save(user);

        UserRespons response = UserRespons.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .name(userDto.getName())
                .surName(userDto.getSurName())
                .role(Role.USER).build();

        return response ;

    }


    public void deleteUser( Long id){

        userRepository.deleteById(id);


    }


}
