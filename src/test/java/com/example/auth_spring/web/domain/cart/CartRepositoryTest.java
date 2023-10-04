package com.example.auth_spring.web.domain.cart;

import com.example.auth_spring.type.Role;
import com.example.auth_spring.web.domain.category.Category;
import com.example.auth_spring.web.domain.category.CategoryRepository;
import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.domain.product.ProductRepository;
import com.example.auth_spring.web.domain.subcategory.SubCategory;
import com.example.auth_spring.web.domain.subcategory.SubCategoryRepository;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;





    @Test
    @DisplayName("[Repository] 장바구니 생성하기")
    void createCart() {

        String email1 = "g";
        String password1 = "h";
        String name1 = "i";
        String nickname1 = "j";
        String phoneNumber1 = "k";
        String gender1 = "l";
        String introduce1 = "안녕하세요 홍길동 입니다.";
        String profileImgUrl1 = "https://img_url";
        Role role1 = Role.valueOf("USER");


        //given
        User user = User.builder()
                .email(email1)
                .password(password1)
                .name(name1)
                .nickname(nickname1)
                .phoneNumber(phoneNumber1)
                .gender(gender1)
                .introduce(introduce1)
                .profileImgUrl(profileImgUrl1)
                .role(role1)
                .build();

        User user1 = userRepository.save(user);

        Category category = Category.builder()
                .name("의류")
                .build();


        categoryRepository.save(category);

        SubCategory subCategory = SubCategory.builder()
                .category(category)
                .name("맨투맨")
                .build();

        subCategoryRepository.save(subCategory);

        Product product = Product.builder()
                .subCategory(subCategory)
                .price(10000L)
                .build();

        Product product1 = productRepository.save(product);


        Cart cart = Cart.builder()
                .user(user1)
                .product(product1)
                .build();

        Cart result = cartRepository.save(cart);

        //when
        //then
        assertThat(result.getUser().getId()).isEqualTo(user.getId());
        assertThat(result.getProduct().getId()).isEqualTo(product.getId());

    }
}