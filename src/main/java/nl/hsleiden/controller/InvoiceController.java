package nl.hsleiden.controller;

import nl.hsleiden.auth.Role;
import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.Invoice;
import nl.hsleiden.repository.InvoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
public class InvoiceController {
    private final Logger LOGGER = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    private InvoiceRepository invoiceRepository;

    @GetMapping("/api/invoice")
    @RolesAllowed({Role.EMPLOYEE, Role.ADMIN})
    public Collection<Invoice> getInvoices() { return invoiceRepository.findAll(); }

    @GetMapping("/api/invoice/{invoiceNumber}")
    @RolesAllowed({Role.EMPLOYEE, Role.ADMIN})
    public Optional<Invoice> getSpecifiedInvoice(@PathVariable Long invoiceNumber) {
        LOGGER.info("Fetching invoice with number: " + invoiceNumber);
        return invoiceRepository.findById(invoiceNumber);
    }

    @PostMapping("/api/invoice")
    @RolesAllowed({Role.EMPLOYEE, Role.ADMIN})
    public Invoice createInvoice(@Valid @RequestBody Invoice invoice) {
        LOGGER.info("Creating new invoice...");
        return invoiceRepository.save(invoice);
    }

    @PutMapping("/api/invoice/{invoiceNumber}")
    @RolesAllowed({Role.EMPLOYEE, Role.ADMIN})
    public Invoice updateInvoice(@PathVariable Long invoiceNumber, @Valid @RequestBody Invoice updatedInvoice) {
        LOGGER.info("Updating invoice with number: " + invoiceNumber);
        return invoiceRepository.findById(invoiceNumber).map(invoice -> {
            invoice.setBankAccount(updatedInvoice.getBankAccount());
            invoice.setDateFullPaid(updatedInvoice.getDateFullPaid());
            invoice.setDateInvoice(updatedInvoice.getDateInvoice());
            invoice.setDateInvoiceMailSent(updatedInvoice.getDateInvoiceMailSent());
            invoice.setDatePartPaid(updatedInvoice.getDatePartPaid());
            invoice.setExcludeFromInvoiceAlert(updatedInvoice.getExcludeFromInvoiceAlert());
//            invoice.setOrderId(updatedInvoice.getOrderId());
            invoice.setOtherCosts(updatedInvoice.getOtherCosts());
            invoice.setOtherCostsBtw(updatedInvoice.getOtherCostsBtw());
            invoice.setPaid(updatedInvoice.getPaid());
            invoice.setPaymentExtras(updatedInvoice.getPaymentExtras());
            invoice.setPriceBtw(updatedInvoice.getPriceBtw());
            invoice.setPricePp(updatedInvoice.getPricePp());
            invoice.setToBePaid(updatedInvoice.getToBePaid());
            return invoiceRepository.save(invoice);
        }).orElseThrow(() -> new ResourceNotFoundException("Invoice not found with number: " + invoiceNumber));
    }

    @DeleteMapping("/api/invoice/{invoiceNumber}")
    @RolesAllowed({Role.EMPLOYEE, Role.ADMIN})
    public ResponseEntity<?> deleteInvoice(@PathVariable Long invoiceNumber) {
        LOGGER.info("Deleting invoice with number: " + invoiceNumber);
        return invoiceRepository.findById(invoiceNumber).map(invoice -> {
            invoiceRepository.delete(invoice);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Invoice not found with number: " + invoiceNumber));
    }
}
