package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.model.ImageUpload;
import az.crocusoft.ecommerce.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final Path path; // The path to the directory where files will be saved

    public ImageService(@Value("${file.upload-dir}") String uploadDir, ImageRepository imageRepository) {
        this.path = Paths.get(uploadDir);
        this.imageRepository = imageRepository;
        // Ensure the directory exists
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage location", e);
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

    public boolean delete(String filename) {
        try {
            Path url = path.resolve(filename);
            return Files.deleteIfExists(url);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }



    public ImageUpload downloadFile(String fileId) throws Exception {
        return imageRepository.findById(fileId)
                .orElseThrow(() -> new Exception("A file with Id : " + fileId + " could not be found"));
    }


}