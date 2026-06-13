package com.nohit.ecommerce_project.service;

import java.io.*;
import java.util.*;

import javax.mail.*;

import com.nohit.ecommerce_project.model.*;

/**
 * Interface khai báo nghiệp vụ cho miền dữ liệu KhachHang.
 */
public interface KhachHangService {
    public List<KhachHang> getDsKhachHang();

    public KhachHang getKhachHang(int id);

    public KhachHang getKhachHang(String email);

    public KhachHang saveKhachHang(KhachHang khachHang);

    public void deleteKhachHang(int id);

    public KhachHang saveKhachHangWithoutPassword(KhachHang khachHang);

    public KhachHang updatePassword(int id, String password);

    public KhachHang resetPassword(String email) throws UnsupportedEncodingException, MessagingException;
}
