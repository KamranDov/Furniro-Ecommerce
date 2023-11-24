package az.crocusoft.ecommerce.service.impl;

import az.crocusoft.ecommerce.dto.AddressDto;
import az.crocusoft.ecommerce.dto.OrderDto;
import az.crocusoft.ecommerce.exception.ResourceNotFoundException;
import az.crocusoft.ecommerce.model.*;
import az.crocusoft.ecommerce.repository.AddressRepository;
import az.crocusoft.ecommerce.repository.OrderRepository;
import az.crocusoft.ecommerce.repository.UserRepository;
import az.crocusoft.ecommerce.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {


    private AddressDto addressDto;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;




    @Autowired
    private ModelMapper modelMapper;



    @Transactional
    @Override
    public Order placeOrder(OrderDto orderDto) {
        // OrderDTO'dan Order ve BillingDetails oluştur
        Order order = modelMapper.map(orderDto, Order.class);
        order.setOrderStatus(OrderStatusValues.SUCCESS); // Varsayılan durum: Yeni sipariş
        Address address =modelMapper.map(orderDto.getAddressDto(),Address.class);


       addressRepository.save(address);
       order.setAddress(address);
        return orderRepository.save(order);
    }











    private void clearCart(User user) {
        // Sepeti temizle
        // Sepetin içindeki ürünleri veritabanından silme veya işaretleme işlemini gerçekleştirin

//        cartServiceImp.clearCartItems(user);
    }
 }