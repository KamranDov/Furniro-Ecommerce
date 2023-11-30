package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.dto.AddressDto;

import java.util.List;

public interface AddressService {

    AddressDto createAddress(AddressDto addressDto, Long userId);
    AddressDto getAddress(Integer address_id);

    AddressDto updateAddress(Integer address_id, AddressDto addressDto);


    String deleteAddressById(Integer address_id);
    public List<AddressDto> getAddressByUserId(Long userId);
}
