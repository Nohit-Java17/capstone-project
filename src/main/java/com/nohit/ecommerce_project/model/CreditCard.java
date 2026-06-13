package com.nohit.ecommerce_project.model;

import lombok.*;

import javax.persistence.*;


import static javax.persistence.FetchType.*;

/**
 * Entity JPA biểu diễn dữ liệu CreditCard trong miền thương mại điện tử.
 */
@Entity(name = "credit_card")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name_on_card")
    private String nameOnCard;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "expiration")
    private String expiration;

    @Column(name = "security_code")
    private String securityCode;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    private KhachHang khachHang;
}
