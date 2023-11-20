package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.dto.CheckOutDto;
import az.crocusoft.ecommerce.model.CheckOut;
import az.crocusoft.ecommerce.service.impl.CheckOutServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController

@RequestMapping
@RequiredArgsConstructor
public class CheckOutController {




    private final CheckOutServiceImpl checkOutService;


    @PostMapping("/address")
    public ResponseEntity<CheckOutDto> createAddress(@RequestBody CheckOutDto checkOutDto) {
        CheckOutDto createdAddress = checkOutService.createAddress(checkOutDto);
        return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
    }

    @GetMapping("/{address_id}")
    public ResponseEntity<CheckOutDto> getAddress(@PathVariable Integer address_id){
        CheckOutDto checkOutDto =checkOutService.getAddress(address_id);
        return  new ResponseEntity<CheckOutDto>(checkOutDto,HttpStatus.FOUND);
    }

    @PutMapping("/update/{address_id}")
    public  ResponseEntity<CheckOutDto> updateAddress(@PathVariable Integer address_id,
                                                      @RequestBody CheckOut checkOut){
        CheckOutDto checkOutDto= checkOutService.updateAddress(address_id,checkOut);
        return new ResponseEntity<CheckOutDto>(checkOutDto,HttpStatus.OK);
    }
}
