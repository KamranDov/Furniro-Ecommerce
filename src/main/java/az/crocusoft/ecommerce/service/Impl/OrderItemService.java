package az.crocusoft.ecommerce.service.Impl;

import az.crocusoft.ecommerce.dto.OrderDto;
import az.crocusoft.ecommerce.dto.OrderItemDto;
import az.crocusoft.ecommerce.model.Order;
import az.crocusoft.ecommerce.model.OrderItem;
import az.crocusoft.ecommerce.model.User;
import az.crocusoft.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public List<OrderItemDto> getOrderItemsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> userOrders = user.getOrders();

        List<OrderItem> orderItems = userOrders.stream()
                .flatMap(order -> order.getOrderItems().stream())
                .collect(Collectors.toList());

        return orderItems.stream()
                .map(orderItem -> modelMapper.map(orderItem, OrderItemDto.class))
                .collect(Collectors.toList());
    }

}
