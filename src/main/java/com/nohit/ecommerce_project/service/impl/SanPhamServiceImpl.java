package com.nohit.ecommerce_project.service.impl;

import lombok.*;

import java.util.*;

import javax.transaction.*;

import org.springframework.stereotype.*;

import com.nohit.ecommerce_project.model.*;
import com.nohit.ecommerce_project.repository.*;
import com.nohit.ecommerce_project.service.*;
import com.nohit.ecommerce_project.util.*;

import lombok.extern.slf4j.*;

import static java.util.stream.Collectors.*;

/**
 * Cài đặt nghiệp vụ và chuẩn hóa dữ liệu cho service SanPham.
 */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class SanPhamServiceImpl implements SanPhamService {
    private final SanPhamRepository sanPhamRepository;
    private final StringUtil stringUtil;
    private final TextUtil textUtil;

    @Override
    public List<SanPham> getDsSanPham() {
        log.info("Fetching all san_pham");
        return sanPhamRepository.findAll();
    }

    @Override
    public List<SanPham> getDsSanPham(String phanLoai) {
        log.info("Fetching all san_pham by phan_loai {}", phanLoai);
        var result = sanPhamRepository.findByPhanLoai(phanLoai);
        // Kiểm tra danh sách sản phẩm theo phân loại.
        if (result.isEmpty()) {
            result = sanPhamRepository.findAll();
        }
        return result.stream().filter(sanPham -> sanPham.getTonKho() > 0).collect(toList());
    }

    @Override
    public SanPham getSanPham(int id) {
        log.info("Fetching san_pham with id {}", id);
        return sanPhamRepository.findById(id).orElse(null);
    }

    @Override
    public SanPham getSanPham(String name) {
        log.info("Fetching san_pham with name {}", name);
        return sanPhamRepository.findByTen(name);
    }

    @Override
    public SanPham saveSanPham(SanPham sanPham) {
        sanPham.setTen(stringUtil.capitalizeCase(sanPham.getTen()));
        sanPham.setMoTa(textUtil.parseToLegalText(sanPham.getMoTa()));
        sanPham.setPhanLoai(stringUtil.sentenceCase(sanPham.getPhanLoai()));
        sanPham.setThuongHieu(stringUtil.upperCase(sanPham.getThuongHieu()));
        log.info("Saving san_pham with name: {}", sanPham.getTen());
        return sanPhamRepository.save(sanPham);
    }

    @Override
    public void updateTonKho(int id, int tonKho) {
        log.info("Update ton_kho san_pham with id: {}", id);
        sanPhamRepository.saveTonKho(id, tonKho);
    }

    @Override
    public void updateDanhGia(int id, int danhGia) {
        log.info("Update danh_gia san_pham with id: {}", id);
        sanPhamRepository.saveDanhGia(id, danhGia);
    }

    @Override
    public void deleteSanPham(int id) {
        log.info("Deleting san_pham with id: {}", id);
        sanPhamRepository.deleteById(id);
    }

    @Override
    public List<SanPham> getDsSanPhamTonKho() {
        log.info("Fetching all san_pham ton_kho");
        return sanPhamRepository.findAll().stream().filter(sanPham -> sanPham.getTonKho() > 0).collect(toList());
    }

    @Override
    public List<SanPham> getDsSanPhamTopSale() {
        var dsSanPham = getDsSanPhamTonKho();
        dsSanPham.sort(Comparator.comparingInt(SanPham::getTonKho));
        log.info("Fetching san_pham with top sale");
        return dsSanPham;
    }

    @Override
    public List<SanPham> getDsSanPhamNewest() {
        var dsSanPham = getDsSanPhamTonKho();
        dsSanPham.sort(Comparator.comparing(SanPham::getNgayNhap).reversed());
        log.info("Fetching san_pham with newest");
        return dsSanPham;
    }

    @Override
    public List<SanPham> getDsSanPhamAscendingPrice() {
        var dsSanPham = getDsSanPhamTonKho();
        dsSanPham.sort(Comparator.comparingInt(this::getGiaSauKhuyenMai));
        log.info("Fetching san_pham with ascending price");
        return dsSanPham;
    }

    @Override
    public List<SanPham> getDsSanPhamDescendingPrice() {
        var dsSanPham = getDsSanPhamTonKho();
        dsSanPham.sort(Comparator.comparingInt(this::getGiaSauKhuyenMai).reversed());
        log.info("Fetching san_pham with descending price");
        return dsSanPham;
    }

    @Override
    public List<SanPham> getDsSanPhamAscendingDiscount() {
        var dsSanPham = getDsSanPhamTonKho();
        dsSanPham.sort(Comparator.comparingInt(SanPham::getKhuyenMai));
        log.info("Fetching san_pham with ascending discount");
        return dsSanPham;
    }

    @Override
    public List<SanPham> getDsSanPhamDescendingDiscount() {
        var dsSanPham = getDsSanPhamTonKho();
        dsSanPham.sort(Comparator.comparingInt(SanPham::getKhuyenMai).reversed());
        log.info("Fetching san_pham with descending discount");
        return dsSanPham;
    }

    private int getGiaSauKhuyenMai(SanPham sanPham) {
        return sanPham.getGiaGoc() - sanPham.getKhuyenMai();
    }
}
