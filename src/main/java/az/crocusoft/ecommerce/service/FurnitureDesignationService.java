package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.dto.AddFurnitureDesignationDTO;
import az.crocusoft.ecommerce.dto.FurnitureDesignationDTO;
import az.crocusoft.ecommerce.model.product.FurnitureDesignation;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FurnitureDesignationService {
    FurnitureDesignationDTO addFurnitureDesignation(AddFurnitureDesignationDTO addFurnitureDesignationDTO, MultipartFile image) throws IOException;

    FurnitureDesignationDTO getFurnitureDesignationById(Long id);
}
