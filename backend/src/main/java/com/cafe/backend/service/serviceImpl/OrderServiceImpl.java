/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cafe.backend.service.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cafe.backend.dto.MenuDTO;
import com.cafe.backend.dto.OrderDTO;
import com.cafe.backend.dto.OrderItemsDTO;
import com.cafe.backend.dto.OrderListDTO;
import com.cafe.backend.entity.OrderEntity;
import com.cafe.backend.entity.OrderItemsEntity;
import com.cafe.backend.entity.PointHistoryEntity;
import com.cafe.backend.entity.UserEntity;
import com.cafe.backend.repository.MenuListRepository;
import com.cafe.backend.repository.OrderItemsRepository;
import com.cafe.backend.repository.OrderRepository;
import com.cafe.backend.repository.PointRepository;
import com.cafe.backend.repository.UserRepository;
import com.cafe.backend.service.OrderService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author tu
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepo;
    private final OrderItemsRepository itemsRepo;
    private final UserRepository userRepo;
    private final MenuListRepository menuRepo;
    private final PointRepository pointRepo;

    private final int DRINK = 0;
    private final int FOOD = 1;

    @Override //注文
    @Transactional
    public int getOrder(OrderDTO dto, Integer userId) {
        UserEntity user = userRepo.findByUserId(userId);
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUser(user);
        orderEntity.setOrderDate(LocalDateTime.now());

        int subtotal = 0;
        int getPoint = 0;

        for(var item : dto.getItems()) {
            var menu = menuRepo.findById(item.getMenuId())
                .orElseThrow(() -> new IllegalArgumentException("menu not found :" + item.getMenuId()));
            
            var orderItem = new OrderItemsEntity();
            orderItem.setOrder(orderEntity);
            orderItem.setMenu(menu);
            orderItem.setOrderName(menu.getMenuName());
            orderItem.setOrderPrice(menu.getMenuPrice());
            orderItem.setNum(item.getCount());

            orderEntity.getItem().add(orderItem);
            subtotal += menu.getMenuPrice() * item.getCount();
            getPoint += menu.getMenuPoint() * item.getCount();
        }

        int rate = dto.getTaxChoice();
        int tax = (int)Math.round(subtotal * (rate / 100.0));
        int total = subtotal + tax;

        orderEntity.setSubtotal(subtotal);
        orderEntity.setTax(tax);
        orderEntity.setTotal(total);

        var save = orderRepo.save(orderEntity);

        int newTotal = pointRepo.findTopByUserUserIdOrderByLastOrderDateDesc(userId)
            .map(PointHistoryEntity::getTotalPoint).orElse(getPoint);
        int totalPoint = newTotal + getPoint;

        PointHistoryEntity pointEntity = new PointHistoryEntity();
        pointEntity.setLastOrderDate(LocalDateTime.now());
        pointEntity.setUser(user);
        pointEntity.setGetPoint(getPoint);
        pointEntity.setOrder(save);
        pointEntity.setTotalPoint(totalPoint);
        pointRepo.save(pointEntity);

        return save.getOrderId();
    }

    @Override //注文履歴
    public List<OrderListDTO> getOrderList(Integer userId){
        List<OrderEntity> getEntity = orderRepo.findByUserUserIdOrderByOrderDateDesc(userId);
        return getEntity.stream()
            .map(o -> OrderListDTO.builder()
                .orderId(o.getOrderId())
                .orderDate(o.getOrderDate())
                .orderPrice(o.getTotal())
                .build()).toList();
    }

    @Override //注文詳細
    public OrderItemsDTO getOrderDetail(Integer orderId, Integer userId){
        OrderItemsDTO dto = new OrderItemsDTO();
        OrderEntity orderEntity = orderRepo.findDetailById(orderId, userId)
            .orElseThrow(() -> new IllegalArgumentException("orderDetail not found : " + orderId));

        PointHistoryEntity pointEntity = pointRepo.findTopByUserUserIdAndOrderOrderIdOrderByLastOrderDateDesc(userId, orderId)
            .orElseThrow(() -> new IllegalArgumentException("pointEntity not found : " + orderId + " / " + userId));
        
        List<OrderItemsEntity> itemList = itemsRepo.findByOrderOrderId(orderId);

        List<OrderItemsDTO.ItemList> dtoList = itemList.stream()
            .map(i -> OrderItemsDTO.ItemList.builder()
                    .orderName(i.getOrderName())
                    .orderPrice(i.getOrderPrice())
                    .orderCount(i.getNum())
                    .build())
            .toList();

        dto.setTotalPoint(pointEntity.getTotalPoint());
        dto.setGetPoint(pointEntity.getGetPoint());
        dto.setOrderDate(orderEntity.getOrderDate());
        dto.setSubtotal(orderEntity.getSubtotal());
        dto.setTotalPrice(orderEntity.getTotal());
        dto.setLists(dtoList);
        return dto;
    }

    @Override
    public List<MenuDTO> drinkMenu(){
        return toListDTO(menuRepo.findByMenuFlgOrderByMenuIdAsc(DRINK));
    }

    @Override
    public List<MenuDTO> foodMenu(){
        return toListDTO(menuRepo.findByMenuFlgOrderByMenuIdAsc(FOOD));
    }

    private List<MenuDTO> toListDTO(List<MenuListRepository.MenuList> rows) {
        return rows.stream().map(r ->
            MenuDTO.builder()
            .menuId(r.getMenuId())
            .menuName(r.getMenuName())
            .price(r.getMenuPrice())
            .point(r.getMenuPoint())
            .build())
            .collect(Collectors.toList());
    }
}
