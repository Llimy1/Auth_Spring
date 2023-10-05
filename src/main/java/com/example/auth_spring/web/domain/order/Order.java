package com.example.auth_spring.web.domain.order;

import com.example.auth_spring.web.domain.address.Address;
import com.example.auth_spring.web.domain.common.BaseTimeEntity;
import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String orderName;

    @Column
    private Integer count;

    @Column
    private Long totalPrice;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @Builder
    public Order(String orderName, Integer count, Long totalPrice, User user, Product product, Address address) {
        this.orderName = orderName;
        this.count = count;
        this.totalPrice = totalPrice;
        this.user = user;
        this.product = product;
        this.address = address;
    }
}
