package com.order.api.service;

import com.order.api.model.Order;
import com.order.api.model.OrderProduct;
import com.order.api.model.Product;
import com.order.api.model.Vendor;
import com.order.api.repository.OrderProductRepository;
import com.order.api.repository.OrderRepository;
import com.order.api.repository.ProductRepository;
import com.order.api.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// OrderService.java
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order editOrder(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    public Order addProductToOrder(Long orderId, Long productId, int quantity) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setOrder(order);
        orderProduct.setProduct(product);
        orderProduct.setQuantity(quantity);

        order.getOrderProducts().add(orderProduct);

        return orderRepository.save(order);
    }

    public void removeProductFromOrder(Long orderId, Long productId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.getOrderProducts().removeIf(op -> op.getProduct().getId().equals(productId));

        orderRepository.save(order);
    }

    public void addVendorToOrderProduct(Long orderProductId, Long vendorId) {
        OrderProduct orderProduct = orderProductRepository.findById(orderProductId)
                .orElseThrow(() -> new RuntimeException("OrderProduct not found"));

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        orderProduct.setVendor(vendor);

        orderProductRepository.save(orderProduct);
    }

    public void removeVendorFromOrderProduct(Long orderProductId, Long vendorId) {
        OrderProduct orderProduct = orderProductRepository.findById(orderProductId)
                .orElseThrow(() -> new RuntimeException("OrderProduct not found"));

        if (orderProduct.getVendor() != null && orderProduct.getVendor().getId().equals(vendorId)) {
            orderProduct.setVendor(null);
            orderProductRepository.save(orderProduct);
        }
    }
}
