package com.example.auth_spring.web.domain.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Long price;

    @Builder
    public Product(String name, Long price) {
        this.name = name;
        this.price = price;
    }
}
