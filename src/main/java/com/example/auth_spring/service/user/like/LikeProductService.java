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
    public String likeProduct(String bearerAccessToken, String productName) {

        User user = tokenService.findUser(bearerAccessToken);

        Long userId = user.getId();

        Product product = productRepository.findByName(productName)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));


        Like like = hasLike(userId, productName);

        // 값이 있다면 좋아요 수 -1 해당 행 삭제, 없다면 새로운 객체 생성 후 DB에 저장
        if (like == null) {
            product.increaseLike();
            return createLike(user, product);
        }

        product.decreaseLike();
        return deleteLike(like);
    }

    // API 반환
    @Transactional
    public CommonResponse<Object> likeProductResponse(String bearerAccessToken, String productName) {
        String successCode = likeProduct(bearerAccessToken, productName);
        return commonService.successResponse(successCode, HttpStatus.CREATED, null);
    }

    // // productId, userId 에 해당하는 좋아요가 있을 시 값 반환 없을 시 null 반환
    private Like hasLike(Long userId, String productName) {

        return likeRepository.findLikeByProductNameAndUserId(userId, productName)
                .orElse(null);
    }

    // 좋아요 추가
    private String createLike(User user, Product product) {

        Like like = Like.builder()
                .user(user)
                .product(product)
                .build();

        likeRepository.save(like);

        return SuccessCode.LIKE_PRODUCT_SUCCESS.getDescription();
    }

    // 좋아요 삭제
    private String deleteLike(Like like) {

        likeRepository.delete(like);

        return SuccessCode.UNLIKE_PRODUCT_SUCCESS.getDescription();
    }
}
