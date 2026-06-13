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

/**
 * Controller điều hướng trang chủ, đăng nhập, tìm kiếm và đăng ký nhận thông báo.
 */
@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class ApplicationController {
    private final SanPhamService sanPhamService;
    private final TheoDoiService theoDoiService;
    private final AuthenticationUtil authenticationUtil;
    private final ApplicationUtil applicationUtil;

    // Hiển thị trang đăng nhập.
    @GetMapping(LOGIN_VIEW)
    public ModelAndView login(boolean error) {
        // Kiểm tra người dùng đã đăng nhập.
        if (authenticationUtil.getAccount() != null) {
            return new ModelAndView(REDIRECT_PREFIX + INDEX_VIEW);
        } else {
            var mav = new ModelAndView(LOGIN_TEMP);
            // Hiển thị thông báo khi đăng nhập thất bại.
            if (error) {
                _isMsgShow = true;
                _msg = "Tài khoản đăng nhập chưa đúng!";
            }
            mav.addObject(TITLE_PARAM, DANG_NHAP);
            _isMsgShow = applicationUtil.showMessageBox(mav);
            return mav;
        }
    }

    // Hiển thị trang chủ.
    @GetMapping(value = { INDEX_VIEW, "/", "" })
    public ModelAndView index() {
        var mav = new ModelAndView(INDEX_TEMP);
        var client = authenticationUtil.getAccount();
        var newestProducts = sanPhamService.getDsSanPhamNewest();
        mav.addObject(TITLE_PARAM, TRANG_CHU);
        mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
        mav.addObject(LOGIN_PARAM, client != null);
        mav.addObject(NEW_PRODUCTS_PARAM, applicationUtil.limit(newestProducts, 6));
        mav.addObject(TOP_SALES_PARAM, applicationUtil.limit(sanPhamService.getDsSanPhamTopSale(), 3));
        mav.addObject(TOP_DISCOUNTS_PARAM, applicationUtil.limit(sanPhamService.getDsSanPhamDescendingDiscount(), 3));
        mav.addObject(TOP_NEWS_PARAM, applicationUtil.limit(newestProducts, 3));
        _isMsgShow = applicationUtil.showMessageBox(mav);
        return mav;
    }

    // Lưu thông tin đăng ký/phản hồi.
    @PostMapping(SUBCRIBE_VIEW)
    public String subcribe(TheoDoi theoDoi) {
        _isMsgShow = true;
        // Kiểm tra email đã tồn tại.
        if (theoDoiService.getTheoDoi(theoDoi.getEmail()) != null) {
            _msg = "Email này đã được đăng ký!";
        } else {
            theoDoiService.saveTheoDoi(theoDoi);
            _msg = "Đăng ký nhận thông báo thành công!";
        }
        return REDIRECT_PREFIX + INDEX_VIEW;
    }

    // Tìm sản phẩm theo tên.
    @GetMapping(SEARCH_VIEW)
    public String search(String ten) {
        var product = sanPhamService.getSanPham(ten);
        // Kiểm tra sản phẩm còn tồn tại và còn hàng.
        if (product == null || product.getTonKho() < 1) {
            return REDIRECT_PREFIX + BLANK_VIEW;
        } else {
            return REDIRECT_PREFIX + DETAIL_VIEW + FIND_VIEW + "?id=" + product.getId();
        }
    }

    // Hiển thị trang trống khi không tìm thấy dữ liệu phù hợp.
    @GetMapping(BLANK_VIEW)
    public ModelAndView blank() {
        var mav = new ModelAndView(BLANK_TEMP);
        var client = authenticationUtil.getAccount();
        mav.addObject(TITLE_PARAM, CHI_TIET);
        mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
        mav.addObject(LOGIN_PARAM, client != null);
        mav.addObject(TOP_DISCOUNTS_PARAM, applicationUtil.limit(sanPhamService.getDsSanPhamDescendingDiscount(), 3));
        mav.addObject(TOP_NEWS_PARAM, applicationUtil.limit(sanPhamService.getDsSanPhamNewest(), 3));
        mav.addObject(TOP_SALES_PARAM, applicationUtil.limit(sanPhamService.getDsSanPhamTopSale(), 4));
        _isMsgShow = applicationUtil.showMessageBox(mav);
        return mav;
    }
}
