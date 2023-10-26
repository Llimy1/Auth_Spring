//package com.example.auth_spring.web.controller.seller.registration.product;
//
//import com.example.auth_spring.security.jwt.service.JwtProvider;
//import com.example.auth_spring.service.seller.upload.S3UploadService;
//import com.example.auth_spring.service.user.token.TokenService;
//import com.example.auth_spring.service.common.CommonService;
//import com.example.auth_spring.service.seller.registration.product.ProductRegistrationService;
//import com.example.auth_spring.type.ErrorCode;
//import com.example.auth_spring.type.ResponseStatus;
//import com.example.auth_spring.type.Role;
//import com.example.auth_spring.type.SuccessCode;
//import com.example.auth_spring.web.dto.common.CommonResponse;
//import com.example.auth_spring.web.dto.product.ProductIdResponseDto;
//import com.example.auth_spring.web.dto.product.ProductListRequestDto;
//import com.example.auth_spring.web.dto.product.ProductRequestDto;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.commons.CommonsMultipartFile;
//
//import java.io.File;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(ProductRegistrationController.class)
//class ProductRegistrationControllerTest {
//
//    @MockBean
//    private ProductRegistrationService productRegistrationService;
//
//    @MockBean
//    private CommonService commonService;
//
//    @MockBean
//    private JwtProvider jwtProvider;
//
//    @MockBean
//    private S3UploadService s3UploadService;
//
//    @MockBean
//    private TokenService tokenService;
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @Autowired
//    private MockMvc mvc;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//
//    @BeforeEach
//    public void setup() {
//        mvc = MockMvcBuilders.webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }
//
//    private ProductRequestDto productRequestDto() {
//        return ProductRequestDto.builder()
//                .productName("옷")
//                .productPrice(10000L)
//                .deliveryPrice(3000)
//                .brandName("나이키")
//                .discountRate(35)
//                .isDiscount(true)
//                .build();
//    }
//
//    @Test
//    @DisplayName("[API] 상품 등록 성공")
//    @WithMockUser(roles = "SELLER")
//    void productRegistrationSuccess() throws Exception {
//        String subCategoryName = "testSubCategory";
//        String bearerAccessToken = "Bearer testToken";
//
//        ProductRequestDto productRequestDto = productRequestDto();
//
//        MockMultipartFile file1 = new MockMultipartFile("images", "imageFile1.jpg", MediaType.IMAGE_JPEG_VALUE, "ImageFile1Contents".getBytes());
//
//        ProductListRequestDto productListRequestDto1 =
//                new ProductListRequestDto(productRequestDto, List.of(file1));
//
//        CommonResponse<Object> commonResponseExpectedResult =
//                CommonResponse.builder()
//                        .httpStatus(HttpStatus.CREATED)
//                        .status(ResponseStatus.SUCCESS.getDescription())
//                        .message(SuccessCode.PRODUCT_REGISTRATION_SUCCESS.getDescription())
//                        .data(null)
//                        .build();
//
//        given(productRegistrationService.registrationResponse(any(), any(), anyList()))
//                .willReturn(commonResponseExpectedResult);
//
//        MockMultipartHttpServletRequestBuilder multipartReq =
//                (MockMultipartHttpServletRequestBuilder) multipart("/api/v1/seller/product/{subCategoryName}", subCategoryName)
//                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
//                        .with(csrf())
//                        .header("Authorization", bearerAccessToken);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        int index = 0;
//        for (ProductListRequestDto prodReq : Collections.singletonList(productListRequestDto1)) {
//            for (MultipartFile file : prodReq.getMultipartFiles()) {
//                String keyName = "productRequests[" + index + "].multipartFiles[" + index + "]";
//                multipartReq.file(new MockMultipartFile(keyName, file.getOriginalFilename(), file.getContentType(), file.getBytes()));
//                index++;
//            }
//
//            String jsonKeyName = "productRequests[" + index + "]";
//            byte[] prodReqJson = objectMapper.writeValueAsBytes(prodReq.getProductRequest());
//            multipartReq.file(new MockMultipartFile(jsonKeyName, "", MediaType.APPLICATION_JSON_VALUE, prodReqJson));
//
//            index++;
//        }
//
//        mvc.perform(multipartReq)
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.getDescription()))
//                .andExpect(jsonPath("$.message").value(SuccessCode.PRODUCT_REGISTRATION_SUCCESS.getDescription()))
//                .andDo(print());
//    }
//    @Test
//    @DisplayName("[API] 상품 등록 실패 - 판매자 권한 없음")
//    @WithMockUser(roles = "USER")
//    void productRegistrationFail() throws Exception {
//        MockMultipartFile image1 = new MockMultipartFile("images", "image1.jpg", MediaType.IMAGE_JPEG_VALUE, "image1".getBytes());
//        ProductRequestDto productRequestDto = productRequestDto();
//
//        List<ProductListRequestDto> productListRequestDtoList = List.of(ProductListRequestDto.builder()
//                .productRequest(productRequestDto)
//                .multipartFiles(List.of(image1))
//                .build());
//        MockMultipartFile multipartFile = new MockMultipartFile("productRequestList", "", MediaType.ALL_VALUE, (InputStream) productListRequestDtoList);
//
//        String bearerAccessToken = "Bearer accessToken";
//
//        CommonResponse<Object> commonResponse = CommonResponse.builder()
//                .httpStatus(HttpStatus.BAD_REQUEST)
//                .status(ResponseStatus.FAIL.getDescription())
//                .message(ErrorCode.AUTHORITY_NOT_SELLER.getDescription())
//                .data(null)
//                .build();
//
//        //given
//        given(tokenService.findUserRole(anyString())).willReturn(Role.USER.getKey());
//
//        given(productRegistrationService.registrationResponse(any(), any(), any()))
//                .willReturn(commonResponse);
//
//
//        //when
//        //then
//        mvc.perform(multipart("/api/v1/seller/product/{subCategoryName}", "서브 카테고리명")
//                        .file(multipartFile)
//                        .with(csrf())
//                        .header("Authorization", bearerAccessToken))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.status").value(ResponseStatus.FAIL.getDescription()))
//                .andExpect(jsonPath("$.message").value(ErrorCode.AUTHORITY_NOT_SELLER.getDescription()))
//                .andDo(print());
//    }
//
//}