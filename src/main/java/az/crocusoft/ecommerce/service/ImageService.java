package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.model.ImageUpload;
import az.crocusoft.ecommerce.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;


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


//    public ImageUpload saveFile(MultipartFile file) throws Exception {
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        System.out.println(fileName);
//        try {
//            if (fileName.contains("..")) {
//                throw new Exception("The file name is invalid" + fileName);
//            }
//            ImageUpload fileUpload = new ImageUpload(fileName, file.getContentType(), file.getBytes());
//            return imageRepository.save(fileUpload);
//        } catch (Exception e) {
//            throw new Exception("File could not be save");
//        }
//    }
public String uploadImage(MultipartFile file, String subDirectoryName) throws IOException {
    // Ensure the file is not empty, validate file type, etc.
    String originalFileName = file.getOriginalFilename();
    String randomId = UUID.randomUUID().toString();
    String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
    // Create the subdirectory if it doesn't exist
    Path subdirectory = path.resolve(subDirectoryName);
    Files.createDirectories(subdirectory);

    // Construct the path where the file will be saved
    Path destinationFile = subdirectory.resolve(
                    Paths.get(fileName))
            .normalize().toAbsolutePath();

    // Save the file
    InputStream inputStream = file.getInputStream();
    Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);


    // Return the path or URL to access the file
    return "/images/" + subDirectoryName + "/" + fileName;
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