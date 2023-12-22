package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.dto.ContactDto;
import az.crocusoft.ecommerce.model.Contact;
import az.crocusoft.ecommerce.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/contact")
public class ContactController {

    private final ContactService contactService;

    @PostMapping("/send-mail")
    public ResponseEntity<Map<String,String>> sendMail(@Valid @RequestBody ContactDto contactDto) {
        contactService.saveContact(contactDto);
        Map<String,String> messageObject = new HashMap<>();
        messageObject.put("message", "User saved successfully");
        return new ResponseEntity<>(messageObject, CREATED);
    }
}

