package com.nohit.ecommerce_project.controller;

import lombok.*;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.nohit.ecommerce_project.service.*;
import com.nohit.ecommerce_project.util.*;

import static com.nohit.ecommerce_project.common.Bean.*;
import static com.nohit.ecommerce_project.constant.ApplicationConstant.*;
import static com.nohit.ecommerce_project.constant.ApplicationConstant.Menu.*;
import static com.nohit.ecommerce_project.constant.AttributeConstant.*;
import static com.nohit.ecommerce_project.constant.TemplateConstant.*;
import static com.nohit.ecommerce_project.constant.ViewConstant.*;

/**
 * Controller hiển thị, lọc và phân trang sản phẩm theo phân loại.
 */
@Controller
@RequestMapping(CATEGORY_VIEW)
@RequiredArgsConstructor
public class PhanLoaiController {
    private final SanPhamService sanPhamService;
    private final AuthenticationUtil authenticationUtil;
    private final ApplicationUtil applicationUtil;

    // Hiển thị trang phân loại.
    @GetMapping("")
    public ModelAndView category() {
        var mav = new ModelAndView(CATEGORY_TEMP);
        var client = authenticationUtil.getAccount();
        var products = sanPhamService.getDsSanPhamTonKho();
        var maxSize = applicationUtil.maxPage(products, DEFAULT_SIZE_PAGE);
        mav.addObject(TITLE_PARAM, PHAN_LOAI);
        mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
        mav.addObject(LOGIN_PARAM, client != null);
        mav.addObject(PRODUCTS_PARAM, applicationUtil.limit(products, DEFAULT_SIZE_PAGE));
        mav.addObject(RADIO_CHECK_PARAM, DEFAULT_CATEGORY);
        mav.addObject(MAX_SIZE_PARAM, maxSize);
        mav.addObject(VIEW_PARAM, PAGE_VIEW + "?page=");
        mav.addObject(PREVIOUS_PARAM, PAGE_VIEW + "?page=" + 1);
        mav.addObject(NEXT_PARAM, PAGE_VIEW + "?page=" + (2 > maxSize ? maxSize : 2));
        _isMsgShow = applicationUtil.showMessageBox(mav);
        return mav;
    }

    // Hiển thị phân loại theo trang.
    @GetMapping(PAGE_VIEW)
    public ModelAndView category(int page) {
        var mav = new ModelAndView(CATEGORY_TEMP);
        var client = authenticationUtil.getAccount();
        var products = sanPhamService.getDsSanPhamTonKho();
        var maxSize = applicationUtil.maxPage(products, DEFAULT_SIZE_PAGE);
        var previous = page - 1;
        var next = page + 1;
        mav.addObject(TITLE_PARAM, PHAN_LOAI);
        mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
        mav.addObject(LOGIN_PARAM, client != null);
        mav.addObject(PRODUCTS_PARAM, applicationUtil.page(products, page, DEFAULT_SIZE_PAGE));
        mav.addObject(RADIO_CHECK_PARAM, DEFAULT_CATEGORY);
        mav.addObject(MAX_SIZE_PARAM, maxSize);
        mav.addObject(VIEW_PARAM, PAGE_VIEW + "?page=");
        mav.addObject(PREVIOUS_PARAM, PAGE_VIEW + "?page=" + (previous < 1 ? 1 : previous));
        mav.addObject(NEXT_PARAM, PAGE_VIEW + "?page=" + (next > maxSize ? maxSize : next));
        _isMsgShow = applicationUtil.showMessageBox(mav);
        return mav;
    }

    // Hiển thị sản phẩm theo bộ lọc phân loại.
    @GetMapping(FILTER_VIEW)
    public ModelAndView categoryFilter(String filter) {
        var mav = new ModelAndView(CATEGORY_TEMP);
        var client = authenticationUtil.getAccount();
        var products = sanPhamService.getDsSanPham(filter);
        var maxSize = applicationUtil.maxPage(products, DEFAULT_SIZE_PAGE);
        mav.addObject(TITLE_PARAM, PHAN_LOAI);
        mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
        mav.addObject(LOGIN_PARAM, client != null);
        mav.addObject(PRODUCTS_PARAM, applicationUtil.limit(products, DEFAULT_SIZE_PAGE));
        mav.addObject(RADIO_CHECK_PARAM, CATEGORIES_MAP.get(filter));
        mav.addObject(MAX_SIZE_PARAM, maxSize);
        mav.addObject(VIEW_PARAM, FILTER_VIEW + PAGE_VIEW + "?filter=" + filter + "&page=");
        mav.addObject(PREVIOUS_PARAM, FILTER_VIEW + PAGE_VIEW + "?filter=" + filter + "&page=" + 1);
        mav.addObject(NEXT_PARAM,
                FILTER_VIEW + PAGE_VIEW + "?filter=" + filter + "&page=" + (2 > maxSize ? maxSize : 2));
        _isMsgShow = applicationUtil.showMessageBox(mav);
        return mav;
    }

    // Hiển thị sản phẩm theo bộ lọc phân loại ở trang cụ thể.
    @GetMapping(FILTER_VIEW + PAGE_VIEW)
    public ModelAndView categoryFilter(String filter, int page) {
        var mav = new ModelAndView(CATEGORY_TEMP);
        var client = authenticationUtil.getAccount();
        var products = sanPhamService.getDsSanPham(filter);
        var maxSize = applicationUtil.maxPage(products, DEFAULT_SIZE_PAGE);
        var previous = page - 1;
        var next = page + 1;
        mav.addObject(TITLE_PARAM, PHAN_LOAI);
        mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
        mav.addObject(LOGIN_PARAM, client != null);
        mav.addObject(PRODUCTS_PARAM, applicationUtil.page(products, page, DEFAULT_SIZE_PAGE));
        mav.addObject(RADIO_CHECK_PARAM, CATEGORIES_MAP.get(filter));
        mav.addObject(MAX_SIZE_PARAM, maxSize);
        mav.addObject(VIEW_PARAM, FILTER_VIEW + PAGE_VIEW + "?filter=" + filter + "&page=");
        mav.addObject(PREVIOUS_PARAM,
                FILTER_VIEW + PAGE_VIEW + "?filter=" + filter + "&page=" + (previous < 1 ? 1 : previous));
        mav.addObject(NEXT_PARAM,
                FILTER_VIEW + PAGE_VIEW + "?filter=" + filter + "&page=" + (next > maxSize ? maxSize : next));
        _isMsgShow = applicationUtil.showMessageBox(mav);
        return mav;
    }
}
