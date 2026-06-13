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
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Controller quản lý giỏ hàng, mã giảm giá, phí vận chuyển và số lượng sản phẩm.
 */
@Controller
@RequestMapping(CART_VIEW)
@RequiredArgsConstructor
public class GioHangController {
    private final SanPhamService sanPhamService;
    private final GioHangService gioHangService;
    private final ChiTietGioHangService chiTietGioHangService;
    private final TinhThanhService tinhThanhService;
    private final AuthenticationUtil authenticationUtil;
    private final ApplicationUtil applicationUtil;

    // Hiển thị giỏ hàng.
    @GetMapping("")
    public ModelAndView cart() {
        var client = authenticationUtil.getAccount();
        // Kiểm tra phiên đăng nhập hiện tại còn hợp lệ.
        if (client == null) {
            return new ModelAndView(LOGIN_TEMP);
        } else {
            var mav = new ModelAndView(CART_TEMP);
            var cart = applicationUtil.getOrDefaultGioHang(client);
            var productsCount = cart.getTongSoLuong();
            var inventories = new ArrayList<>();
            // Lấy tồn kho của từng sản phẩm trong giỏ.
            for (var item : cart.getDsChiTietGioHang()) {
                inventories.add(item.getSanPham().getTonKho());
            }
            mav.addObject(TITLE_PARAM, GIO_HANG);
            mav.addObject(CART_PARAM, cart);
            mav.addObject(LOGIN_PARAM, client != null);
            mav.addObject(TOP_DISCOUNTS_PARAM, applicationUtil.limit(sanPhamService.getDsSanPhamDescendingDiscount(), 3));
            mav.addObject(TOP_NEWS_PARAM, applicationUtil.limit(sanPhamService.getDsSanPhamNewest(), 3));
            mav.addObject(TOP_SALES_PARAM, applicationUtil.limit(sanPhamService.getDsSanPhamTopSale(), 2));
            mav.addObject(PROVINCES_PARAM, tinhThanhService.getDsTinhThanh());
            mav.addObject(COUPON_PARAM, productsCount < 1 ? 0 : cart.getGiamGia());
            mav.addObject(SHIPFEE_PARAM, productsCount < 1 ? 0 : cart.getTinhThanh().getChiPhiVanChuyen());
            mav.addObject(LIMITS_PARAM, inventories);
            _isMsgShow = applicationUtil.showMessageBox(mav);
            return mav;
        }
    }

    // Thêm sản phẩm vào giỏ hàng.
    @RequestMapping(value = ADD_VIEW + PRODUCT_VIEW, method = { GET, POST })
    public String cartAddProduct(int id, int soLuongSanPham) {
        var client = authenticationUtil.getAccount();
        // Kiểm tra phiên đăng nhập hiện tại còn hợp lệ.
        if (client == null) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            var cart = applicationUtil.getOrDefaultGioHang(client);
            var product = sanPhamService.getSanPham(id);
            _isMsgShow = true;
            // Kiểm tra sản phẩm còn tồn tại và còn hàng.
            if (product == null || product.getTonKho() < 1) {
                _msg = "Sản phẩm không còn tồn tại!";
                return REDIRECT_PREFIX + PRODUCT_VIEW;
            } else {
                var idCartDetail = new ChiTietGioHangId(client.getId(), id);
                // Kiểm tra sản phẩm đã có trong giỏ hàng.
                if (chiTietGioHangService.getChiTietGioHang(idCartDetail) != null) {
                    _msg = "Sản phẩm đã tồn tại trong giỏ hàng!";
                } else {
                    var cartDetail = new ChiTietGioHang();
                    cartDetail.setId(idCartDetail);
                    cartDetail.setSoLuongSanPham(soLuongSanPham);
                    cartDetail.setGiaBanSanPham(product.getGiaGoc() - product.getKhuyenMai());
                    cartDetail = chiTietGioHangService.saveChiTietGioHang(cartDetail);
                    cart.setTongSoLuong(cart.getTongSoLuong() + soLuongSanPham);
                    cart.setTongGioHang(cart.getTongGioHang() + cartDetail.getTongTienSanPham());
                    cart = gioHangService.saveGioHang(cart);
                    _msg = "Thêm sản phẩm vào giỏ hàng thành công!";
                }
                return REDIRECT_PREFIX + CART_VIEW;
            }
        }
    }

    // Cập nhật mã giảm giá.
    @RequestMapping(value = EDIT_VIEW + COUPON_VIEW, method = { GET, PUT })
    public String cartEditCoupon(String maGiamGia) {
        var client = authenticationUtil.getAccount();
        // Kiểm tra phiên đăng nhập hiện tại còn hợp lệ.
        if (client == null) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            var cart = applicationUtil.getOrDefaultGioHang(client);
            var coupon = maGiamGia == null ? null : COUPON_MAP.get(maGiamGia.trim().toLowerCase());
            _isMsgShow = true;
            // Kiểm tra mã giảm giá.
            if (coupon == null) {
                _msg = "Mã giảm giá chưa chính xác!";
            } else {
                cart.setGiamGia(coupon);
                cart = gioHangService.saveGioHang(cart);
                _msg = "Áp dụng giảm giá thành công!";
            }
            return REDIRECT_PREFIX + CART_VIEW;
        }
    }

    // Cập nhật phí vận chuyển.
    @RequestMapping(value = EDIT_VIEW + SHIP_FEE_VIEW, method = { GET, PUT })
    public String cartEditShipFee(int id, String huyenQuan) {
        var client = authenticationUtil.getAccount();
        // Kiểm tra phiên đăng nhập hiện tại còn hợp lệ.
        if (client == null) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            var cart = applicationUtil.getOrDefaultGioHang(client);
            cart.setHuyenQuan(huyenQuan);
            cart.setIdTinhThanh(id);
            cart = gioHangService.saveGioHang(cart);
            _isMsgShow = true;
            _msg = "Cập nhật địa chỉ giao hàng cho giỏ hàng thành công!";
            return REDIRECT_PREFIX + CART_VIEW;
        }
    }

    // Cập nhật giỏ hàng.
    @RequestMapping(value = SAVE_VIEW, method = { GET, PUT })
    public String cartSave(int[] soSanPham) {
        var client = authenticationUtil.getAccount();
        // Kiểm tra phiên đăng nhập hiện tại còn hợp lệ.
        if (client == null) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            var cart = applicationUtil.getOrDefaultGioHang(client);
            if (soSanPham == null || soSanPham.length < cart.getDsChiTietGioHang().size()) {
                _isMsgShow = true;
                _msg = "Dữ liệu cập nhật giỏ hàng chưa hợp lệ!";
                return REDIRECT_PREFIX + CART_VIEW;
            }
            var productsCount = 0;
            var cartTotal = 0;
            var index = 0;
            // Cập nhật từng dòng chi tiết giỏ hàng.
            for (var item : cart.getDsChiTietGioHang()) {
                item.setSoLuongSanPham(soSanPham[index]);
                item = chiTietGioHangService.saveChiTietGioHang(item);
                productsCount += soSanPham[index];
                cartTotal += item.getTongTienSanPham();
                index++;
            }
            cart.setTongSoLuong(productsCount);
            cart.setTongGioHang(cartTotal);
            cart = gioHangService.saveGioHang(cart);
            _isMsgShow = true;
            _msg = "Cập nhật giỏ hàng thành công!";
            return REDIRECT_PREFIX + CART_VIEW;
        }
    }

    // Xóa dòng chi tiết giỏ hàng.
    @RequestMapping(value = DELETE_VIEW + PRODUCT_VIEW, method = { GET, DELETE })
    public String cartDeleteProduct(int id) {
        var client = authenticationUtil.getAccount();
        // Kiểm tra phiên đăng nhập hiện tại còn hợp lệ.
        if (client == null) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            var cart = applicationUtil.getOrDefaultGioHang(client);
            var idCartDetail = new ChiTietGioHangId(client.getId(), id);
            var cartDetail = chiTietGioHangService.getChiTietGioHang(idCartDetail);
            if (cartDetail == null) {
                _isMsgShow = true;
                _msg = "Sản phẩm không còn trong giỏ hàng!";
                return REDIRECT_PREFIX + CART_VIEW;
            }
            cart.setTongGioHang(cart.getTongGioHang() - cartDetail.getTongTienSanPham());
            cart.setTongSoLuong(cart.getTongSoLuong() - cartDetail.getSoLuongSanPham());
            cart = gioHangService.saveGioHang(cart);
            chiTietGioHangService.deleteChiTietGioHang(idCartDetail);
            _isMsgShow = true;
            _msg = "Sản phẩm đã được xóa khỏi giỏ hàng thành công!";
            return REDIRECT_PREFIX + CART_VIEW;
        }
    }
}
