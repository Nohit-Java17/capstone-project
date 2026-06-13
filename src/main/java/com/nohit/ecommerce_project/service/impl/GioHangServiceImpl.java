package com.nohit.ecommerce_project.service.impl;

import lombok.*;

import java.util.*;

import javax.transaction.*;

import org.springframework.stereotype.*;

import com.nohit.ecommerce_project.model.*;
import com.nohit.ecommerce_project.repository.*;
import com.nohit.ecommerce_project.service.*;

import lombok.extern.slf4j.*;

/**
 * Cài đặt nghiệp vụ và chuẩn hóa dữ liệu cho service GioHang.
 */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class GioHangServiceImpl implements GioHangService {
    private final GioHangRepository gioHangRepository;

    @Override
    public List<GioHang> getDsGioHang() {
        log.info("Fetching all gio_hang");
        return gioHangRepository.findAll();
    }

    @Override
    public GioHang getGioHang(int id) {
        log.info("Fetching gio_hang with id {}", id);
        return gioHangRepository.findById(id).orElse(null);
    }

    @Override
    public GioHang saveGioHang(GioHang gioHang) {
        log.info("Saving gio_hang with id: {}", gioHang.getId());
        return gioHangRepository.save(gioHang);
    }

    @Override
    public void deleteGioHang(int id) {
        log.info("Deleting gio_hang with id: {}", id);
        gioHangRepository.deleteById(id);
    }

    @Override
    public GioHang createGioHang(KhachHang khachHang) {
        var gioHang = new GioHang();
        gioHang.setId(khachHang.getId());
        gioHang.setIdTinhThanh(khachHang.getIdTinhThanh());
        log.info("Create gio_hang with email: {}", khachHang.getEmail());
        return gioHangRepository.save(gioHang);
    }
}
