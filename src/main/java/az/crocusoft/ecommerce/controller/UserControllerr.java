package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.dto.UserDto;
import az.crocusoft.ecommerce.dto.UserRespons;
import az.crocusoft.ecommerce.dto.UserResponse;
import az.crocusoft.ecommerce.model.User;
import az.crocusoft.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('ADMIN')")
public class UserControllerr {




    @Autowired
    private UserService service;


    @GetMapping("getUser/{id}")
    public UserRespons getUser(@PathVariable("id") Long id){

        return  service.getUser(id);


    }

    @PostMapping("/save")

    public ResponseEntity<UserResponse> addUser(@RequestBody UserDto userDto){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createUser(userDto));


    }

    @PostMapping("/update/{id}")
    public ResponseEntity<UserRespons> updateUser(@PathVariable( "id") Long id,@RequestBody UserDto userDto){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.updateUser(id,userDto));

    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable( "id") Long id){
        service.deleteUser(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).
                build();

    }

}
