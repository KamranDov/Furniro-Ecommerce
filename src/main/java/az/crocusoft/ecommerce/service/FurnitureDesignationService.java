package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.dto.request.FurnitureDesignationRequest;
import az.crocusoft.ecommerce.dto.FurnitureDesignationDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FurnitureDesignationService {
    void addFurnitureDesignation(FurnitureDesignationRequest furnitureDesignationRequest, MultipartFile image) throws IOException;

    FurnitureDesignationDTO getFurnitureDesignationById(Long id);
}
