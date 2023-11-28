package az.crocusoft.ecommerce.controller;


import az.crocusoft.ecommerce.dto.ImageResponse;

import az.crocusoft.ecommerce.model.ImageUpload;
import az.crocusoft.ecommerce.service.ImageService;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping
@AllArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/upload")
    public ImageResponse uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        ImageUpload attachment = null;
        String downloadUrl = "";
        attachment = imageService.saveFile(file);
        downloadUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/download/")
                .path(attachment.getId())
                .toUriString();
        return new ImageResponse(attachment.getFileName(), downloadUrl, file.getContentType(), file.getSize());

    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity download(@PathVariable("fileId") String fileId) throws Exception {
        ImageUpload fileUpload = null;
        fileUpload = imageService.downloadFile(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileUpload.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "fileUpload; filename=\"" + fileUpload.getFileName()
                        + "\"").body(new ByteArrayResource(fileUpload.getData()));
    }


    @DeleteMapping("/{filename:.+}")
    public ResponseEntity<String> deleteFile(@PathVariable String filename) {
        String message = "";
        try {
            boolean existed = imageService.delete(filename);

            if (existed) {
                message = "Delete the file successfully: " + filename;
                return ResponseEntity.status(HttpStatus.OK).body(message);
            }

            message = "The file does not exist!";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } catch (Exception e) {
            message = "Could not delete the file: " + filename + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }
}