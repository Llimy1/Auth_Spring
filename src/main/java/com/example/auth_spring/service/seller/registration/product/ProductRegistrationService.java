package com.example.auth_spring.service.seller.registration.product;

import com.example.auth_spring.service.seller.upload.S3UploadService;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.Role;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.brand.Brand;
import com.example.auth_spring.web.domain.brand.BrandRepository;
import com.example.auth_spring.web.domain.image.Image;
import com.example.auth_spring.web.domain.image.ImageRepository;
import com.example.auth_spring.web.domain.option.Option;
import com.example.auth_spring.web.domain.option.OptionRepository;
import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.domain.product.ProductRepository;
import com.example.auth_spring.web.domain.productoption.ProductOption;
import com.example.auth_spring.web.domain.productoption.ProductOptionRepository;
import com.example.auth_spring.web.domain.subcategory.SubCategory;
import com.example.auth_spring.web.domain.subcategory.SubCategoryRepository;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.domain.view.View;
import com.example.auth_spring.web.domain.view.ViewRepository;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.product.ProductIdResponseDto;
import com.example.auth_spring.web.dto.product.ProductListRequestDto;
import com.example.auth_spring.web.dto.product.ProductRequestDto;
import com.example.auth_spring.web.exception.IllegalStateException;
import com.example.auth_spring.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductRegistrationService {

    private final TokenService tokenService;
    private final CommonService commonService;
    private final S3UploadService s3UploadService;
    private final ProductRepository productRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final ProductOptionRepository productOptionRepository;
    private final OptionRepository optionRepository;
    private final BrandRepository brandRepository;
    private final ViewRepository viewRepository;
    private final ImageRepository imageRepository;


    // 상품 등록
    @Transactional
    public void registration(String bearerAccessToken, String subCategoryName, List<ProductListRequestDto> productListRequestDtoList) {

        tokenService.accessTokenExpiration(bearerAccessToken);

        User user = tokenService.findUser(bearerAccessToken);

        if (!user.getRoleKey().equals(Role.SELLER.getKey())) {
            throw new IllegalStateException(ErrorCode.AUTHORITY_NOT_SELLER);
        }

        SubCategory subCategory = subCategoryRepository.findByName(subCategoryName)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SUB_CATEGORY_NOT_FOUND));

        for (ProductListRequestDto productListRequest : productListRequestDtoList) {

            ProductRequestDto productRequest = productListRequest.getProductRequest();
            List<MultipartFile> multipartFiles = productListRequest.getMultipartFiles();

            String brandName = productRequest.getBrandName();

            Brand brand = brandRepository.findByName(brandName)
                    .orElseThrow(() -> new NotFoundException(ErrorCode.BRAND_NOT_FOUND));

            Product product = productRequest.toProductEntity(user, subCategory, brand);

            productRepository.save(product);

            View view = productRequest.toViewEntity(product);

            viewRepository.save(view);

            Option option = productRequest.getOptionRequestDto().toOptionEntity(user);

            optionRepository.save(option);

            ProductOption productOption = ProductOption.builder()
                    .option(option)
                    .product(product)
                    .stock(productRequest.getProductStock())
                    .build();

            productOptionRepository.save(productOption);

            imageRegistration(multipartFiles, product);
        }
    }

    // API 반환
    @Transactional
    public CommonResponse<Object> registrationResponse(String bearerAccessToken, String subCategoryName, List<ProductListRequestDto> productListRequestDtoList) {
        registration(bearerAccessToken, subCategoryName, productListRequestDtoList);

        return commonService.successResponse(SuccessCode.PRODUCT_REGISTRATION_SUCCESS.getDescription(), HttpStatus.CREATED, null);
    }


    // 이미지 저장
    private void imageRegistration(List<MultipartFile> multipartFiles, Product product) {

        List<String> imageUrlList = s3UploadService.upload(multipartFiles);

        Integer imageSequence = 1;

        for (String imageUrl : imageUrlList) {
            Image image = Image.builder()
                    .imageSequence(imageSequence)
                    .imageUrl(imageUrl)
                    .product(product)
                    .build();

            imageRepository.save(image);

            imageSequence++;
        }
    }
}
