package az.crocusoft.ecommerce.service.Impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileService {

    private final Path rootLocation; // The path to the directory where files will be saved

    public FileService(@Value("${file.upload-dir}") String uploadDir) {
        this.rootLocation = Paths.get(uploadDir);
        // Ensure the directory exists
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage location", e);
        }
    }

    public String uploadImage(MultipartFile file, String subDirectoryName) throws IOException {
        // Ensure the file is not empty, validate file type, etc.
        String originalFileName = file.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
        // Create the subdirectory if it doesn't exist
        Path subdirectory = rootLocation.resolve(subDirectoryName);
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

//    public MultipartFile downloadImage(String fileName) throws IOException {
//
//    }
}
