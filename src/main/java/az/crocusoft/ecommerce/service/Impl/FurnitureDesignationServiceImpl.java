package az.crocusoft.ecommerce.service.Impl;

import az.crocusoft.ecommerce.dto.FurnitureDesignationDTO;
import az.crocusoft.ecommerce.dto.request.FurnitureDesignationRequest;
import az.crocusoft.ecommerce.exception.EntityExistsException;
import az.crocusoft.ecommerce.model.product.FurnitureDesignation;
import az.crocusoft.ecommerce.model.product.Image;
import az.crocusoft.ecommerce.repository.FurnitureDesignationRepository;
import az.crocusoft.ecommerce.service.FurnitureDesignationService;
import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FurnitureDesignationServiceImpl implements FurnitureDesignationService {

    private final ModelMapper modelMapper;
    private final FurnitureDesignationRepository furnitureDesignationRepository;
    private final

    FileService fileService;
    private static final String FURNITURE_DESIGNATION_IMAGES_FOLDER_NAME = "Furniture-designation-images";

    @Override
    public void addFurnitureDesignation(FurnitureDesignationRequest furnitureDesignationRequest, MultipartFile image) throws IOException {
        FurnitureDesignation furnitureDesignation = new FurnitureDesignation();
        if (furnitureDesignationRepository
                .existsByName(furnitureDesignationRequest
                        .getFurnitureDesignationName())
        )
            throw new EntityExistsException("Furniture designation with the name "
                    + furnitureDesignationRequest.getFurnitureDesignationName() + " already exists!");

        String uploadedImageURL = fileService.uploadImage(image, FURNITURE_DESIGNATION_IMAGES_FOLDER_NAME);
        Image uploadedImage = new Image(uploadedImageURL);

        furnitureDesignation.setName(furnitureDesignationRequest.getFurnitureDesignationName());
        furnitureDesignation.setDescription(furnitureDesignationRequest.getDescription());
        furnitureDesignation.setImage(uploadedImage);
        FurnitureDesignation savedFurnitureDesignation = furnitureDesignationRepository.save(furnitureDesignation);
    }

    @Override
    public FurnitureDesignationDTO getFurnitureDesignationById(Long id) {
        FurnitureDesignation furnitureDesignation = furnitureDesignationRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Furniture designation not found"));
        FurnitureDesignationDTO dto = modelMapper.map(furnitureDesignation, FurnitureDesignationDTO.class);
        return dto;
    }

    @Override
    public List<FurnitureDesignationDTO> getAllFurnitureDesignation() {
        List<FurnitureDesignationDTO> all = furnitureDesignationRepository
                .findAll()
                .stream()
                .map(furnitureDesignation -> modelMapper
                        .map(furnitureDesignation, FurnitureDesignationDTO.class))
                .toList();
        return all;
    }

}
