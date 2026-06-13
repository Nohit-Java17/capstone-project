package com.nohit.ecommerce_project.service;

import java.util.*;

import com.nohit.ecommerce_project.model.*;

/**
 * Interface khai báo nghiệp vụ cho miền dữ liệu ChiTietDonHang.
 */
public interface ChiTietDonHangService {
    public List<ChiTietDonHang> getDsChiTietDonHang();

    public ChiTietDonHang getChiTietDonHang(ChiTietDonHangId id);

    public ChiTietDonHang saveChiTietDonHang(ChiTietDonHang chiTietDonHang);

    public void deleteChiTietDonHang(ChiTietDonHangId id);
}
