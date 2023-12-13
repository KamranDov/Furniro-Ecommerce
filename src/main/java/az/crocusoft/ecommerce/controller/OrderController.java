package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.dto.OrderDto;
import az.crocusoft.ecommerce.exception.OrderException;
import az.crocusoft.ecommerce.model.Order;
import az.crocusoft.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/placeOrder")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Order> placeOrder(@RequestBody OrderDto orderDto,
                                            @RequestParam Long userId)
            throws LoginException, OrderException {
        if (orderDto == null ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Order placedOrder = orderService.placeOrder(orderDto,userId);
        return ResponseEntity.ok(placedOrder);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> allOrders = orderService.getAllOrders();
        return new ResponseEntity<>(allOrders, HttpStatus.OK);
    }
    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId)
                .map(order -> new ResponseEntity<>(order, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long orderId, @RequestBody OrderDto updatedOrderDto) {
        OrderDto updatedOrder = orderService.updateOrder(orderId, updatedOrderDto);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderDto>> getOrdersByUserId(@PathVariable Long userId) {
        List<OrderDto> userOrders = orderService.getOrdersByUserId(userId);
        return new ResponseEntity<>(userOrders, HttpStatus.OK);
    }


}
