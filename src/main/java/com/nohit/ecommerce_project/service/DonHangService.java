package com.nohit.ecommerce_project.service;

import java.util.*;

import com.nohit.ecommerce_project.model.*;

/**
 * Interface khai báo nghiệp vụ cho miền dữ liệu DonHang.
 */
public interface DonHangService {
    public List<DonHang> getDsDonHang();

    public DonHang getDonHang(int id);

    public DonHang saveDonHang(DonHang donHang);

    public void deleteDonHang(int id);
}
