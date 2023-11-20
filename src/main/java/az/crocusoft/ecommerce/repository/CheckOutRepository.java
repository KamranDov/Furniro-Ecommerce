package az.crocusoft.ecommerce.repository;

import az.crocusoft.ecommerce.model.CheckOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckOutRepository extends JpaRepository<CheckOut,Integer> {




    CheckOut  findByFirstNameAndLastNameAndCompanyHomeAndCountryAndStreetAddressAndCityAndProvinceAndZipcodeAndPhoneAndEmailAndInformation(
            String firstName,
            String lastName,
            String companyHome,
            String country,
            String streetAddress,
            String city,
            String province,
            String zipcode,
            String phone,
            String email,
            String information



    );
}
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