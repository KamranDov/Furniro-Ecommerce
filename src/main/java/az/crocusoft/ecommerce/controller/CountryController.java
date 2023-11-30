package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.dto.CountryDto;
import az.crocusoft.ecommerce.service.impl.CountryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class CountryController {


    private final CountryServiceImpl countryService;
   @GetMapping("/countries")
    public List<CountryDto> getAllCountries(){
       return countryService.getAllCountries();

   }

}
