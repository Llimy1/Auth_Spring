package com.example.auth_spring.web.domain.order;

import com.example.auth_spring.web.dto.order.OrderProductAllResponseDto;
import com.example.auth_spring.web.dto.order.OrderProductDetailResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT NEW com.example.auth_spring.web.dto.order.OrderProductAllResponseDto(" +
            "o.orderName as orderName, " +
            "p.name as productName, " +
            "o.totalPrice as totalOrderPrice, " +
            "o.count as orderProductCount) " +
            "FROM Order o " +
            "JOIN o.user u " +
            "JOIN o.product p " +
            "WHERE u.email = :email ")
    Page<OrderProductAllResponseDto> findAllOrderList(@Param("email") String email, Pageable pageable);


    @Query("SELECT NEW com.example.auth_spring.web.dto.order.OrderProductDetailResponseDto(" +
            "u.name as userName, " +
            "p.name as productName, " +
            "p.price as productPrice," +
            "p.deliveryPrice as deliveryPrice, " +
            "o.totalPrice as totalOrderPrice, " +
            "a.zipCode as zipCode, " +
            "a.streetAddress as streetAddress," +
            "a.detailAddress as detailAddress, " +
            "o.count as orderProductCount) " +
            "FROM Order o " +
            "JOIN o.user u " +
            "JOIN o.product p " +
            "JOIN o.address a " +
            "WHERE o.orderName = ?1 ")
    OrderProductDetailResponseDto findDetailOrder(String orderName);
}
