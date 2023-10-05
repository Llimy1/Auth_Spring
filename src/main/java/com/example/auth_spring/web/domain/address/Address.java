package com.example.auth_spring.web.domain.address;

import com.example.auth_spring.web.domain.order.Order;
import com.example.auth_spring.web.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String zipCode;

    @Column
    private String streetAddress;

    @Column
    private String detailAddress;

    @Column
    private Boolean isDefault;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "address")
    private List<Order> orderList;

    @Builder
    public Address(String zipCode, String streetAddress, String detailAddress, User user, Boolean isDefault) {
        this.zipCode = zipCode;
        this.streetAddress = streetAddress;
        this.detailAddress = detailAddress;
        this.user = user;
        this.isDefault = isDefault;
    }

    public void addressUpdate(String zipCode, String streetAddress, String detailAddress, Boolean isDefault) {
        this.zipCode = zipCode;
        this.streetAddress = streetAddress;
        this.detailAddress = detailAddress;
        this.isDefault = isDefault;
    }
}
