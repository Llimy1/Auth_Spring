package com.example.auth_spring.web.domain.subcategory;

import com.example.auth_spring.web.domain.category.Category;
import com.example.auth_spring.web.domain.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "sub_categories")
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column
    private String name;

    @OneToMany(mappedBy = "subCategory")
    private List<Product> productList;

    @Builder
    public SubCategory(Category category, String name) {
        this.category = category;
        this.name = name;
    }

    public void subCategoryUpdate(String afterSubCategoryName) {
        this.name = afterSubCategoryName;
    }
}
