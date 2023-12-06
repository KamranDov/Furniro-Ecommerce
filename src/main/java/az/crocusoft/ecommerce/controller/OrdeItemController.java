package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.dto.OrderItemDto;
import az.crocusoft.ecommerce.service.Impl.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orderitem")
public class OrdeItemController {
    private final OrderItemService orderItemService;



    @GetMapping("/user/{userId}")
    public List<OrderItemDto> getOrderItemsByUserId(@PathVariable Long userId) {
        return orderItemService.getOrderItemsByUserId(userId);
    }
}
