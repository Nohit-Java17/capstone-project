package com.nohit.ecommerce_project.model;

import lombok.*;

import java.io.*;

import javax.persistence.*;


/**
 * Khóa chính tổng hợp cho entity ChiTietDonHang trong cơ sở dữ liệu ecommerce.
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietDonHangId implements Serializable {
    @Column(name = "id_don_hang")
    private int idDonHang;

    @Column(name = "id_san_pham")
    private int idSanPham;
}
