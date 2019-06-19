package nl.hsleiden.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageService {
    Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private final Path rootLocation = Paths.get("upload-dir");

    public void store(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
    }

    public Resource loadFile(String fileName) {
        try{
            Path file = rootLocation.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new RuntimeException("FAILED TO LOAD FILE");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("FAIL!");
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public void delete(Resource file) {
        try {
            file.getFile().delete();
        } catch (IOException exception) {
            throw new RuntimeException("FAILED TO DELETE FILE");
        }
    }

    public void init() {
        try{
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
