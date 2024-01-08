package az.crocusoft.ecommerce.service.Impl;

import az.crocusoft.ecommerce.dto.OrderDto;
import az.crocusoft.ecommerce.dto.cart.CartDto;
import az.crocusoft.ecommerce.dto.cart.CartItemDto;
import az.crocusoft.ecommerce.exception.CartNotFoundException;
import az.crocusoft.ecommerce.exception.InsufficientStockException;
import az.crocusoft.ecommerce.exception.UserNotFoundException;
import az.crocusoft.ecommerce.model.*;
import az.crocusoft.ecommerce.model.product.Product;
import az.crocusoft.ecommerce.model.product.ProductVariation;
import az.crocusoft.ecommerce.repository.CartRepository;
import az.crocusoft.ecommerce.repository.OrderItemRepository;
import az.crocusoft.ecommerce.repository.OrderRepository;
import az.crocusoft.ecommerce.repository.UserRepository;
import az.crocusoft.ecommerce.service.CartService;
import az.crocusoft.ecommerce.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;
    private final CartService cartService;
    private final OrderItemRepository  orderItemRepository;
    private final ProductServiceImpl productService;
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public Order placeOrder(OrderDto orderDto, Long userId) {

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        User user = userOptional.get();
        CartDto cartDto = cartService.listCartItems(user);
        if (cartDto == null || cartDto.getCartItems().isEmpty()) {
            throw new CartNotFoundException("Cart not found ", "cartId", userId);
        }
       List< Cart> cart = cartRepository.findAllByUser(user);
        if (cart==null){
            throw new CartNotFoundException("Cart not found ", "cartId", userId);
        }
        Order order = modelMapper.map(orderDto, Order.class);
        order.setOrderDate(LocalDate.now());
        order.setUser(user);
        for (CartItemDto cartItemDto : cartDto.getCartItems()) {
            ProductVariation productVariation = productService.findById(cartItemDto.getVariationId());

            Product product = productVariation.getProduct();
            int orderedQuantity = cartItemDto.getQuantity();
            int remainingStock = productVariation.getStockQuantity() - orderedQuantity;

            if (remainingStock < 0) {
                throw new InsufficientStockException("Insufficient stock for product variation with ID: " + productVariation.getProductVariationiId());
            }
            productVariation.setStockQuantity(remainingStock);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(orderedQuantity);
            orderItem.setTotalAmount(
                    BigDecimal.valueOf((productService.getProductSpecialPrice(product))*(cartItemDto.getQuantity())));
            order.getOrderItems().add(orderItem);
        }

        order.setOrderStatus(OrderStatusValues.PENDING);
        Order savedOrder = orderRepository.save(order);

        cartService.clearAllCartsForUser(user);
        log.info("order completed");

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
//    @Transactional
    public void deleteOrder(Long orderId) {
        System.out.println("orderId = " + orderId);
        orderItemRepository.deleteById(orderId);
        orderRepository.deleteById(orderId);
        log.info("order and orderItem are deleted");
    }

    @Override
    @Transactional
    public OrderDto updateOrder(Long orderId, OrderDto orderDto) {

        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new CartNotFoundException("Order", "orderId", orderId));

        modelMapper.map(orderDto, existingOrder);
        Order updatedOrder = orderRepository.save(existingOrder);
        return modelMapper.map(updatedOrder, OrderDto.class);
    }
    @Override
    public List<OrderDto> getOrdersByUserId(Long userId) {

        List<Order> userOrders = orderRepository.findAllByUserId(userId);
        return userOrders.stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }
 }