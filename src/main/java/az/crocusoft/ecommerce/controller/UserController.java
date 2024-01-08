package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.dto.UserDto;
import az.crocusoft.ecommerce.dto.UserResponse;
import az.crocusoft.ecommerce.model.User;
import az.crocusoft.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
//@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private  final  UserService service;
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUser(@PathVariable("id") Long id){

        return  service.getUser(id);


    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@RequestBody UserDto userDto){

       service.createUser(userDto);

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@PathVariable( "id") Long id,@RequestBody UserDto userDto){


               service.updateUser(id,userDto);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable( "id") Long id){
        service.deleteUser(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).
                build();

    }

}
