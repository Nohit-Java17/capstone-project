package com.nohit.ecommerce_project.service;

import java.util.*;

import com.nohit.ecommerce_project.model.*;

/**
 * Interface khai báo nghiệp vụ cho miền dữ liệu TinhThanh.
 */
public interface TinhThanhService {
    public List<TinhThanh> getDsTinhThanh();

    public TinhThanh getTinhThanh(int id);

    public TinhThanh saveTinhThanh(TinhThanh tinhThanh);

    public void deleteTinhThanh(int id);
}
