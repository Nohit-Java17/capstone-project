package com.nohit.ecommerce_project.model;

import lombok.*;

import java.io.*;

import javax.persistence.*;


/**
 * Khóa chính tổng hợp cho entity ChiTietGioHang trong cơ sở dữ liệu ecommerce.
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietGioHangId implements Serializable {
    @Column(name = "id_gio_hang")
    private int idGioHang;

    @Column(name = "id_san_pham")
    private int idSanPham;
}
