package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.dto.AddressDto;
import az.crocusoft.ecommerce.dto.OrderDto;
import az.crocusoft.ecommerce.model.Order;
import jakarta.transaction.Transactional;

import java.util.List;

public interface OrderService {




    @Transactional
    Order placeOrder(OrderDto orderDto);





    }
