package nl.hsleiden.controller;

import nl.hsleiden.auth.Role;
import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.*;
import nl.hsleiden.repository.*;
import nl.hsleiden.service.CollectionDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
public class OrderController {
    private final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CateringOrderRepository cateringOrderRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private QuotationRepository quotationRepository;

    CollectionDataService<Invoice> invoiceCollectionDataService = new CollectionDataService<>();
    CollectionDataService<CateringOrder> cateringOrderCollectionDataService = new CollectionDataService<>();
    CollectionDataService<Quotation> quotationCollectionDataService = new CollectionDataService<>();

    @GetMapping("/api/orders")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    public Collection<Order> getOrders() { return orderRepository.findAll(); }

    @GetMapping("/api/orders/{orderId}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    public Optional<Order> getSpecifiedCatering(@PathVariable Long orderId) {
        LOGGER.info("Fetching order with id: " + orderId);
        return orderRepository.findById(orderId);
    }

    @PostMapping("/api/orders")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    public Order createOrder(@Valid @RequestBody Order order) {
        LOGGER.info("Creating new order...");
        Order savedOrder = orderRepository.save(order);

        this.saveCateringOrders(savedOrder, order.getCateringOrders());
        this.saveInvoices(savedOrder, order.getInvoices());
        this.saveQuotations(savedOrder, order.getQuotations());

        return orderRepository.save(order);
    }

    @PutMapping("/api/orders/{orderId}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    public Order updateOrder(@PathVariable Long orderId, @Valid @RequestBody Order updatedOrder) {
        LOGGER.info("Updating order with id: " + orderId);
        return orderRepository.findById(orderId).map(order -> {
            order.setEvent(updatedOrder.getEvent());
            order.setDateEvent(updatedOrder.getDateEvent());
            order.setDateOrder(updatedOrder.getDateOrder());
            order.setEndTime(updatedOrder.getEndTime());
            order.setNote(updatedOrder.getNote());
            order.setStartTime(updatedOrder.getStartTime());

            Collection<Invoice> invoicesToBeSaved = invoiceCollectionDataService.getToBeSaved(order.getInvoices(), updatedOrder.getInvoices());
            Collection<Invoice> invoicesToBeDeleted = invoiceCollectionDataService.getToBeDeleted(order.getInvoices(), updatedOrder.getInvoices());

            Collection<CateringOrder> cateringOrdersToBeSaved = cateringOrderCollectionDataService.getToBeSaved(order.getCateringOrders(), updatedOrder.getCateringOrders());
            Collection<CateringOrder> cateringOrdersToBeDeleted = cateringOrderCollectionDataService.getToBeDeleted(order.getCateringOrders(), updatedOrder.getCateringOrders());

            Collection<Quotation> quotationsToBeSaved = quotationCollectionDataService.getToBeSaved(order.getQuotations(), updatedOrder.getQuotations());
            Collection<Quotation> quotationsToBeDeleted = quotationCollectionDataService.getToBeDeleted(order.getQuotations(), updatedOrder.getQuotations());

            saveInvoices(order, invoicesToBeSaved);
            deleteInvoices(invoicesToBeDeleted);

            saveCateringOrders(order, cateringOrdersToBeSaved);
            deleteCateringOrders(cateringOrdersToBeDeleted);

            saveQuotations(order, quotationsToBeSaved);
            deleteQuotations(quotationsToBeDeleted);

            order.setInvoices(
                    invoiceCollectionDataService.getDefinitiveCollection(order.getInvoices(), invoicesToBeSaved, invoicesToBeDeleted)
            );
            order.setCateringOrders(
                    cateringOrderCollectionDataService.getDefinitiveCollection(order.getCateringOrders(), cateringOrdersToBeSaved, cateringOrdersToBeDeleted)
            );
            order.setQuotations(
                    quotationCollectionDataService.getDefinitiveCollection(order.getQuotations(), quotationsToBeSaved, quotationsToBeDeleted)
            );

            return orderRepository.save(order);
        }).orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
    }

    @DeleteMapping("/api/orders/{orderId}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId) {
        LOGGER.info("Deleting order with id: " + orderId);
        return orderRepository.findById(orderId).map(order -> {
            orderRepository.delete(order);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
    }

    private void saveInvoices(Order order, Collection<Invoice> toBeSaved) {
        LOGGER.info("Saving invoices...");
        try {
            for (Invoice invoice : toBeSaved) {
                invoice.setOrder(order);
            }
            invoiceRepository.saveAll(toBeSaved);
        } catch (NullPointerException exception) {
            LOGGER.info("Unable to save invoices");
        }
    }

    private void deleteInvoices(Collection<Invoice> toBeDeleted) {
        LOGGER.info("Deleting invoices...");
        try {
            invoiceRepository.deleteAll(toBeDeleted);
        } catch (NullPointerException exception) {
            LOGGER.info("Unable to delete invoices");
        }
    }

    private void saveCateringOrders(Order order, Collection<CateringOrder> toBeSaved) {
        LOGGER.info("Saving catering orders...");
        try {
            for (CateringOrder cateringOrder : toBeSaved) {
                cateringOrder.setOrder(order);
            }
            cateringOrderRepository.saveAll(toBeSaved);
        } catch (NullPointerException exception) {
            LOGGER.info("Unable to save catering orders");
        }
    }

    private void deleteCateringOrders(Collection<CateringOrder> toBeDeleted) {
        LOGGER.info("Deleting catering orders...");
        try {
            cateringOrderRepository.deleteAll(toBeDeleted);
        } catch (NullPointerException exception) {
            LOGGER.info("Unable to delete catering orders");
        }
    }

    private void saveQuotations(Order order, Collection<Quotation> toBeSaved) {
        LOGGER.info("Saving quotations...");
        try {
            for (Quotation quotation : toBeSaved) {
                quotation.setOrder(order);
            }
            quotationRepository.saveAll(toBeSaved);
        } catch (NullPointerException exception) {
            LOGGER.info("Unable to save quotations");
        }
    }

    private void deleteQuotations(Collection<Quotation> toBeDeleted) {
        LOGGER.info("Deleting quotations...");
        try {
            quotationRepository.deleteAll(toBeDeleted);
        } catch (NullPointerException exception) {
            LOGGER.info("Unable to delete quotations");
        }
    }
}
