package nl.hsleiden.controller;

import nl.hsleiden.model.*;
import nl.hsleiden.repository.*;
import nl.hsleiden.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class FileUploadController  {

    @Autowired
    StorageService storageService;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventImageRepository eventImageRepository;

    @Autowired
    private EventLocationRepository eventLocationRepository;

    @Autowired
    private CateringImageRepository cateringImageController;

    @Autowired
    private CateringRepository cateringRepository;

    List<String> files = new ArrayList<>();

    @PostMapping("/api/images/post/{nameOfEntity}/{entityId}")
    public ResponseEntity<String> handleFileUpload(@PathVariable String nameOfEntity, @PathVariable Long entityId, @RequestParam("file") MultipartFile file) {

        String message = "";
        String entityToLowerCase = nameOfEntity.toLowerCase();

        try {
            storageService.store(file);
            files.add(file.getOriginalFilename());

            saveImagePathToDatabase(file.getOriginalFilename(), nameOfEntity, entityId);

            message = "You successfully uploaded " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.OK).body(message);

        } catch (Exception e) {
            message = "FAIL to upload " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @GetMapping("/api/images")
    public ResponseEntity<List<String>> getListFiles(Model model) {
        List<String> fileNames = files
                .stream().map(fileName -> MvcUriComponentsBuilder
                        .fromMethodName(FileUploadController.class, "getFile", fileName).build().toString())
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(fileNames);
    }

    @GetMapping("/api/images/{supplierId}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable Long supplierId) {

        Supplier supplier = supplierRepository.getOne(supplierId);

        Resource file = storageService.loadFile(supplier.getImage());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    /**
     * THis is for storing imagepaths to the database.
     * @param imagePath path to an image
     * @param entity tablename
     * @param id id of entityobject
     */
    private void saveImagePathToDatabase(String imagePath, String entity, Long id) {
        // This is for storing imagePaths to the database.
        switch (entity.toLowerCase()) {
            case "events":
                System.out.println("Event image ready to be stored.");

                Event event = eventRepository.getOne(id);
                EventImage eventImage = new EventImage();
                eventImage.setImagePath(imagePath);
                eventImage.setEvent(event);
                eventImageRepository.save(eventImage);
                break;
            case "event_location":

                System.out.println("Routepicture ready to be stored.");

                EventLocation eventLocation = eventLocationRepository.getOne(id);
                eventLocation.setRoutePicture(imagePath);
                eventLocationRepository.save(eventLocation);
                break;
            case "supplier":

                System.out.println("Supplier image ready to be stored.");

                Supplier supplier = supplierRepository.getOne(id);
                supplier.setImage(imagePath);
                supplierRepository.save(supplier);
                break;
            default:
                System.out.println("Catering image ready to be stored.");

                Catering catering = cateringRepository.getOne(id);
                CateringImage cateringImage = new CateringImage();
                cateringImage.setImage(imagePath);
                cateringImage.setCatering(catering);
                cateringImageController.save(cateringImage);
                break;
        }
    }

}
