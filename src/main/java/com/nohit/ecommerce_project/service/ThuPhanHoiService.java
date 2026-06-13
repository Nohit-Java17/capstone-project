package com.nohit.ecommerce_project.service;

import java.util.*;

import com.nohit.ecommerce_project.model.*;

/**
 * Interface khai báo nghiệp vụ cho miền dữ liệu ThuPhanHoi.
 */
public interface ThuPhanHoiService {
    public List<ThuPhanHoi> getDsThuPhanHoi();

    public ThuPhanHoi getThuPhanHoi(int id);

    public ThuPhanHoi saveThuPhanHoi(ThuPhanHoi phanHoi);

    public void deleteThuPhanHoi(int id);
}
