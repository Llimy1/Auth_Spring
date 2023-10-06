package com.example.auth_spring.web.domain.productoption;

import com.example.auth_spring.web.domain.cart.Cart;
import com.example.auth_spring.web.domain.option.Option;
import com.example.auth_spring.web.domain.order.Order;
import com.example.auth_spring.web.domain.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "products_options")
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private Option option;

    @OneToMany(mappedBy = "productOption")
    private List<Cart> cartList;

    @OneToMany(mappedBy = "productOption")
    private List<Order> orderList;

    @Builder
    public ProductOption(Product product, Option option) {
        this.product = product;
        this.option = option;
    }
}
