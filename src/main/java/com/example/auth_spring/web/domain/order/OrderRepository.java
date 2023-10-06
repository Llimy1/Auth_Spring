package com.example.auth_spring.web.domain.order;

import com.example.auth_spring.web.dto.order.OrderProductAllResponseDto;
import com.example.auth_spring.web.dto.order.OrderProductDetailResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT NEW com.example.auth_spring.web.dto.order.OrderProductAllResponseDto(" +
            "o.orderName as orderName, " +
            "p.name as productName, " +
            "o.totalPrice as totalOrderPrice," +
            "b.name as productBrand," +
            "o.count as orderProductCount," +
            "op.name as optionName) " +
            "FROM Order o " +
            "JOIN o.user u " +
            "JOIN o.productOption po " +
            "JOIN po.product p " +
            "JOIN p.brand b " +
            "JOIN po.option op " +
            "WHERE u.email = :email ")
    Page<OrderProductAllResponseDto> findAllOrderList(@Param("email") String email, Pageable pageable);


    @Query("SELECT NEW com.example.auth_spring.web.dto.order.OrderProductDetailResponseDto(" +
            "u.name as userName, " +
            "p.name as productName, " +
            "p.price as productPrice," +
            "b.name as productBrand," +
            "op.name as optionName," +
            "p.deliveryPrice as deliveryPrice, " +
            "o.totalPrice as totalOrderPrice, " +
            "a.zipCode as zipCode, " +
            "a.streetAddress as streetAddress," +
            "a.detailAddress as detailAddress, " +
            "o.count as orderProductCount) " +
            "FROM Order o " +
            "JOIN o.user u " +
            "JOIN o.productOption po " +
            "JOIN po.product p " +
            "JOIN o.address a " +
            "JOIN p.brand b " +
            "JOIN po.option op " +
            "WHERE o.orderName = ?1 ")
    OrderProductDetailResponseDto findDetailOrder(String orderName);

    boolean existsByOrderName(String orderName);
}
