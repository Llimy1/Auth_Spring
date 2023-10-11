package com.example.auth_spring.web.domain.product;

import com.example.auth_spring.web.domain.brand.Brand;
import com.example.auth_spring.web.domain.cart.Cart;
import com.example.auth_spring.web.domain.common.BaseTimeEntity;
import com.example.auth_spring.web.domain.image.Image;
import com.example.auth_spring.web.domain.like.Like;
import com.example.auth_spring.web.domain.order.Order;
import com.example.auth_spring.web.domain.productoption.ProductOption;
import com.example.auth_spring.web.domain.subcategory.SubCategory;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.domain.view.View;
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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Integer deliveryPrice;

    @Column
    private Integer discountRate;

    @Column(nullable = false)
    private Boolean isDiscount;

    @Column(nullable = false)
    private Long likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToMany(mappedBy = "product")
    private List<ProductOption> productOptionList;

    @OneToMany(mappedBy = "product")
    private List<View> viewList;

    @OneToMany(mappedBy = "product")
    private List<Like> likeList;

    @OneToMany(mappedBy = "product")
    private List<Image> imageList;

    @Builder
    public Product(String name, Long price, Integer deliveryPrice, Integer discountRate, Boolean isDiscount, User user, SubCategory subCategory, Brand brand, Long likeCount) {
        this.name = name;
        this.price = price;
        this.deliveryPrice = deliveryPrice;
        this.discountRate = discountRate;
        this.isDiscount = isDiscount;
        this.user = user;
        this.subCategory = subCategory;
        this.brand = brand;
        this.likeCount = likeCount;
    }

    public void increaseLike() {
        this.likeCount += 1;
    }

    public void decreaseLike() {
        this.likeCount -= 1;
    }
}
