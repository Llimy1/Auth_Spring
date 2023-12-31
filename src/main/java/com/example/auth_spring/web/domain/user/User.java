package com.example.auth_spring.web.domain.user;


import com.example.auth_spring.type.Role;
import com.example.auth_spring.web.domain.address.Address;
import com.example.auth_spring.web.domain.cart.Cart;
import com.example.auth_spring.web.domain.common.BaseTimeEntity;
import com.example.auth_spring.web.domain.like.Like;
import com.example.auth_spring.web.domain.login.Login;
import com.example.auth_spring.web.domain.option.Option;
import com.example.auth_spring.web.domain.order.Order;
import com.example.auth_spring.web.domain.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String nickname;

    @Column(unique = true)
    private String phoneNumber;

    @Column
    private String gender;

    @Column
    private String profileImgUrl;

    @Column
    private String introduce;

    @Column
    private String provider;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Address> addressList;

    @OneToMany(mappedBy = "user")
    private List<Product> productList;

    @OneToMany(mappedBy = "user")
    private List<Cart> cartList;

    @OneToMany(mappedBy = "user")
    private List<Order> orderList;

    @OneToMany(mappedBy = "user")
    private List<Option> optionList;

    @OneToMany(mappedBy = "user")
    private List<Like> likeList;

    @OneToOne(mappedBy = "user")
    private Login login;




    @Builder
    public User(String email, String password, String name, String nickname, String phoneNumber, String gender, String profileImgUrl, String introduce, String provider, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.profileImgUrl = profileImgUrl;
        this.introduce = introduce;
        this.provider = provider;
        this.role = role;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public void roleUpdate() {
        this.role = Role.SELLER;

    }

    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public User update(String name, String profileImgUrl) {
        this.name = name;
        this.profileImgUrl = profileImgUrl;

        return this;
    }

    public void oauth2UserUpdate(String nickname, String phoneNumber, String gender, String introduce) {
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.introduce = introduce;
        this.role = Role.USER;

    }
}
