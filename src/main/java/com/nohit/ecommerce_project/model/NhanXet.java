package com.nohit.ecommerce_project.model;

import lombok.*;

import javax.persistence.*;


import static javax.persistence.FetchType.*;

/**
 * Entity JPA biểu diễn dữ liệu NhanXet trong miền thương mại điện tử.
 */
@Entity(name = "nhan_xet")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NhanXet {
    @EmbeddedId
    private NhanXetId id;

    @Column(name = "danh_gia")
    private int danhGia;

    @Column(name = "binh_luan")
    private String binhLuan;

    @ManyToOne(fetch = LAZY)
    @MapsId("id_khach_hang")
    @JoinColumn(name = "id_khach_hang", referencedColumnName = "id", insertable = false, updatable = false)
    private KhachHang khachHang;

    @ManyToOne(fetch = LAZY)
    @MapsId("id_san_pham")
    @JoinColumn(name = "id_san_pham", referencedColumnName = "id", insertable = false, updatable = false)
    private SanPham sanPham;
}
