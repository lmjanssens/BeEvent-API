package nl.hsleiden.controller;

import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.CateringOrder;
import nl.hsleiden.model.Invoice;
import nl.hsleiden.model.Order;
import nl.hsleiden.repository.CateringOrderRepository;
import nl.hsleiden.repository.InvoiceRepository;
import nl.hsleiden.repository.OrderRepository;
import nl.hsleiden.service.CollectionDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    
    CollectionDataService<Invoice> invoiceCollectionDataService = new CollectionDataService<>();
    CollectionDataService<CateringOrder> cateringOrderCollectionDataService = new CollectionDataService<>();

    @GetMapping("/api/order")
    public Collection<Order> getOrders() { return orderRepository.findAll(); }

    @GetMapping("/api/order/{orderId}")
    public Optional<Order> getSpecifiedCatering(@PathVariable Long orderId) {
        LOGGER.info("Fetching order with id: " + orderId);
        return orderRepository.findById(orderId);
    }

    @PostMapping("/api/order")
    public Order createOrder(@Valid @RequestBody Order order) {
        LOGGER.info("Creating new order...");
        Order savedOrder = orderRepository.save(order);
        
        this.saveCateringOrders(savedOrder, order.getCateringOrders());
        this.saveInvoices(savedOrder, order.getInvoices());
        
        return orderRepository.save(order);
    }

    @PutMapping("/api/order/{orderId}")
    public Order updateOrder(@PathVariable Long orderId, @Valid @RequestBody Order updatedOrder) {
        LOGGER.info("Updating order with id: " + orderId);
        return orderRepository.findById(orderId).map(order -> {
            order.setCustomerId(updatedOrder.getCustomerId());
            order.setDateEvent(updatedOrder.getDateEvent());
            order.setDateOrder(updatedOrder.getDateOrder());
            order.setEndTime(updatedOrder.getEndTime());
            order.setMaxInstructors(updatedOrder.getMaxInstructors());
            order.setNote(updatedOrder.getNote());
            order.setStartTime(updatedOrder.getStartTime());

            Collection<Invoice> invoicesToBeSaved = invoiceCollectionDataService.getToBeSaved(order.getInvoices(), updatedOrder.getInvoices());
            Collection<Invoice> invoicesToDeleted = invoiceCollectionDataService.getToBeDeleted(order.getInvoices(), updatedOrder.getInvoices());

            Collection<CateringOrder> cateringOrdersToSaved = cateringOrderCollectionDataService.getToBeSaved(order.getCateringOrders(), order.getCateringOrders());
            Collection<CateringOrder> cateringOrdersToDeleted = cateringOrderCollectionDataService.getToBeDeleted(order.getCateringOrders(), order.getCateringOrders());

            saveInvoices(order, invoicesToBeSaved);
            deleteInvoices(invoicesToDeleted);

            saveCateringOrders(order, cateringOrdersToSaved);
            deleteCateringOrders(cateringOrdersToDeleted);

            order.setInvoices(
                    invoiceCollectionDataService.getDefinitiveCollection(order.getInvoices(), invoicesToBeSaved, invoicesToDeleted)
            );
            order.setCateringOrders(
                    cateringOrderCollectionDataService.getDefinitiveCollection(order.getCateringOrders(), cateringOrdersToSaved, cateringOrdersToDeleted)
            );
            
            return orderRepository.save(order);
        }).orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
    }

    @DeleteMapping("/api/order/{orderId}")
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
            LOGGER.info("Unable to catering orders");
        }
    }
}
