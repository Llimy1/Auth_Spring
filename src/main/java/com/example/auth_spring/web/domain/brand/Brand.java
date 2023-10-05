package com.example.auth_spring.web.domain.brand;

import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "brands")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "brand")
    private List<Product> product;



    @Builder
    public Brand(String name, User user) {
        this.name = name;
        this.user = user;
    }
}
