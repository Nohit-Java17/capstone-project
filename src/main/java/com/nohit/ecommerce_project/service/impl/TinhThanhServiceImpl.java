package com.nohit.ecommerce_project.service.impl;

import lombok.*;

import java.util.*;

import javax.transaction.*;

import org.springframework.stereotype.*;

import com.nohit.ecommerce_project.model.*;
import com.nohit.ecommerce_project.repository.*;
import com.nohit.ecommerce_project.service.*;
import com.nohit.ecommerce_project.util.*;

import lombok.extern.slf4j.*;

/**
 * Cài đặt nghiệp vụ và chuẩn hóa dữ liệu cho service TinhThanh.
 */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class TinhThanhServiceImpl implements TinhThanhService {
    private final TinhThanhRepository tinhThanhRepository;
    private final StringUtil stringUtil;

    @Override
    public List<TinhThanh> getDsTinhThanh() {
        log.info("Fetching all tinh_thanh");
        return tinhThanhRepository.findAll();
    }

    @Override
    public TinhThanh getTinhThanh(int id) {
        log.info("Fetching tinh_thanh with id {}", id);
        return tinhThanhRepository.findById(id).orElse(null);
    }

    @Override
    public TinhThanh saveTinhThanh(TinhThanh tinhThanh) {
        tinhThanh.setTen(stringUtil.titleCase(tinhThanh.getTen()));
        log.info("Saving tinh_thanh with name: {}", tinhThanh.getTen());
        return tinhThanhRepository.save(tinhThanh);
    }

    @Override
    public void deleteTinhThanh(int id) {
        log.info("Deleting tinh_thanh with id: {}", id);
        tinhThanhRepository.deleteById(id);
    }
}
