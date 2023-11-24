package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.dto.AddressDto;
import az.crocusoft.ecommerce.service.AddressService;
import az.crocusoft.ecommerce.service.impl.AddressServiceImpl;
import az.crocusoft.ecommerce.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController

@RequestMapping
@RequiredArgsConstructor
public class AddressController {




    private final AddressService addressService;


    @PostMapping("/user/{user_id}/address")
    public ResponseEntity<AddressDto> createAddressForUser(
            @RequestBody AddressDto addressDto, @PathVariable(name = "user_id") Long userId) {
        AddressDto createdAddress = addressService.createAddress(addressDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
        }



    @GetMapping("/{address_id}")
    public ResponseEntity<AddressDto> getAddress(@PathVariable Integer address_id){
        AddressDto addressDto =addressService.getAddress(address_id);
        return  new ResponseEntity<AddressDto>(addressDto,HttpStatus.FOUND);
    }

    @PutMapping("/update/{address_id}")
    public  ResponseEntity<AddressDto> updateAddress(@PathVariable Integer address_id,
                                                     @RequestBody AddressDto addressDto){
        AddressDto updatedAddressDto = addressService.updateAddress(address_id, addressDto);
        return new ResponseEntity<AddressDto>(updatedAddressDto,HttpStatus.OK);
    }

    @DeleteMapping("/{addres_id}")
    public ResponseEntity<String> deleteAddressById(@PathVariable Integer addres_id){
        String status = addressService.deleteAddressById(addres_id);
        return new ResponseEntity<String>(status,HttpStatus.OK);
    }

    @GetMapping("/{userId}/address")
    public ResponseEntity<List<AddressDto>> getUserAddresses(@PathVariable Long userId) {
        List<AddressDto> addresses = addressService.getAddressByUserId(userId);
        return ResponseEntity.ok(addresses);
    }


}
