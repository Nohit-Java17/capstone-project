package com.nohit.ecommerce_project.service;

import java.util.*;

import com.nohit.ecommerce_project.model.*;

/**
 * Interface khai báo nghiệp vụ cho miền dữ liệu NguoiNhan.
 */
public interface NguoiNhanService {
    public List<NguoiNhan> getDsNguoiNhan();

    public NguoiNhan getNguoiNhan(int id);

    public NguoiNhan saveNguoiNhan(NguoiNhan nguoiNhan);

    public void deleteNguoiNhan(int id);
}
