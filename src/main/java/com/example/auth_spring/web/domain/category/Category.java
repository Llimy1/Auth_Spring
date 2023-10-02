package com.example.auth_spring.web.domain.category;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Builder
    public Category(String name) {
        this.name = name;
    }

    public void categoryUpdate(String afterCategoryName) {
        this.name = afterCategoryName;
    }
}