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
 * Cài đặt nghiệp vụ và chuẩn hóa dữ liệu cho service NhanXet.
 */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class NhanXetServiceImpl implements NhanXetService {
    private final NhanXetRepository nhanXetRepository;
    private final TextUtil textUtil;

    @Override
    public List<NhanXet> getDsNhanXet() {
        log.info("Fetching all nhan_xet");
        return nhanXetRepository.findAll();
    }

    @Override
    public NhanXet getNhanXet(NhanXetId id) {
        log.info("Fetching nhan_xet with id {}", id);
        return nhanXetRepository.findById(id).orElse(null);
    }

    @Override
    public NhanXet saveNhanXet(NhanXet nhanXet) {
        nhanXet.setBinhLuan(textUtil.parseToLegalText(nhanXet.getBinhLuan()));
        log.info("Saving nhan_xet with id: {}", nhanXet.getId());
        return nhanXetRepository.save(nhanXet);
    }

    @Override
    public void deleteNhanXet(NhanXetId id) {
        log.info("Deleting nhan_xet with id: {}", id);
        nhanXetRepository.deleteById(id);
    }
}
