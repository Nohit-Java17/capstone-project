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
 * Cài đặt nghiệp vụ và chuẩn hóa dữ liệu cho service ThuPhanHoi.
 */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ThuPhanHoiServiceImpl implements ThuPhanHoiService {
    private final ThuPhanHoiRepository phanHoiRepository;
    private final StringUtil stringUtil;
    private final TextUtil textUtil;

    @Override
    public List<ThuPhanHoi> getDsThuPhanHoi() {
        log.info("Fetching all thu_phan_hoi");
        return phanHoiRepository.findAll();
    }

    @Override
    public ThuPhanHoi getThuPhanHoi(int id) {
        log.info("Fetching thu_phan_hoi with id {}", id);
        return phanHoiRepository.findById(id).orElse(null);
    }

    @Override
    public ThuPhanHoi saveThuPhanHoi(ThuPhanHoi thuPhanHoi) {
        thuPhanHoi.setEmail(stringUtil.parseEmail(thuPhanHoi.getEmail()));
        thuPhanHoi.setHoTen(stringUtil.parseName(thuPhanHoi.getHoTen()));
        thuPhanHoi.setNoiDung(textUtil.parseToLegalText(thuPhanHoi.getNoiDung()));
        log.info("Saving thu_phan_hoi with email: {}", thuPhanHoi.getEmail());
        return phanHoiRepository.save(thuPhanHoi);
    }

    @Override
    public void deleteThuPhanHoi(int id) {
        log.info("Deleting thu_phan_hoi with id: {}", id);
        phanHoiRepository.deleteById(id);
    }
}
