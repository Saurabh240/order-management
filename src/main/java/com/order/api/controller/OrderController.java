package com.order.api.controller;

import com.order.api.model.Order;
import com.order.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public ResponseEntity<Order> addOrder(@RequestBody Order order) {
        Order addedOrder = orderService.addOrder(order);
        return new ResponseEntity<>(addedOrder, HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    public ResponseEntity<Order> editOrder(@RequestBody Order order) {
        Order editedOrder = orderService.editOrder(order);
        return new ResponseEntity<>(editedOrder, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{orderId}/add-product/{productId}")
    public ResponseEntity<Order> addProductToOrder(
            @PathVariable Long orderId,
            @PathVariable Long productId,
            @RequestParam int quantity
    ) {
        Order updatedOrder = orderService.addProductToOrder(orderId, productId, quantity);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @PostMapping("/{orderId}/remove-product/{productId}")
    public ResponseEntity<Void> removeProductFromOrder(
            @PathVariable Long orderId,
            @PathVariable Long productId
    ) {
        orderService.removeProductFromOrder(orderId, productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/add-vendor/{orderProductId}/{vendorId}")
    public ResponseEntity<Void> addVendorToOrderProduct(
            @PathVariable Long orderProductId,
            @PathVariable Long vendorId
    ) {
        orderService.addVendorToOrderProduct(orderProductId, vendorId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/remove-vendor/{orderProductId}/{vendorId}")
    public ResponseEntity<Void> removeVendorFromOrderProduct(
            @PathVariable Long orderProductId,
            @PathVariable Long vendorId
    ) {
        orderService.removeVendorFromOrderProduct(orderProductId, vendorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


