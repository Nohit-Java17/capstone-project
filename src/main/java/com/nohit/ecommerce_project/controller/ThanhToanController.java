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
import static com.nohit.ecommerce_project.constant.ApplicationConstant.Payment.*;
import static com.nohit.ecommerce_project.constant.AttributeConstant.*;
import static com.nohit.ecommerce_project.constant.TemplateConstant.*;
import static com.nohit.ecommerce_project.constant.ViewConstant.*;
import static org.springframework.util.StringUtils.*;

/**
 * Controller xử lý thanh toán, tạo đơn hàng và cập nhật tồn kho sau khi đặt hàng.
 */
@Controller
@RequestMapping(CHECKOUT_VIEW)
@RequiredArgsConstructor
public class ThanhToanController {
    private final NguoiNhanService nguoiNhanService;
    private final SanPhamService sanPhamService;
    private final GioHangService gioHangService;
    private final ChiTietGioHangService chiTietGioHangService;
    private final DonHangService donHangService;
    private final ChiTietDonHangService chiTietDonHangService;
    private final TinhThanhService tinhThanhService;
    private final AuthenticationUtil authenticationUtil;
    private final ApplicationUtil applicationUtil;

    // Hiển thị trang thanh toán.
    @GetMapping("")
    public ModelAndView checkout() {
        var client = authenticationUtil.getAccount();
        // Kiểm tra phiên đăng nhập hiện tại còn hợp lệ.
        if (client == null) {
            return new ModelAndView(LOGIN_TEMP);
        } else {
            var mav = new ModelAndView(CHECKOUT_TEMP);
            var cart = applicationUtil.getOrDefaultGioHang(client);
            var provinceCart = cart.getIdTinhThanh();
            var differentAddress = provinceCart != client.getIdTinhThanh();
            mav.addObject(TITLE_PARAM, THANH_TOAN);
            mav.addObject(CART_PARAM, cart);
            mav.addObject(LOGIN_PARAM, client != null);
            mav.addObject(CLIENT_PARAM, client);
            mav.addObject(TOP_DISCOUNTS_PARAM, applicationUtil.limit(sanPhamService.getDsSanPhamDescendingDiscount(), 3));
            mav.addObject(TOP_NEWS_PARAM, applicationUtil.limit(sanPhamService.getDsSanPhamNewest(), 3));
            mav.addObject(PROVINCES_PARAM, tinhThanhService.getDsTinhThanh());
            mav.addObject(DEFAULT_PROVINCE_PARAM, differentAddress ? provinceCart : DEFAULT_PROVINCE);
            mav.addObject(DEFAULT_WARD_PARAM, differentAddress ? cart.getHuyenQuan() : "");
            _isMsgShow = applicationUtil.showMessageBox(mav);
            return mav;
        }
    }

    // Xử lý đặt hàng.
    @PostMapping("")
    public String checkout(NguoiNhan nguoiNhan, boolean differentAddress, String phuongThucThanhToan) {
        var client = authenticationUtil.getAccount();
        // Kiểm tra phiên đăng nhập hiện tại còn hợp lệ.
        if (client == null) {
            return REDIRECT_PREFIX + LOGIN_VIEW;
        } else {
            var cart = applicationUtil.getOrDefaultGioHang(client);
            _isMsgShow = true;
            // Kiểm tra trạng thái giỏ hàng.
            if (cart.getTongSoLuong() <= 0) {
                _msg = "Không thể thanh toán giỏ hàng trống!";
                return REDIRECT_PREFIX + CHECKOUT_VIEW;
            } else {
                var creditCard = client.getCreditCard();
                var name = client.getHoTen();
                var phone = client.getSoDienThoai();
                var address = client.getDiaChi();
                var ward = client.getXaPhuong();
                var district = client.getHuyenQuan();
                // Kiểm tra thông tin thẻ khi khách chọn thanh toán bằng thẻ.
                if (CARD.equals(phuongThucThanhToan)
                        && (creditCard == null || !hasText(creditCard.getNameOnCard()) || !hasText(creditCard.getCardNumber())
                                || !hasText(creditCard.getExpiration()) || !hasText(creditCard.getSecurityCode()))) {
                    _msg = "Bạn chưa có thông tin thẻ tín dụng trong tài khoản!";
                    return REDIRECT_PREFIX + PROFILE_VIEW;
                } else if (!hasText(name) || !hasText(phone) || !hasText(address) || !hasText(ward)
                        || !hasText(district)) {
                    _msg = "Bạn chưa có thông tin cá nhân đầy đủ để thanh toán!";
                    return REDIRECT_PREFIX + PROFILE_VIEW;
                } else {
                    // Dùng địa chỉ hồ sơ nếu khách không nhập địa chỉ nhận khác.
                    if (!differentAddress) {
                        nguoiNhan.setHoTen(name);
                        nguoiNhan.setSoDienThoai(phone);
                        nguoiNhan.setDiaChi(address);
                        nguoiNhan.setXaPhuong(ward);
                        nguoiNhan.setHuyenQuan(district);
                        nguoiNhan.setIdTinhThanh(client.getIdTinhThanh());
                    }
                    nguoiNhan = nguoiNhanService.saveNguoiNhan(nguoiNhan);
                    var idReceiver = nguoiNhan.getId();
                    var order = new DonHang();
                    order.setNgayDat(new Date());
                    order.setTongGioHang(cart.getTongGioHang());
                    var tinhThanhGiaoHang = cart.getTinhThanh() != null ? cart.getTinhThanh() : client.getTinhThanh();
                    order.setChiPhiVanChuyen(tinhThanhGiaoHang == null ? 0 : tinhThanhGiaoHang.getChiPhiVanChuyen());
                    order.setGiamGia(cart.getGiamGia());
                    order.setPhuongThucThanhToan(phuongThucThanhToan);
                    order.setTrangThai(DEFAULT_STATUS);
                    order.setIdKhachHang(client.getId());
                    order.setIdNguoiNhan(idReceiver);
                    order = donHangService.saveDonHang(order);
                    var id = order.getId();
                    // Chuyển từng dòng giỏ hàng sang chi tiết đơn hàng.
                    for (var item : cart.getDsChiTietGioHang()) {
                        var product = item.getSanPham();
                        var productsCount = item.getSoLuongSanPham();
                        if (product == null || product.getTonKho() < productsCount) {
                            nguoiNhanService.deleteNguoiNhan(idReceiver);
                            donHangService.deleteDonHang(id);
                            _msg = "Không còn đủ sản phẩm để thanh toán!";
                            return REDIRECT_PREFIX + CART_VIEW;
                        }
                        var idProduct = product.getId();
                        var inventory = product.getTonKho();
                        var orderDetail = new ChiTietDonHang();
                        orderDetail.setSoLuongSanPham(productsCount);
                        orderDetail.setGiaBanSanPham(item.getGiaBanSanPham());
                        orderDetail.setTongTienSanPham(item.getTongTienSanPham());
                        orderDetail.setId(new ChiTietDonHangId(id, idProduct));
                        orderDetail = chiTietDonHangService.saveChiTietDonHang(orderDetail);
                        product.setTonKho(inventory - productsCount);
                        sanPhamService.updateTonKho(idProduct, product.getTonKho());
                        chiTietGioHangService.deleteChiTietGioHang(new ChiTietGioHangId(cart.getId(), idProduct));
                    }
                    cart = gioHangService.saveGioHang(gioHangService.createGioHang(client));
                    _msg = "Đơn hàng đã được đặt thành công!";
                    return REDIRECT_PREFIX + HISTORY_VIEW;
                }
            }
        }
    }
}
