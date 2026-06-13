package com.nohit.ecommerce_project.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import com.nohit.ecommerce_project.model.*;

/**
 * Repository Spring Data JPA truy cập dữ liệu cho entity KhachHang.
 */
@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    public KhachHang findByEmail(String email);
}
