package az.crocusoft.ecommerce.service.impl;
import az.crocusoft.ecommerce.dto.OrderDto;
import az.crocusoft.ecommerce.exception.ResourceNotFoundException;
import az.crocusoft.ecommerce.model.*;
import az.crocusoft.ecommerce.repository.AddressRepository;
import az.crocusoft.ecommerce.repository.OrderRepository;
import az.crocusoft.ecommerce.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {


    private final AddressRepository addressRepository;

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Order placeOrder(OrderDto orderDto) {
        User user = userRepository.findById(orderDto.getUserId()).orElseThrow();
        if (orderDto == null || orderDto.getAddressDto() == null) {
            throw new IllegalArgumentException("OrderDto or AddressDto cannot be null");
        }
        Order order = modelMapper.map(orderDto, Order.class);
        order.setOrderStatus(OrderStatusValues.SUCCESS);
        Address address =modelMapper.map(orderDto.getAddressDto(),Address.class);
        order.setUser(user);
       addressRepository.save(address);
       order.setAddress(address);
        return orderRepository.save(order);
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
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", Math.toIntExact(orderId)));
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