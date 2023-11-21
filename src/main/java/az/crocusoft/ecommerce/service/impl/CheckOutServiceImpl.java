package az.crocusoft.ecommerce.service.impl;

import az.crocusoft.ecommerce.dto.CheckOutDto;
import az.crocusoft.ecommerce.exception.APIException;
import az.crocusoft.ecommerce.exception.ResourceNotFoundException;
import az.crocusoft.ecommerce.model.CheckOut;
import az.crocusoft.ecommerce.model.User;
import az.crocusoft.ecommerce.repository.CheckOutRepository;
import az.crocusoft.ecommerce.repository.UserRepository;
import az.crocusoft.ecommerce.service.CheckOutService;
import jakarta.persistence.EntityNotFoundException;
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
    private UserRepository userRepository;


    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CheckOutDto createAddress( CheckOutDto checkOutDto , Long userId) {
        // CheckOutDto'dan CheckOut nesnesi oluştur
        CheckOut newCheckOut = modelMapper.map(checkOutDto, CheckOut.class);

        User user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("User not found with id: " + userId));

        // Oluşturulan CheckOut nesnesine ilgili kullanıcıyı ata
        newCheckOut.setUser(user);

        // Oluşturulan CheckOut nesnesini repository'e kaydet
        CheckOut savedCheckOut = checkOutRepository.save(newCheckOut);

        // Kaydedilen CheckOut nesnesini CheckOutDto'ya dönüştür ve geri döndür

        return modelMapper.map(savedCheckOut,CheckOutDto.class);
    }






    @Override
    public CheckOutDto getAddress(Integer address_id) {
        CheckOut address = checkOutRepository.findById(address_id)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", address_id));

        return modelMapper.map(address, CheckOutDto.class);
    }

    @Override
    public CheckOutDto updateAddress(Integer address_id, CheckOutDto checkOutDto) {
        Optional<CheckOut> checkOutFromDBOptional = checkOutRepository.findById(address_id);

        if (checkOutFromDBOptional.isPresent()) {
            CheckOut checkOutFromDB = checkOutFromDBOptional.get();


            checkOutFromDB.setFirstName(checkOutDto.getFirstName());
            checkOutFromDB.setLastName(checkOutDto.getLastName());
            checkOutFromDB.setCompanyHome(checkOutDto.getCompanyHome());
            checkOutFromDB.setCountry(checkOutDto.getCountry());
            checkOutFromDB.setStreetAddress(checkOutDto.getStreetAddress());
            checkOutFromDB.setCity(checkOutDto.getCity());
            checkOutFromDB.setProvince(checkOutDto.getProvince());
            checkOutFromDB.setZipcode(checkOutDto.getZipcode());
            checkOutFromDB.setPhone(checkOutDto.getPhone());
            checkOutFromDB.setEmail(checkOutDto.getEmail());
            checkOutFromDB.setInformation(checkOutDto.getInformation());

            CheckOut updatedAddress = checkOutRepository.save(checkOutFromDB);

            return modelMapper.map(updatedAddress, CheckOutDto.class);
        }
        else {
            throw new ResourceNotFoundException("Address", "addressId", address_id);

        }


    }}
