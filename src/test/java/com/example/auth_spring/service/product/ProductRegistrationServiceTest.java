package com.example.auth_spring.service.product;

import com.example.auth_spring.security.jwt.service.TokenService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.seller.registration.ProductRegistrationService;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.web.domain.category.Category;
import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.domain.product.ProductRepository;
import com.example.auth_spring.web.domain.subcategory.SubCategory;
import com.example.auth_spring.web.domain.subcategory.SubCategoryRepository;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.dto.product.ProductRequestDto;
import com.example.auth_spring.web.exception.IllegalStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ProductRegistrationServiceTest {



    private TokenService tokenService;
    private CommonService commonService;

    private ProductRepository productRepository;

    private SubCategoryRepository subCategoryRepository;

    private ProductRegistrationService productRegistrationService;

    private User user;

    private Category category;

    private SubCategory subCategory;

    @BeforeEach
    void setup() {
        tokenService = mock(TokenService.class);
        productRepository = mock(ProductRepository.class);
        subCategoryRepository = mock(SubCategoryRepository.class);
        productRegistrationService = new ProductRegistrationService(tokenService, commonService, productRepository, subCategoryRepository);
    }

    private ProductRequestDto productRequestDto() {
        return ProductRequestDto.builder()
                .productName("옷")
                .productPrice(10000L)
                .build();
    }




    @Test
    @DisplayName("[Service] 상품 등록 성공")
    void productRegistrationSuccess() {

        user = User.builder()
                .email("abcd@naver.com")
                .name("홍길동")
                .nickname("바람")
                .phoneNumber("01000000000")
                .gender("male")
                .introduce("안녕하세요")
                .profileImgUrl("https://img_url")
                .role(Role.SELLER)
                .build();

        ReflectionTestUtils.setField(user, "id", 1L);

        category = Category.builder()
                .name("의류")
                .build();


        subCategory = SubCategory.builder()
                .category(category)
                .name("맨투맨")
                .build();

        //given
        given(tokenService.findUser(anyString())).willReturn(user);


        ProductRequestDto productRequestDto = productRequestDto();
        Product product = productRequestDto.toProductEntity(user, subCategory);

        ReflectionTestUtils.setField(product, "id", 1L);

        given(productRepository.save(any()))
                .willReturn(product);

        given(subCategoryRepository.findByName(anyString()))
                .willReturn(Optional.of(subCategory));

        String bearerAccessToken = "Bearer accessToken";

        //when
        Long productId = productRegistrationService.registration(bearerAccessToken, "맨투맨", productRequestDto);

        //then
        assertThat(productId).isEqualTo(product.getId());

    }

    @Test
    @DisplayName("[Service] 상품 등록 실패 - 판매자 권한 없음")
    void productRegistrationFail() {
        String bearerAccessToken = "Bearer accessToken";

        user = User.builder()
                .email("abcd@naver.com")
                .name("홍길동")
                .nickname("바람")
                .phoneNumber("01000000000")
                .gender("male")
                .introduce("안녕하세요")
                .profileImgUrl("https://img_url")
                .role(Role.USER)
                .build();

        ReflectionTestUtils.setField(user, "id", 1L);

        //given
        given(tokenService.findUser(anyString())).willReturn(user);

        //when
        //then
        assertThatThrownBy(() -> productRegistrationService.registration(bearerAccessToken, "맨투맨", productRequestDto()))
                .isInstanceOf(IllegalStateException.class);
    }
}