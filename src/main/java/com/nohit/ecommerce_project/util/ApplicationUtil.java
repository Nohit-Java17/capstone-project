package com.nohit.ecommerce_project.util;

import lombok.*;

import java.util.*;

import org.springframework.stereotype.*;
import org.springframework.web.servlet.*;

import com.nohit.ecommerce_project.model.*;
import com.nohit.ecommerce_project.service.*;

import static com.nohit.ecommerce_project.common.Bean.*;
import static com.nohit.ecommerce_project.constant.AttributeConstant.*;

/**
 * Tiện ích gom các thao tác lặp lại cho giỏ hàng, phân trang và thông báo giao diện.
 */
@Component
@RequiredArgsConstructor
public class ApplicationUtil {
    private final GioHangService gioHangService;

    // Lấy giỏ hàng hiện tại hoặc tạo mới khi khách hàng chưa có giỏ.
    public GioHang getOrDefaultGioHang(KhachHang khachHang) {
        if (khachHang == null) {
            return new GioHang();
        } else {
            var gioHang = khachHang.getGioHang();
            return gioHang == null ? gioHangService.createGioHang(khachHang) : gioHang;
        }
    }

    // Giới hạn số phần tử hiển thị mà không làm lỗi khi dữ liệu ít hơn kỳ vọng.
    public <T> List<T> limit(List<T> items, int maxSize) {
        if (items == null || items.isEmpty() || maxSize <= 0) {
            return Collections.emptyList();
        }
        return items.subList(0, Math.min(items.size(), maxSize));
    }

    // Cắt dữ liệu phân trang và tự kẹp page vào khoảng hợp lệ.
    public <T> List<T> page(List<T> items, int page, int pageSize) {
        if (items == null || items.isEmpty() || pageSize <= 0) {
            return Collections.emptyList();
        }
        var currentPage = Math.min(Math.max(page, 1), maxPage(items, pageSize));
        var fromIndex = (currentPage - 1) * pageSize;
        var toIndex = Math.min(fromIndex + pageSize, items.size());
        return items.subList(fromIndex, toIndex);
    }

    // Tính tổng số trang, luôn trả tối thiểu 1 để template không bị trạng thái rỗng khó xử lý.
    public int maxPage(List<?> items, int pageSize) {
        if (items == null || items.isEmpty() || pageSize <= 0) {
            return 1;
        }
        return (items.size() - 1) / pageSize + 1;
    }

    // Đưa thông báo flash-like từ Bean chung vào ModelAndView rồi tắt cờ hiển thị.
    public boolean showMessageBox(ModelAndView mav) {
        if (_isMsgShow) {
            mav.addObject(FLAG_MSG_PARAM, true);
            mav.addObject(MSG_PARAM, _msg);
        }
        return false;
    }
}
