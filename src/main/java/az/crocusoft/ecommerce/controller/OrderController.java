package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.dto.OrderDto;
import az.crocusoft.ecommerce.exception.OrderException;
import az.crocusoft.ecommerce.model.Order;
import az.crocusoft.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class OrderController {

    private OrderService orderService;


    @PostMapping("/placeOrder")
    public ResponseEntity<Order> placeOrder(@RequestBody OrderDto orderDto)
            throws LoginException, OrderException {
        Order placedOrder = orderService.placeOrder(orderDto);
        return ResponseEntity.ok(placedOrder);
    }




}
