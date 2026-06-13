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
 * Cài đặt nghiệp vụ và chuẩn hóa dữ liệu cho service ChiTietGioHang.
 */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ChiTietGioHangServiceImpl implements ChiTietGioHangService {
    private final ChiTietGioHangRepository chiTietGioHangRepository;

    @Override
    public List<ChiTietGioHang> getDsChiTietGioHang() {
        log.info("Fetching all chi_tiet_gio_hang");
        return chiTietGioHangRepository.findAll();
    }

    @Override
    public ChiTietGioHang getChiTietGioHang(ChiTietGioHangId id) {
        log.info("Fetching chi_tiet_gio_hang with id: {}", id);
        return chiTietGioHangRepository.findById(id).orElse(null);
    }

    @Override
    public ChiTietGioHang saveChiTietGioHang(ChiTietGioHang chiTietGioHang) {
        chiTietGioHang.setTongTienSanPham(chiTietGioHang.getSoLuongSanPham() * chiTietGioHang.getGiaBanSanPham());
        log.info("Saving chi_tiet_gio_hang with id: {}", chiTietGioHang.getId());
        return chiTietGioHangRepository.save(chiTietGioHang);
    }

    @Override
    public void deleteChiTietGioHang(ChiTietGioHangId id) {
        log.info("Deleting chi_tiet_gio_hang with id: {}", id);
        chiTietGioHangRepository.deleteById(id);
    }
}
