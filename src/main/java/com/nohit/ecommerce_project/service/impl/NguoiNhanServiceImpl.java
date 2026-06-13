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
 * Cài đặt nghiệp vụ và chuẩn hóa dữ liệu cho service NguoiNhan.
 */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class NguoiNhanServiceImpl implements NguoiNhanService {
    private final NguoiNhanRepository nguoiNhanRepository;
    private final StringUtil stringUtil;
    private final TextUtil textUtil;
    private final AddressUtil addressUtil;

    @Override
    public List<NguoiNhan> getDsNguoiNhan() {
        log.info("Fetching all nguoi_nhan");
        return nguoiNhanRepository.findAll();
    }

    @Override
    public NguoiNhan getNguoiNhan(int id) {
        log.info("Fetching nguoi_nhan with id {}", id);
        return nguoiNhanRepository.findById(id).orElse(null);
    }

    @Override
    public NguoiNhan saveNguoiNhan(NguoiNhan nguoiNhan) {
        nguoiNhan.setHoTen(stringUtil.parseName(nguoiNhan.getHoTen()));
        nguoiNhan.setDiaChi(addressUtil.parseToLegalAddress(nguoiNhan.getDiaChi()));
        nguoiNhan.setXaPhuong(addressUtil.parseToLegalAddress(nguoiNhan.getXaPhuong()));
        nguoiNhan.setHuyenQuan(addressUtil.parseToLegalAddress(nguoiNhan.getHuyenQuan()));
        nguoiNhan.setGhiChu(textUtil.parseToLegalText(nguoiNhan.getGhiChu()));
        log.info("Saving nguoi_nhan with name: {}", nguoiNhan.getHoTen());
        return nguoiNhanRepository.save(nguoiNhan);
    }

    @Override
    public void deleteNguoiNhan(int id) {
        log.info("Deleting nguoi_nhan with id: {}", id);
        nguoiNhanRepository.deleteById(id);
    }
}
