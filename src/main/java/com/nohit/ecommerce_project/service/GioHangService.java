package com.nohit.ecommerce_project.service;

import java.util.*;

import com.nohit.ecommerce_project.model.*;

/**
 * Interface khai báo nghiệp vụ cho miền dữ liệu GioHang.
 */
public interface GioHangService {
    public List<GioHang> getDsGioHang();

    public GioHang getGioHang(int id);

    public GioHang saveGioHang(GioHang gioHang);

    public void deleteGioHang(int id);

    public GioHang createGioHang(KhachHang khachHang);
}
