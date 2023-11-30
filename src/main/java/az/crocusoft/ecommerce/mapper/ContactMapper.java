package az.crocusoft.ecommerce.mapper;


import az.crocusoft.ecommerce.dto.ContactDto;
import az.crocusoft.ecommerce.model.Contact;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactMapper {

      Contact dtoToEntity(ContactDto contactDto);
}
