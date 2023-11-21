package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.dto.CheckOutDto;
import az.crocusoft.ecommerce.exception.APIException;
import az.crocusoft.ecommerce.model.CheckOut;
import az.crocusoft.ecommerce.service.impl.CheckOutServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

@Slf4j
@RestController

@RequestMapping
@RequiredArgsConstructor
public class CheckOutController {




    private final CheckOutServiceImpl checkOutService;


    @PostMapping("/user/{user_id}/address")
    public ResponseEntity<CheckOutDto> createAddressForUser(
            @RequestBody CheckOutDto checkOutDto, @PathVariable(name = "user_id") Long userId) {
        CheckOutDto createdAddress = checkOutService.createAddress(checkOutDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
        }



    @GetMapping("/{address_id}")
    public ResponseEntity<CheckOutDto> getAddress(@PathVariable Integer address_id){
        CheckOutDto checkOutDto =checkOutService.getAddress(address_id);
        return  new ResponseEntity<CheckOutDto>(checkOutDto,HttpStatus.FOUND);
    }

    @PutMapping("/update/{address_id}")
    public  ResponseEntity<CheckOutDto> updateAddress(@PathVariable Integer address_id,
                                                      @RequestBody CheckOutDto checkOutDto){
        CheckOutDto updatedCheckOutDto= checkOutService.updateAddress(address_id,checkOutDto);
        return new ResponseEntity<CheckOutDto>(updatedCheckOutDto,HttpStatus.OK);
    }



}
