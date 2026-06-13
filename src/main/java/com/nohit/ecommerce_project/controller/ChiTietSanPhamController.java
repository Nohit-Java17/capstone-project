package com.nohit.ecommerce_project.controller;

import lombok.*;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.nohit.ecommerce_project.model.*;
import com.nohit.ecommerce_project.service.*;
import com.nohit.ecommerce_project.util.*;

import static com.nohit.ecommerce_project.common.Bean.*;
import static com.nohit.ecommerce_project.constant.ApplicationConstant.Menu.*;
import static com.nohit.ecommerce_project.constant.AttributeConstant.*;
import static com.nohit.ecommerce_project.constant.TemplateConstant.*;
import static com.nohit.ecommerce_project.constant.ViewConstant.*;
import static java.lang.Math.*;

/**
 * Controller hiển thị chi tiết sản phẩm và ghi nhận đánh giá của khách hàng.
 */
@Controller
@RequestMapping(DETAIL_VIEW)
@RequiredArgsConstructor
public class ChiTietSanPhamController {
    private final SanPhamService sanPhamService;
    private final NhanXetService nhanXetService;
    private final AuthenticationUtil authenticationUtil;
    private final ApplicationUtil applicationUtil;

    @GetMapping("")
    public String detail() {
        _isMsgShow = true;
        _msg = "Cần chọn 1 sản phẩm để xem!";
        return REDIRECT_PREFIX + PRODUCT_VIEW;
    }

    // Hiển thị chi tiết sản phẩm.
    @GetMapping(FIND_VIEW)
    public ModelAndView detailFind(int id) {
        var product = sanPhamService.getSanPham(id);
        // Kiểm tra sản phẩm còn tồn tại và còn hàng.
        if (product == null || product.getTonKho() < 1) {
            _isMsgShow = true;
            _msg = "Sản phẩm không còn tồn tại!";
            return new ModelAndView(REDIRECT_PREFIX + PRODUCT_VIEW);
        } else {
            var mav = new ModelAndView(DETAIL_TEMP);
            var client = authenticationUtil.getAccount();
            mav.addObject(TITLE_PARAM, CHI_TIET);
            mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
            mav.addObject(LOGIN_PARAM, client != null);
            mav.addObject(PRODUCT_PARAM, product);
            mav.addObject(TOP_DISCOUNTS_PARAM, applicationUtil.limit(sanPhamService.getDsSanPhamDescendingDiscount(), 3));
            mav.addObject(TOP_NEWS_PARAM, applicationUtil.limit(sanPhamService.getDsSanPhamNewest(), 3));
            mav.addObject(TOP_SALES_PARAM, applicationUtil.limit(sanPhamService.getDsSanPhamTopSale(), 4));
            _isMsgShow = applicationUtil.showMessageBox(mav);
            return mav;
        }
    }

    // Ghi nhận đánh giá sản phẩm.
    @PostMapping(RATE_VIEW)
    public String detailRate(NhanXet nhanXet, int idSanPham) {
        var client = authenticationUtil.getAccount();
        _isMsgShow = true;
        // Kiểm tra phiên đăng nhập hiện tại còn hợp lệ.
        if (client == null) {
            _msg = "Cần đăng nhập để nhận xét sản phẩm!";
            return REDIRECT_PREFIX + LOGIN_VIEW;
        } else {
            var product = sanPhamService.getSanPham(idSanPham);
            // Kiểm tra sản phẩm còn tồn tại và còn hàng.
            if (product == null || product.getTonKho() < 1) {
                _msg = "Sản phẩm không còn tồn tại!";
                return REDIRECT_PREFIX + PRODUCT_VIEW;
            } else {
                var votes = product.getDsNhanXet();
                var votesSize = votes.size();
                var rate = 0;
                // Tính tổng điểm đánh giá hiện có của sản phẩm.
                for (var i = 0; i < votesSize; i++) {
                    rate += votes.get(i).getDanhGia();
                }
                var id = new NhanXetId(client.getId(), idSanPham);
                var clientVote = nhanXetService.getNhanXet(id);
                if (clientVote != null) {
                    rate -= clientVote.getDanhGia();
                } else {
                    votesSize++;
                }
                product.setDanhGia(round((rate + nhanXet.getDanhGia()) / votesSize));
                sanPhamService.updateDanhGia(idSanPham, product.getDanhGia());
                nhanXet.setId(id);
                nhanXet = nhanXetService.saveNhanXet(nhanXet);
                _msg = "Nhận xét sản phẩm thành công!";
                return REDIRECT_PREFIX + DETAIL_VIEW + FIND_VIEW + "?id=" + product.getId();
            }
        }
    }
}
