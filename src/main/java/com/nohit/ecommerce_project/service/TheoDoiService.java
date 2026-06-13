package com.nohit.ecommerce_project.service;

import java.util.*;

import com.nohit.ecommerce_project.model.*;

/**
 * Interface khai báo nghiệp vụ cho miền dữ liệu TheoDoi.
 */
public interface TheoDoiService {
    public List<TheoDoi> getDsTheoDoi();

    public TheoDoi getTheoDoi(int id);

    public TheoDoi getTheoDoi(String email);

    public TheoDoi saveTheoDoi(TheoDoi theoDoi);

    public void deleteTheoDoi(int id);
}
