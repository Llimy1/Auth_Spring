package com.example.auth_spring.web.domain.category;


import com.example.auth_spring.web.domain.subcategory.SubCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "category")
    private List<SubCategory> subCategory;

    @Builder
    public Category(String name) {
        this.name = name;
    }

    public void categoryUpdate(String afterCategoryName) {
        this.name = afterCategoryName;
    }
}
