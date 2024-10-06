package com.backend.delivery.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.backend.delivery.utils.File;
import com.backend.delivery.utils.FileProperties;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileService {
    
    private Path fileLocation;
    
    public FileService(FileProperties fileProprities) {
        this.fileLocation = Paths.get(fileProprities.getUploadDir())
            .toAbsolutePath()
            .normalize();
    }

    public String upload(MultipartFile file) {
        log.info("Uploading image...");
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            Path targetImage = fileLocation.resolve(filename);

            file.transferTo(targetImage);

            return filename;
        } catch (IOException e) {
            return null;
        }
    }

    public File donwload(String filename, HttpServletRequest request) throws IOException {

        log.info("donwloading image...");
        try {
            Path filePath = fileLocation.resolve(filename);
            Resource resource = new UrlResource(filePath.toUri());
            String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            
            if(contentType == null) {
                contentType = "application/octet-stream";
            }

            return new File(resource, contentType);

        } catch (MalformedURLException e) {
            return null;
        }
    }
}
