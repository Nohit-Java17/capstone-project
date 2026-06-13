package com.nohit.ecommerce_project.model;

import lombok.*;

import javax.persistence.*;


import static javax.persistence.GenerationType.*;

/**
 * Entity JPA biểu diễn dữ liệu TheoDoi trong miền thương mại điện tử.
 */
@Entity(name = "theo_doi")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class TheoDoi {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private int id;

    @NonNull
    @Column(name = "thu_dien_tu")
    private String email;
}
