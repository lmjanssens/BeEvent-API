package nl.hsleiden.controller;

import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
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
    private CateringOrderRepository cateringOrderRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private QuotationRepository quotationRepository;

    CollectionDataService<CateringOrder> cateringOrderCollectionDataService = new CollectionDataService<>();
    CollectionDataService<Quotation> quotationCollectionDataService = new CollectionDataService<>();

    @GetMapping("/api/orders")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "') or hasAuthority('" + Role.INSTRUCTOR + "')")
    @JsonView(View.Public.class)
    public Collection<Order> getOrders() { return orderRepository.findAll(); }

    @GetMapping("/api/orders/{orderId}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')  or hasAuthority('" + Role.INSTRUCTOR + "')")
    @JsonView(View.Public.class)
    public Optional<Order> getSpecifiedCatering(@PathVariable Long orderId) {
        LOGGER.info("Fetching order with id: " + orderId);
        return orderRepository.findById(orderId);
    }

    @PostMapping("/api/orders/{customerId}/{eventId}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    @JsonView(View.Public.class)
    public Order createOrder(@Valid @RequestBody Order order,
                             @PathVariable Long eventId,
                             @PathVariable Long customerId) {
        LOGGER.info("Creating new order...");
        Order savedOrder = orderRepository.save(order);

        this.saveCateringOrders(savedOrder, order.getCateringOrders());
        this.saveQuotations(savedOrder, order.getQuotations());

        return linkEventAndCustomerToOrder(savedOrder, eventId, customerId);
    }

    @PutMapping("/api/orders/{orderId}/{customerId}/{eventId}")
    @PreAuthorize("hasAuthority('" + Role.EMPLOYEE + "') or hasAuthority('" + Role.ADMIN + "')")
    @JsonView(View.Public.class)
    public Order updateOrder(@PathVariable Long orderId, @Valid @RequestBody Order updatedOrder,
                             @PathVariable Long customerId, @PathVariable Long eventId) {
        LOGGER.info("Updating order with id: " + orderId);
        return orderRepository.findById(orderId).map(order -> {
            order.setEvent(updatedOrder.getEvent());
            order.setDateEvent(updatedOrder.getDateEvent());
            order.setDateOrder(updatedOrder.getDateOrder());
            order.setEndTime(updatedOrder.getEndTime());
            order.setNote(updatedOrder.getNote());
            order.setStartTime(updatedOrder.getStartTime());

            Collection<CateringOrder> cateringOrdersToBeSaved = cateringOrderCollectionDataService.getToBeSaved(order.getCateringOrders(), updatedOrder.getCateringOrders());
            Collection<CateringOrder> cateringOrdersToBeDeleted = cateringOrderCollectionDataService.getToBeDeleted(order.getCateringOrders(), updatedOrder.getCateringOrders());

            Collection<Quotation> quotationsToBeSaved = quotationCollectionDataService.getToBeSaved(order.getQuotations(), updatedOrder.getQuotations());
            Collection<Quotation> quotationsToBeDeleted = quotationCollectionDataService.getToBeDeleted(order.getQuotations(), updatedOrder.getQuotations());

            saveCateringOrders(order, cateringOrdersToBeSaved);
            deleteCateringOrders(cateringOrdersToBeDeleted);

            saveQuotations(order, quotationsToBeSaved);
            deleteQuotations(quotationsToBeDeleted);

            order.setCateringOrders(
                    cateringOrderCollectionDataService.getDefinitiveCollection(order.getCateringOrders(), cateringOrdersToBeSaved, cateringOrdersToBeDeleted)
            );
            order.setQuotations(
                    quotationCollectionDataService.getDefinitiveCollection(order.getQuotations(), quotationsToBeSaved, quotationsToBeDeleted)
            );

            return linkEventAndCustomerToOrder(order, eventId, customerId);
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

    private Order linkEventAndCustomerToOrder(Order order, Long eventId, Long customerId) {
        return eventRepository.findById(eventId).map(event -> {
            order.setEvent(event);
            return customerRepository.findById(customerId).map(customer -> {
                order.setCustomer(customer);
                return orderRepository.save(order);
            }).orElseThrow(() -> new ResourceNotFoundException("No customer found with id: " + customerId));
        }).orElseThrow(() -> new ResourceNotFoundException("No event found with id: " + eventId));
    }
}
