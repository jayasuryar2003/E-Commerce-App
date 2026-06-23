package com.app.ecom.service;

import com.app.ecom.dto.OrderItemDTO;
import com.app.ecom.dto.OrderResponse;
import com.app.ecom.model.*;
import com.app.ecom.repository.CartItemRespository;
import com.app.ecom.repository.OrderRepository;
import com.app.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartItemService cartItemService;

    public Optional<OrderResponse> createOrder(String userId) {
        //validate for cart items
        List<CardItem> cartitems = cartItemService.getCart(userId);
        if (cartitems.isEmpty()) {
            return Optional.empty();
        }
        // validate for user
        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
        if (userOptional.isEmpty()) {
            return Optional.empty();
        }
        User user = userOptional.get();
        // calculate total price
        BigDecimal totalPrice = cartitems.stream()
                .map(CardItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // create order
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);
        // focus on the below line instead creating a new function using streams we assigned values inside a constrctor
        // two things we have to remember here inmstead of using loops because cartitems have multiple items we used streams and passing each item as constructor is a smart way
        List<OrderItem> orderItems = cartitems.stream()
                .map(item -> new OrderItem(
                        null,
                        item.getProduct(),
                        item.getQuantity(),
                        item.getPrice(),
                        order
                ))
                .collect(Collectors.toList());
        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);
        // clear the cart
        cartItemService.clearCart(userId);

        return Optional.of(mapToOrderResponse(savedOrder));
    }

    private OrderResponse mapToOrderResponse(Order savedOrder) {
        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getTotalAmount(),
                savedOrder.getStatus(),
                savedOrder.getItems().stream()
                        .map(orderItem -> new OrderItemDTO( // here we need to item into the OrderItemDto that's why we are mapping like this
                                orderItem.getId(),
                                orderItem.getProduct().getId(),
                                orderItem.getQuantity(),
                                orderItem.getPrice(),
                                orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()))


                        )).toList(),
                savedOrder.getCreatedAt()


        );
    }
}
