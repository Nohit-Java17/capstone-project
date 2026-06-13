package com.nohit.ecommerce_project.repository;

import java.util.*;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;

import com.nohit.ecommerce_project.model.*;

/**
 * Repository Spring Data JPA truy cập dữ liệu cho entity SanPham.
 */
@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
    public List<SanPham> findByPhanLoai(String phanLoai);

    public SanPham findByTen(String name);

    @Modifying
    @Query("UPDATE san_pham sp SET sp.tonKho = :tonKho WHERE sp.id = :id")
    public void saveTonKho(@Param("id") int id, @Param("tonKho") int tonKho);

    @Modifying
    @Query("UPDATE san_pham sp SET sp.danhGia = :danhGia WHERE sp.id = :id")
    public void saveDanhGia(@Param("id") int id, @Param("danhGia") int danhGia);
}
