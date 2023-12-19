package az.crocusoft.ecommerce.mapper;

import az.crocusoft.ecommerce.dto.ContactDto;
import az.crocusoft.ecommerce.model.Contact;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class ContactMapperTest {

    @Test
    void dtoToEntity() {

        ContactMapper contactMapper = new ContactMapperImpl();
//            ContactMapper contactMapper = Mappers.getMapper(ContactMapper.class);

        //given or arrange
        var contactDto = new ContactDto();
        contactDto.setName("test name");
        contactDto.setEmail("test email");
        contactDto.setSubject("test subject");
        contactDto.setMessage("test message");

        var expectedContact = new Contact();
        expectedContact.setName("test name");
        expectedContact.setEmail("test email");
        expectedContact.setSubject("test subject");
        expectedContact.setMessage("test message");

        //when or actual
        var actualContact = contactMapper.dtoToEntity(contactDto);

        //then or assert
        Assertions.assertThat(actualContact).isEqualTo(expectedContact);

    }
}