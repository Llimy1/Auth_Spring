package com.example.auth_spring.service.user.like;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.like.Like;
import com.example.auth_spring.web.domain.like.LikeRepository;
import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.domain.product.ProductRepository;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikeProductService {

    private final TokenService tokenService;
    private final CommonService commonService;
    private final LikeRepository likeRepository;
    private final ProductRepository productRepository;


    // 좋아요 추가
    @Transactional
    public void likeProduct(String bearerAccessToken, String productName) {

        User user = tokenService.findUser(bearerAccessToken);

        Long userId = user.getId();

        Product product = productRepository.findByName(productName)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));

        // productId, useId 에 해당하는 좋아요가 있을 시 값 반환 없을 시 null 반환
        Like like = likeRepository.findLikeByProductNameAndUserId(userId, productName)
                        .orElse(null);

        // 값이 있다면 좋아요 수 -1 해당 행 삭제, null 이면 새로운 객체 생성 후 DB에 저장
        if (like != null) {
            product.decreaseLike();
            likeRepository.delete(like);

        } else {
            likeRepository.save(Like.builder()
                            .user(user)
                            .product(product)
                    .build());
            product.increaseLike();
        }
    }

    // API 반환
    @Transactional
    public CommonResponse<Object> likeProductResponse(String bearerAccessToken, String productName) {
        likeProduct(bearerAccessToken, productName);
        return commonService.successResponse(SuccessCode.LIKE_PRODUCT_SUCCESS.getDescription(), HttpStatus.CREATED, null);
    }
}
