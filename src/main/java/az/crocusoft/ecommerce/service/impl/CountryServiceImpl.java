package az.crocusoft.ecommerce.service.impl;

import az.crocusoft.ecommerce.dto.CountryDto;
import az.crocusoft.ecommerce.model.Country;
import az.crocusoft.ecommerce.repository.CountryRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl {

    private final CountryRepository countryRepository;
//    private final String filePath = "src/main/resources/countries.json";




//


    public List<CountryDto> getAllCountries() {
        List<Country> existingCountries = countryRepository.findAll();

        if (existingCountries.isEmpty()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                List<Country> countries = objectMapper.readValue(new File("src/main/resources/country.json"),
                        new TypeReference<List<Country>>() {
                        });

                countryRepository.saveAll(countries);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception if needed
            }
        }

        List<Country> countries = countryRepository.findAll();

        List<CountryDto> countryDtos = new ArrayList<>();
        for (Country country : countries) {
            countryDtos.add(new CountryDto(country.getCountry()));
        }


        return countryDtos;    }
}

