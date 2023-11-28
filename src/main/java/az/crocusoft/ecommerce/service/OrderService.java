package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.dto.AddressDto;
import az.crocusoft.ecommerce.dto.OrderDto;
import az.crocusoft.ecommerce.model.Order;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface OrderService {




    @Transactional
    Order placeOrder(OrderDto orderDto);
    public List<Order> getAllOrders();

    public Optional<Order> getOrderById(Long orderId);

    public void deleteOrder(Long orderId);

    public OrderDto updateOrder(Long orderId, OrderDto orderDto);
    public List<OrderDto> getOrdersByUserId(Long userId);







    }
