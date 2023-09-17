package com.example.auth_spring.service.auth.signup;

import com.example.auth_spring.web.domain.address.Address;
import com.example.auth_spring.web.domain.address.AddressRepository;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.domain.user.UserRepository;
import com.example.auth_spring.web.dto.SignupRequestDto;
import com.example.auth_spring.web.dto.SignupResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SignupService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public Long signup(SignupRequestDto signupRequestDto) {
        User user = signupRequestDto.toUserEntity();
        Long userId = userRepository.save(user).getId();

        Address address = signupRequestDto.toAddressEntity(user);
        addressRepository.save(address).getUser().getId();

        return userId;
    }
}
