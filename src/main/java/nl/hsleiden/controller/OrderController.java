package nl.hsleiden.controller;

import nl.hsleiden.exception.ResourceNotFoundException;
import nl.hsleiden.model.Order;
import nl.hsleiden.repository.OrderRepository;
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
            order.setEventId(updatedOrder.getEventId());
            order.setInvoiceNumber(updatedOrder.getInvoiceNumber());
            order.setNote(updatedOrder.getNote());
            order.setQuotationId(updatedOrder.getQuotationId());
            order.setStartTime(updatedOrder.getStartTime());
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
}
