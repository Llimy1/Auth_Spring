package com.example.auth_spring.web.domain.subcategory;

import com.example.auth_spring.web.domain.category.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
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

    @Builder
    public SubCategory(Category category, String name) {
        this.category = category;
        this.name = name;
    }

    public void subCategoryUpdate(String afterSubCategoryName) {
        this.name = afterSubCategoryName;

    }
}
