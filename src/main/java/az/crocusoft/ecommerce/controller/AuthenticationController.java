package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.dto.UserDto;
import az.crocusoft.ecommerce.dto.UserRequest;
import az.crocusoft.ecommerce.dto.AuthResponse;
import az.crocusoft.ecommerce.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> save(@RequestBody  @Valid UserDto userDto) {
        return ResponseEntity.ok(authenticationService.save(userDto));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> auth(@RequestBody   @Valid  UserRequest userRequest) {
        return ResponseEntity.ok(authenticationService.auth(userRequest));

    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    )
            throws IOException {
        authenticationService.refreshToken(request, response);
    }


}
