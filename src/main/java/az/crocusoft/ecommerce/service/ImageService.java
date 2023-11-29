package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.model.ImageUpload;
import az.crocusoft.ecommerce.repository.ImageRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
@RequiredArgsConstructor
//@AllArgsConstructor
//@ConfigurationProperties(prefix = "file")
public class ImageService {

    private final ImageRepository imageRepository;

    @Value("${file.downloadPath}")
     String downloadPath;

    @Value("${file.uploadPath}")
    String uploadPath;



    private final Path root = Paths.get("C:\\Users\\Admin\\Pictures\\Screenshots\\");

    public boolean delete(String filename) {
        try {
            Path file = root.resolve(filename);
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public ImageUpload saveFile(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new Exception("The file name is invalid" + fileName);
            }
            ImageUpload fileUpload = new ImageUpload(fileName, file.getContentType(), file.getBytes());
            return imageRepository.save(fileUpload);
        } catch (Exception e) {
            throw new Exception("File could not be save");
        }
    }

    public ImageUpload downloadFile(String fileId) throws Exception {
        return imageRepository.findById(fileId)
                .orElseThrow(() -> new Exception("A file with Id : " + fileId + " could not be found"));
    }




}
