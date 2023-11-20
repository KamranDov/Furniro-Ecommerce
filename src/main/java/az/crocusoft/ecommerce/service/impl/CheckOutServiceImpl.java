package az.crocusoft.ecommerce.service.impl;

import az.crocusoft.ecommerce.dto.CheckOutDto;
import az.crocusoft.ecommerce.exception.APIException;
import az.crocusoft.ecommerce.exception.ResourceNotFoundException;
import az.crocusoft.ecommerce.model.CheckOut;
import az.crocusoft.ecommerce.repository.CheckOutRepository;
import az.crocusoft.ecommerce.service.CheckOutService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckOutServiceImpl implements CheckOutService {


    private CheckOutDto checkOutDto;
    @Autowired
    private CheckOutRepository checkOutRepository;


    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CheckOutDto createAddress(CheckOutDto checkOutDto) {


        String firstName =checkOutDto.getFirstName();
        String lastName = checkOutDto.getLastName();
        String companyHome = checkOutDto.getCompanyHome();
        String country = checkOutDto.getCountry();
        String streetAddress = checkOutDto.getStreetAddress();
        String city = checkOutDto.getCity();
        String province = checkOutDto.getProvince();
        String zipcode = checkOutDto.getZipcode();
        String phone = checkOutDto.getPhone();
        String email = checkOutDto.getEmail();
        String information = checkOutDto.getInformation();

        CheckOut checkOutFromDB =checkOutRepository.
                findByFirstNameAndLastNameAndCompanyHomeAndCountryAndStreetAddressAndCityAndProvinceAndZipcodeAndPhoneAndEmailAndInformation(
                firstName,
                lastName,
                companyHome,
                country,
                streetAddress,
                city,
                province,
                zipcode,
                phone,
                email ,
                        information);

        // private String firstName;
//    private String lastName;
//
//    private String companyHome;
//    private String country;
//    private String streetAddress;
//    private String city;
//    private String province;
//    private String zipcode;
//    private String phone;
//    private String email;
//    private  String information;

        if (checkOutFromDB != null) {
            throw new APIException("Address already exists with addressId: "
                    + checkOutFromDB.getAddress_id());
        }

        CheckOut checkOut =modelMapper.map(checkOutDto, CheckOut.class);
        CheckOut saveCheckOut =checkOutRepository.save(checkOut);
        return modelMapper.map(saveCheckOut,CheckOutDto.class);
    }






    @Override
    public CheckOutDto getAddress(Integer address_id) {
        CheckOut address = checkOutRepository.findById(address_id)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", address_id));

        return modelMapper.map(address, CheckOutDto.class);
    }

    @Override
    public CheckOutDto updateAddress(Integer address_id, CheckOut checkOut) {
        CheckOut checkOutFromDB = checkOutRepository.
                findByFirstNameAndLastNameAndCompanyHomeAndCountryAndStreetAddressAndCityAndProvinceAndZipcodeAndPhoneAndEmailAndInformation(
                        checkOut.getFirstName(),
                        checkOut.getLastName(),
                        checkOut.getCompanyHome(),
                        checkOut.getEmail(),
                        checkOut.getCountry(),
                        checkOut.getStreetAddress(),
                        checkOut.getCity(),
                        checkOut.getZipcode(),checkOut.getPhone(),
                        checkOut.getEmail(),
                        checkOut.getInformation());
        if (checkOutFromDB == null) {
            checkOutFromDB = checkOutRepository.findById(address_id)
                    .orElseThrow(() -> new ResourceNotFoundException
                            ("Address", "addressId", address_id));


            checkOutFromDB.setFirstName(checkOut.getFirstName());
            checkOutFromDB.setLastName(checkOut.getLastName());
            checkOutFromDB.setCompanyHome(checkOut.getCompanyHome());
            checkOutFromDB.setCountry(checkOut.getCountry());
            checkOutFromDB.setStreetAddress(checkOut.getStreetAddress());
            checkOutFromDB.setCity(checkOut.getCity());
            checkOutFromDB.setProvince(checkOut.getProvince());
            checkOutFromDB.setZipcode(checkOut.getZipcode());
            checkOutFromDB.setPhone(checkOut.getPhone());
            checkOutFromDB.setEmail(checkOut.getEmail());
            checkOutFromDB.setInformation(checkOut.getInformation());

            CheckOut updatedAddress = checkOutRepository.save(checkOutFromDB);

            return modelMapper.map(updatedAddress, CheckOutDto.class);
        }


        return checkOutDto;
    }}
