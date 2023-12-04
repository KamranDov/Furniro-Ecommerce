package az.crocusoft.ecommerce.service.Impl;

import az.crocusoft.ecommerce.dto.OrderDto;
import az.crocusoft.ecommerce.dto.cart.CartDto;
import az.crocusoft.ecommerce.exception.ResourceNotFoundException;
import az.crocusoft.ecommerce.model.*;
import az.crocusoft.ecommerce.repository.CartRepository;
import az.crocusoft.ecommerce.repository.OrderRepository;
import az.crocusoft.ecommerce.repository.UserRepository;
import az.crocusoft.ecommerce.service.CartService;
import az.crocusoft.ecommerce.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {



    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    private final ModelMapper modelMapper;
    private final CartService cartService;




    @Override
    public Order placeOrder(OrderDto orderDto, Long cartId) {
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        if (cartOptional.isEmpty()){
            throw new ResourceNotFoundException("Cart", "cartId", cartId);

        }
        Cart cart = cartOptional.get();
        CartDto cartDto = new CartDto();

        Order order = modelMapper.map(orderDto, Order.class);
        order.setOrderDate(LocalDate.now());
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(cart.getQuantity());
        orderItem.setTotalAmount(cartDto.getTotalPrice());
        order.setOrderStatus(OrderStatusValues.SUCCESS);
        order.setCart(cart);


        Order savedOrder = orderRepository.save(order);
        return savedOrder;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }


    @Override
    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    @Transactional
    public OrderDto updateOrder(Long orderId, OrderDto orderDto) {

        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "orderId", orderId));

        modelMapper.map(orderDto, existingOrder);
        Order updatedOrder = orderRepository.save(existingOrder);
        return modelMapper.map(updatedOrder, OrderDto.class);
    }
    @Override
    public List<OrderDto> getOrdersByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Order> userOrders = user.getOrders();
        return userOrders.stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }
 }