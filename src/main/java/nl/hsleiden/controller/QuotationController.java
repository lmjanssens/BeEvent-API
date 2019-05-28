package nl.hsleiden.controller;

import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.Quotation;
import nl.hsleiden.repository.QuotationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
public class QuotationController {
    private final Logger LOGGER = LoggerFactory.getLogger(QuotationController.class);

    @Autowired
    private QuotationRepository quotationRepository;

    @GetMapping("/api/quotation")
    public Collection<Quotation> getQuotations() { return quotationRepository.findAll(); }

    @GetMapping("/api/quotation/{quotationNumber}")
    public Optional<Quotation> getSpecifiedQuotation(@PathVariable Long quotationNumber) {
        LOGGER.info("Fetching quotation with number: " + quotationNumber);
        return quotationRepository.findById(quotationNumber);
    }

    @PostMapping("/api/quotation")
    public Quotation createQuotation(@Valid @RequestBody Quotation quotation) {
        LOGGER.info("Creating new quotation...");
        return quotationRepository.save(quotation);
    }

    @PutMapping("/api/quotation/{quotationNumber}")
    public Quotation updateQuotation(@PathVariable Long quotationNumber,
                                     @Valid @RequestBody Quotation updatedQuotation) {
        LOGGER.info("Updating quotation with number: " + quotationNumber);
        return quotationRepository.findById(quotationNumber).map(quotation -> {
            quotation.setBankAccount(updatedQuotation.getBankAccount());
            quotation.setDateQuotation(updatedQuotation.getDateQuotation());
//            quotation.setOrderId(updatedQuotation.getOrderId());
            quotation.setPriceBtw(updatedQuotation.getPriceBtw());
            quotation.setPricePp(updatedQuotation.getPricePp());
            return quotationRepository.save(quotation);
        }).orElseThrow(() -> new ResourceNotFoundException("Quotation not found with number: " + quotationNumber));
    }

    @DeleteMapping("/api/quotation/{quotationNumber}")
    public ResponseEntity<?> deleteQuotation(@PathVariable Long quotationNumber) {
        LOGGER.info("Deleting quotation with id: " + quotationNumber);
        return quotationRepository.findById(quotationNumber).map(quotation -> {
            quotationRepository.delete(quotation);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Quotation not found with number: " + quotationNumber));
    }
}
