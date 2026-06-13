package com.nohit.ecommerce_project.model;

import lombok.*;

import java.io.*;

import javax.persistence.*;


/**
 * Khóa chính tổng hợp cho entity NhanXet trong cơ sở dữ liệu ecommerce.
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NhanXetId implements Serializable {
    @Column(name = "id_khach_hang")
    private int idKhachHang;

    @Column(name = "id_san_pham")
    private int idSanPham;
}
