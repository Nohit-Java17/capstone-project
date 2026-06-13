package com.nohit.ecommerce_project.controller;

import lombok.*;

import java.util.*;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.nohit.ecommerce_project.model.*;
import com.nohit.ecommerce_project.service.*;
import com.nohit.ecommerce_project.util.*;

import static com.nohit.ecommerce_project.common.Bean.*;
import static com.nohit.ecommerce_project.constant.ApplicationConstant.*;
import static com.nohit.ecommerce_project.constant.ApplicationConstant.Menu.*;
import static com.nohit.ecommerce_project.constant.AttributeConstant.*;
import static com.nohit.ecommerce_project.constant.TemplateConstant.*;
import static com.nohit.ecommerce_project.constant.ViewConstant.*;

/**
 * Controller hiển thị, sắp xếp và phân trang danh sách sản phẩm.
 */
@Controller
@RequestMapping(PRODUCT_VIEW)
@RequiredArgsConstructor
public class SanPhamController {
    private final AuthenticationUtil authenticationUtil;
    private final SanPhamService sanPhamService;
    private final ApplicationUtil applicationUtil;

    // Hiển thị trang sản phẩm.
    @GetMapping("")
    public ModelAndView product() {
        var mav = new ModelAndView(PRODUCT_TEMP);
        var client = authenticationUtil.getAccount();
        var products = sanPhamService.getDsSanPhamTonKho();
        var maxSize = applicationUtil.maxPage(products, DEFAULT_SIZE_PAGE);
        mav.addObject(TITLE_PARAM, SAN_PHAM);
        mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
        mav.addObject(LOGIN_PARAM, client != null);
        mav.addObject(PRODUCTS_PARAM, applicationUtil.limit(products, DEFAULT_SIZE_PAGE));
        mav.addObject(RADIO_CHECK_PARAM, DEFAULT_PRODUCT);
        mav.addObject(MAX_SIZE_PARAM, maxSize);
        mav.addObject(VIEW_PARAM, PAGE_VIEW + "?page=");
        mav.addObject(PREVIOUS_PARAM, PAGE_VIEW + "?page=" + 1);
        mav.addObject(NEXT_PARAM, PAGE_VIEW + "?page=" + (2 > maxSize ? maxSize : 2));
        _isMsgShow = applicationUtil.showMessageBox(mav);
        return mav;
    }

    // Hiển thị sản phẩm theo trang.
    @GetMapping(PAGE_VIEW)
    public ModelAndView product(int page) {
        var mav = new ModelAndView(PRODUCT_TEMP);
        var client = authenticationUtil.getAccount();
        var products = sanPhamService.getDsSanPhamTonKho();
        var maxSize = applicationUtil.maxPage(products, DEFAULT_SIZE_PAGE);
        var previous = page - 1;
        var next = page + 1;
        mav.addObject(TITLE_PARAM, SAN_PHAM);
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

    // Hiển thị sản phẩm theo kiểu sắp xếp.
    @GetMapping(SORT_VIEW)
    public ModelAndView productSort(String sort) {
        var mav = new ModelAndView(PRODUCT_TEMP);
        var client = authenticationUtil.getAccount();
        var products = sortProducts(sort);
        var maxSize = applicationUtil.maxPage(products, DEFAULT_SIZE_PAGE);
        mav.addObject(TITLE_PARAM, SAN_PHAM);
        mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
        mav.addObject(LOGIN_PARAM, client != null);
        mav.addObject(PRODUCTS_PARAM, applicationUtil.limit(products, DEFAULT_SIZE_PAGE));
        mav.addObject(RADIO_CHECK_PARAM, PRODUCTS_MAP.get(sort));
        mav.addObject(MAX_SIZE_PARAM, maxSize);
        mav.addObject(VIEW_PARAM, SORT_VIEW + PAGE_VIEW + "?sort=" + sort + "&page=");
        mav.addObject(PREVIOUS_PARAM, SORT_VIEW + PAGE_VIEW + "?sort=" + sort + "&page=" + 1);
        mav.addObject(NEXT_PARAM, SORT_VIEW + PAGE_VIEW + "?sort=" + sort + "&page=" + (2 > maxSize ? maxSize : 2));
        _isMsgShow = applicationUtil.showMessageBox(mav);
        return mav;
    }

    // Hiển thị sản phẩm theo kiểu sắp xếp.
    @GetMapping(SORT_VIEW + PAGE_VIEW)
    public ModelAndView productSort(String sort, int page) {
        var mav = new ModelAndView(PRODUCT_TEMP);
        var client = authenticationUtil.getAccount();
        var products = sortProducts(sort);
        var maxSize = applicationUtil.maxPage(products, DEFAULT_SIZE_PAGE);
        var previous = page - 1;
        var next = page + 1;
        mav.addObject(TITLE_PARAM, SAN_PHAM);
        mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
        mav.addObject(LOGIN_PARAM, client != null);
        mav.addObject(PRODUCTS_PARAM, applicationUtil.page(products, page, DEFAULT_SIZE_PAGE));
        mav.addObject(RADIO_CHECK_PARAM, PRODUCTS_MAP.get(sort));
        mav.addObject(MAX_SIZE_PARAM, maxSize);
        mav.addObject(VIEW_PARAM, SORT_VIEW + PAGE_VIEW + "?sort=" + sort + "&page=");
        mav.addObject(PREVIOUS_PARAM,
                SORT_VIEW + PAGE_VIEW + "?sort=" + sort + "&page=" + (previous < 1 ? 1 : previous));
        mav.addObject(NEXT_PARAM,
                SORT_VIEW + PAGE_VIEW + "?sort=" + sort + "&page=" + (next > maxSize ? maxSize : next));
        _isMsgShow = applicationUtil.showMessageBox(mav);
        return mav;
    }

    // Lấy danh sách sản phẩm theo kiểu sắp xếp.
    private List<SanPham> sortProducts(String sort) {
        switch (sort) {
            case "topSale": {
                return sanPhamService.getDsSanPhamTopSale();
            }
            case "newest": {
                return sanPhamService.getDsSanPhamNewest();
            }
            case "discount": {
                return sanPhamService.getDsSanPhamDescendingDiscount();
            }
            case "ascendingPrice": {
                return sanPhamService.getDsSanPhamAscendingPrice();
            }
            case "descendingPrice": {
                return sanPhamService.getDsSanPhamDescendingPrice();
            }
            default: {
                return sanPhamService.getDsSanPhamTonKho();
            }
        }
    }
}
