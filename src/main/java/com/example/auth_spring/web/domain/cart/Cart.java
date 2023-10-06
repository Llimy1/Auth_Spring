package com.example.auth_spring.web.domain.cart;

import com.example.auth_spring.web.domain.common.BaseTimeEntity;
import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.domain.productoption.ProductOption;
import com.example.auth_spring.web.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "carts")
public class Cart extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_option_id")
    private ProductOption productOption;

    @Builder
    public Cart(User user, ProductOption productOption) {
        this.user = user;
        this.productOption = productOption;
    }
}
