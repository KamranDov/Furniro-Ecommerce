package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.dto.AddFurnitureDesignationDTO;
import az.crocusoft.ecommerce.dto.CategoryDTO;
import az.crocusoft.ecommerce.dto.FurnitureDesignationDTO;
import az.crocusoft.ecommerce.model.product.Category;
import az.crocusoft.ecommerce.model.product.FurnitureDesignation;
import az.crocusoft.ecommerce.service.FurnitureDesignationService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/designation")
public class FurnitureDesignationController {
    @Autowired
    FurnitureDesignationService furnitureDesignationService;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping(consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.CREATED)
    public void addFurnitureDesignation(
            AddFurnitureDesignationDTO addFurnitureDesignationDTO,
            @RequestParam("image") MultipartFile image) throws IOException {

        var furnitureDesignationDTO = furnitureDesignationService
                .addFurnitureDesignation(addFurnitureDesignationDTO, image);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FurnitureDesignationDTO> getFurnitureDesignation(@PathVariable Long id) {
        FurnitureDesignationDTO furnitureDesignationDTO = furnitureDesignationService.getFurnitureDesignationById(id);
        return ResponseEntity.ok(furnitureDesignationDTO);
    }


}
