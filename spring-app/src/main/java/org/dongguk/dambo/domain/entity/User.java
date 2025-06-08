package org.dongguk.dambo.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.dongguk.dambo.domain.type.EJob;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "login_id", nullable = false, unique = true)
    private String loginId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    @Column(name = "job", nullable = false, length = 50)
    private EJob job;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "cash", nullable = false)
    private Long cash;

    @Column(name = "wallet_addr", nullable = false, length = 500)
    private String walletAddr;

    @Builder(access = AccessLevel.PRIVATE)
    public User(String loginId, String password, String name, LocalDate birth,
                EJob job, String phoneNumber, String walletAddr) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.job = job;
        this.phoneNumber = phoneNumber;
        this.cash = 0L;
        this.walletAddr = walletAddr;
    }

    public static User create(String loginId, String password, String name, LocalDate birth,
                              EJob job, String phoneNumber, String walletAddr) {
        return User.builder()
                .loginId(loginId)
                .password(password)
                .name(name)
                .birth(birth)
                .job(job)
                .phoneNumber(phoneNumber)
                .walletAddr(walletAddr)
                .build();
    }
}
