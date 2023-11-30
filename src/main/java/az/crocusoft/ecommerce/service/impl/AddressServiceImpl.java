package az.crocusoft.ecommerce.service.impl;

import az.crocusoft.ecommerce.dto.AddressDto;
import az.crocusoft.ecommerce.exception.ResourceNotFoundException;
import az.crocusoft.ecommerce.model.Address;
import az.crocusoft.ecommerce.repository.AddressRepository;
import az.crocusoft.ecommerce.service.AddressService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {


    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

     private  final AddressRepository addressRepository;

    @Override
    public AddressDto createAddress(AddressDto addressDto, Long userId) {
        Address newAddress = modelMapper.map(addressDto, Address.class);
        User user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("User not found with id: " + userId));
        newAddress.setUser(user);
        Address savedAddress = addressRepository.save(newAddress);
        return modelMapper.map(savedAddress, AddressDto.class);
    }

    @Override
    public AddressDto getAddress(Integer address_id) {
        Address address = addressRepository.findById(address_id)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", address_id));

        return modelMapper.map(address, AddressDto.class);
    }
    @Override
    @Transactional
    public AddressDto updateAddress(Integer address_id, AddressDto addressDto) {
        Address existingAddress = addressRepository.findById(address_id)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", address_id));
        modelMapper.map(addressDto, existingAddress);
        Address updatedAddress = addressRepository.save(existingAddress);
        return modelMapper.map(updatedAddress, AddressDto.class);
    }

    @Override
    @Transactional
    public String deleteAddressById(Integer address_id) {
        addressRepository.deleteById(address_id);
        return "Address deleted succesfully with addressId: " + address_id;
    }

    @Override
    public List<AddressDto> getAddressByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Address> addresses = user.getAddressList();
            System.out.println("Addresses: " + addresses);
            return addresses.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } else {
            System.out.println("Kullanıcı bulunamadı");
            return Collections.emptyList();
        }

    }

    private AddressDto convertToDto(Address address) {
        AddressDto addressDto1 = modelMapper.map(address, AddressDto.class);
        addressDto1.setUsername(address.getUser().getUsername());
        return addressDto1;
    }
}
