package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.dto.CheckOutDto;
import az.crocusoft.ecommerce.model.CheckOut;

import java.util.List;

public interface CheckOutService {

    CheckOutDto createAddress(CheckOutDto checkOutDto);


    CheckOutDto getAddress(Integer address_id);

    CheckOutDto updateAddress(Integer address_id, CheckOutDto checkOutDto);


}
