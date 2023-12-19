package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.dto.ContactDto;
import az.crocusoft.ecommerce.exception.MailSenderException;
import az.crocusoft.ecommerce.mapper.ContactMapper;
import az.crocusoft.ecommerce.model.Contact;
import az.crocusoft.ecommerce.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactService {

//    @Value("${spring.mail.username}")
//    private String mailSenderUsername;

    private final JavaMailSender javaMailSender;
    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    public void saveContact(ContactDto contactDto) {
        Contact contact = contactMapper.dtoToEntity(contactDto);
//        sendMail(contactDto);
        contactRepository.save(contact);
        log.info("User saved successfully: {}", contactDto);
    }


//    public void sendMail(ContactDto contactDto) {
//        try {
//            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//            simpleMailMessage.setFrom(mailSenderUsername);
//            simpleMailMessage.setTo(contactDto.getEmail());
//            simpleMailMessage.setSubject(contactDto.getSubject());
//            simpleMailMessage.setText(contactDto.getMessage());
//
//            javaMailSender.send(simpleMailMessage);
//            log.info("User saved successfully: {}", contactDto);
//        } catch (MailSenderException e) {
//            log.error("Error occurred while sending mail for customer: {}", contactDto, e);
//            throw new MailSenderException("Error while sending email");
//        }
//    }
}
