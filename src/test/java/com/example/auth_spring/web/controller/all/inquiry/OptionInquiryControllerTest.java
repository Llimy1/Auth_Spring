package com.example.auth_spring.web.controller.all.inquiry;

import com.example.auth_spring.security.jwt.service.JwtProvider;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.all.inquiry.OptionInquiryService;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.type.ResponseStatus;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.controller.all.inquiry.OptionInquiryController;
import com.example.auth_spring.web.domain.option.Option;
import com.example.auth_spring.web.domain.product.Product;
import com.example.auth_spring.web.domain.productoption.ProductOption;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.option.OptionListResponseDto;
import com.example.auth_spring.web.dto.option.OptionResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OptionInquiryController.class)
class OptionInquiryControllerTest {

    @MockBean
    private OptionInquiryService optionInquiryService;

    @MockBean
    private CommonService commonService;

    @MockBean
    private JwtProvider jwtProvider;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    private User user;
    private Product product;
    private Option option;
    private ProductOption productOption;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("[API] 옵션 조회 성공")
    @WithMockUser(roles = "USER")
    void optionInquirySuccess() throws Exception {


        user = new User();
        ReflectionTestUtils.setField(user, "id", 1L);

        product = new Product();
        ReflectionTestUtils.setField(product, "id", 1L);

        option = Option.builder()
                .user(user)
                .size("XL")
                .color("RED")
                .build();

        OptionListResponseDto optionListResponseDto = OptionListResponseDto.builder()
                .optionList(List.of(  OptionResponseDto.builder()
                                .size(option.getSize())
                                .color(option.getColor())
                        .build()))
                .build();

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .httpStatus(HttpStatus.OK)
                .status(ResponseStatus.SUCCESS.getDescription())
                .message(SuccessCode.OPTION_LIST_INQUIRY_SUCCESS.getDescription())
                .data(optionListResponseDto)
                .build();

        //given
        given(optionInquiryService.optionInquiryResponse(anyString()))
                .willReturn(commonResponse);

        //when
        //then
        mvc.perform(get("/api/v1/all/option")
                        .with(csrf())
                        .param("productName", "productName")
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.getDescription()))
                .andExpect(jsonPath("$.message").value(SuccessCode.OPTION_LIST_INQUIRY_SUCCESS.getDescription()))
                .andExpect(jsonPath("$.data.optionList[0].size").value(optionListResponseDto.getOptionList().get(0).getSize()))
                .andExpect(jsonPath("$.data.optionList[0].color").value(optionListResponseDto.getOptionList().get(0).getColor()))
                .andDo(print());
    }

}