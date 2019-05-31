package nl.hsleiden.controller;

import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.EmailText;
import nl.hsleiden.repository.EmailTextRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
public class EmailTextController {

    private final Logger LOGGER = LoggerFactory.getLogger(EmailTextController.class);

    @Autowired
    private EmailTextRepository emailTextRepository;

    @GetMapping("/api/emailtexts")
    public Collection<EmailText> getEmailTexts() { return emailTextRepository.findAll(); }

    @GetMapping("/api/emailtexts/{id}")
    public Optional<EmailText> getSpecificEmailTexts(@PathVariable Long id) { return emailTextRepository.findById(id); }

    @PostMapping("/api/emailtexts")
    public EmailText createEmailText(@RequestBody EmailText emailText) {
        LOGGER.info("Creating an email text object");
        return emailTextRepository.save(emailText);
    }

    @PutMapping("/api/emailtexts/{id}")
    public EmailText updateEmailText(@PathVariable Long id, @RequestBody EmailText updatedEmailText) {
        LOGGER.info("Updating email text object of id " + id);
        return emailTextRepository.findById(id).map(emailText -> {
            emailText.setEmailText(updatedEmailText.getEmailText());
            emailText.setEmailType(updatedEmailText.getEmailType());
            return emailTextRepository.save(emailText);
        }).orElseThrow(() -> new ResourceNotFoundException("No email text object found of id " + id));
    }

    @DeleteMapping("/api/emailtexts/{id}")
    public ResponseEntity<?> deleteEmailText(@PathVariable Long id) {
        LOGGER.info("Deleting email text object of id " + id);
        return emailTextRepository.findById(id).map(emailText -> {
            emailTextRepository.delete(emailText);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("No email text object found of id " + id));
    }
}
