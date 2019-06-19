package nl.hsleiden.controller;

import com.sun.mail.iap.ByteArray;
import nl.hsleiden.auth.Role;
import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.Quotation;
import nl.hsleiden.pdf.PdfGenaratorUtil;
import nl.hsleiden.repository.QuotationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.WebApplicationException;
import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class QuotationController {
    @Autowired
    public PdfGenaratorUtil pdfGenaratorUtil;
    private final Logger LOGGER = LoggerFactory.getLogger(QuotationController.class);

    @Autowired
    private QuotationRepository quotationRepository;

    @GetMapping("/api/quotation")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "') or hasAuthority('" + Role.INSTRUCTOR + "')")
    public Collection<Quotation> getQuotations() {
        return quotationRepository.findAll();
    }

    @GetMapping("/api/quotation/{quotationNumber}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    public Optional<Quotation> getSpecifiedQuotation(@PathVariable Long quotationNumber) {
        LOGGER.info("Fetching quotation with number: " + quotationNumber);
        return quotationRepository.findById(quotationNumber);
    }

    @PostMapping("/api/quotation")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    public Quotation createQuotation(@Valid @RequestBody Quotation quotation) {
        LOGGER.info("Creating new quotation...");
        return quotationRepository.save(quotation);
    }

//    @GetMapping(value = "/api/pdf", headers = "Accept=*/*", produces = "application/pdf")
//    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
//    public @ResponseBody
//    byte[] getPDF() throws WebApplicationException, IOException {
//        Map<String, String> data = new HashMap<String, String>();
//        data.put("name", "James");
//        File file = pdfGenaratorUtil.createPdf("quotation", data);
//        byte[] bos = readFileToByteArray(file);
////        bos.writeTo(file);
//        return bos;
//
//    }
//
////    private byte[] readFileToByteArray(File file) {
////        FileInputStream fis = null;
////        byte[] bos = new byte[(int) file.length()]
////    }

    @PutMapping("/api/quotation/{quotationNumber}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
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
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    public ResponseEntity<?> deleteQuotation(@PathVariable Long quotationNumber) {
        LOGGER.info("Deleting quotation with id: " + quotationNumber);
        return quotationRepository.findById(quotationNumber).map(quotation -> {
            quotationRepository.delete(quotation);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Quotation not found with number: " + quotationNumber));
    }
}
