package com.nohit.ecommerce_project.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import com.nohit.ecommerce_project.model.*;

/**
 * Repository Spring Data JPA truy cập dữ liệu cho entity NhanXet.
 */
@Repository
public interface NhanXetRepository extends JpaRepository<NhanXet, NhanXetId> {
}
