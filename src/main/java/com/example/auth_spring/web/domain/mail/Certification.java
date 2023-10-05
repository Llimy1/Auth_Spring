package com.example.auth_spring.web.domain.mail;

import com.example.auth_spring.web.domain.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "certification")
public class Certification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String code;

    @Builder
    public Certification(String email, String code) {
        this.email = email;
        this.code = code;
    }

    public void codeUpdate(String code) {
        this.code = code;

    }
}
