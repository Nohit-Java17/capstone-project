package com.nohit.ecommerce_project.service;

import java.util.*;

import com.nohit.ecommerce_project.model.*;

/**
 * Interface khai báo nghiệp vụ cho miền dữ liệu NhanXet.
 */
public interface NhanXetService {
    public List<NhanXet> getDsNhanXet();

    public NhanXet getNhanXet(NhanXetId id);

    public NhanXet saveNhanXet(NhanXet nhanXet);

    public void deleteNhanXet(NhanXetId id);
}
