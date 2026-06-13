package com.nohit.ecommerce_project.model;

import lombok.*;

import java.util.*;

import javax.persistence.*;


import static javax.persistence.GenerationType.*;

/**
 * Entity JPA biểu diễn dữ liệu TinhThanh trong miền thương mại điện tử.
 */
@Entity(name = "tinh_thanh")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class TinhThanh {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private int id;

    @NonNull
    @Column(name = "ten")
    private String ten;

    @Column(name = "chi_phi_van_chuyen")
    private int chiPhiVanChuyen;

    @OneToMany(mappedBy = "tinhThanh")
    private List<KhachHang> dsKhachHang;

    @OneToMany(mappedBy = "tinhThanh")
    private List<NguoiNhan> dsNguoiNhan;

    @OneToMany(mappedBy = "tinhThanh")
    private List<GioHang> dsGioHang;
}
