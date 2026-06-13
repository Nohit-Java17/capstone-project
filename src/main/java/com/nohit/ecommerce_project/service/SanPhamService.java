package com.nohit.ecommerce_project.service;

import java.util.*;

import com.nohit.ecommerce_project.model.*;

/**
 * Interface khai báo nghiệp vụ cho miền dữ liệu SanPham.
 */
public interface SanPhamService {
    public List<SanPham> getDsSanPham();

    public List<SanPham> getDsSanPham(String phanLoai);

    public SanPham getSanPham(int id);

    public SanPham getSanPham(String name);

    public SanPham saveSanPham(SanPham sanPham);

    public void updateTonKho(int id, int tonKho);

    public void updateDanhGia(int id, int danhGia);

    public void deleteSanPham(int id);

    public List<SanPham> getDsSanPhamTonKho();

    public List<SanPham> getDsSanPhamTopSale();

    public List<SanPham> getDsSanPhamNewest();

    public List<SanPham> getDsSanPhamAscendingPrice();

    public List<SanPham> getDsSanPhamDescendingPrice();

    public List<SanPham> getDsSanPhamAscendingDiscount();

    public List<SanPham> getDsSanPhamDescendingDiscount();
}
