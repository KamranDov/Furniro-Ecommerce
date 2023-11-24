package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.dto.AddressDto;
import az.crocusoft.ecommerce.dto.OrderDto;
import az.crocusoft.ecommerce.model.Order;
import jakarta.transaction.Transactional;

import java.util.List;

public interface OrderService {

    AddressDto createAddress(AddressDto addressDto, Long userId);


    @Transactional
    Order placeOrder(OrderDto orderDto);

    AddressDto getAddress(Integer address_id);

    AddressDto updateAddress(Integer address_id, AddressDto addressDto);


    String deleteAddressById(Integer address_id);
    public List<AddressDto> getAddressByUserId(Long userId);



    }
