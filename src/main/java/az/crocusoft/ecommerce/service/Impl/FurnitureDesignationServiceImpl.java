package az.crocusoft.ecommerce.service.Impl;

import az.crocusoft.ecommerce.dto.AddFurnitureDesignationDTO;
import az.crocusoft.ecommerce.dto.FurnitureDesignationDTO;
import az.crocusoft.ecommerce.exception.EntityExistsException;
import az.crocusoft.ecommerce.model.product.FurnitureDesignation;
import az.crocusoft.ecommerce.model.product.Image;
import az.crocusoft.ecommerce.repository.FurnitureDesignationRepository;
import az.crocusoft.ecommerce.service.FurnitureDesignationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class FurnitureDesignationServiceImpl implements FurnitureDesignationService {

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final FurnitureDesignationRepository furnitureDesignationRepository;
    private static final String FURNITURE_DESIGNATION_IMAGES_FOLDER_NAME = "Furniture-designation-images";
    private FileService fileService;

    @Override
    public FurnitureDesignationDTO addFurnitureDesignation(AddFurnitureDesignationDTO addFurnitureDesignationDTO, MultipartFile image) throws IOException {
        FurnitureDesignation furnitureDesignation = new FurnitureDesignation();
        if (furnitureDesignationRepository
                .existsByName(addFurnitureDesignationDTO
                        .getFurnitureDesignationName())
        )
            throw new EntityExistsException("Furniture designation with the name "
                    + addFurnitureDesignationDTO.getFurnitureDesignationName() + " already exists!");

        String uploadedImageURL = fileService.uploadImage(image, FURNITURE_DESIGNATION_IMAGES_FOLDER_NAME);
        Image uploadedImage = new Image(uploadedImageURL);

        furnitureDesignation.setName(addFurnitureDesignationDTO.getFurnitureDesignationName());
        furnitureDesignation.setDescription(addFurnitureDesignationDTO.getDescription());
        furnitureDesignation.setImage(uploadedImage);
        FurnitureDesignation savedFurnitureDesignation = furnitureDesignationRepository.save(furnitureDesignation);

        FurnitureDesignationDTO furnitureDesignationDTO = modelMapper.map(savedFurnitureDesignation, FurnitureDesignationDTO.class);

        return furnitureDesignationDTO;
    }

    @Override
    public FurnitureDesignationDTO getFurnitureDesignationById(Long id) {
        FurnitureDesignation furnitureDesignation = furnitureDesignationRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Furniture designation not found"));
        FurnitureDesignationDTO dto = modelMapper.map(furnitureDesignation, FurnitureDesignationDTO.class);
        return dto;
    }
}
