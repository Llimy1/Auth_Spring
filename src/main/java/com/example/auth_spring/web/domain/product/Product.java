package com.example.auth_spring.web.domain.product;

import com.example.auth_spring.web.domain.cart.Cart;
import com.example.auth_spring.web.domain.common.BaseTimeEntity;
import com.example.auth_spring.web.domain.order.Order;
import com.example.auth_spring.web.domain.subcategory.SubCategory;
import com.example.auth_spring.web.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Long price;

    @Column
    private Integer deliveryPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategory;

    @OneToMany(mappedBy = "product")
    private List<Cart> cartList;

    @OneToMany(mappedBy = "product")
    private List<Order> orderList;

    @Builder
    public Product(User user, SubCategory subCategory, String name, Long price, Integer deliveryPrice) {
        this.user = user;
        this.subCategory = subCategory;
        this.name = name;
        this.price = price;
        this.deliveryPrice = deliveryPrice;
    }
}
