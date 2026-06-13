package com.nohit.ecommerce_project.controller;

import lombok.*;

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
 * Controller xử lý màn hình đăng ký tài khoản khách hàng mới.
 */
@Controller
@RequestMapping(REGISTER_VIEW)
@RequiredArgsConstructor
public class DangKyController {
    private final KhachHangService khachHangService;
    private final GioHangService gioHangService;
    private final CreditCardService creditCardService;
    private final AuthenticationUtil authenticationUtil;
    private final ApplicationUtil applicationUtil;

    // Hiển thị trang đăng ký.
    @GetMapping("")
    public ModelAndView register() {
        // Kiểm tra người dùng đã đăng nhập.
        if (authenticationUtil.getAccount() != null) {
            return new ModelAndView(REDIRECT_PREFIX + INDEX_VIEW);
        } else {
            var mav = new ModelAndView(REGISTER_TEMP);
            mav.addObject(TITLE_PARAM, DANG_KY);
            _isMsgShow = applicationUtil.showMessageBox(mav);
            return mav;
        }
    }

    // Xử lý đăng ký tài khoản.
    @PostMapping("")
    public String register(KhachHang khachHang) {
        _isMsgShow = true;
        // Kiểm tra email đã tồn tại.
        if (khachHangService.getKhachHang(khachHang.getEmail()) != null) {
            _msg = "Email này đã được đăng ký!";
            return REDIRECT_PREFIX + REGISTER_VIEW;
        } else {
            khachHang.setIdTinhThanh(DEFAULT_PROVINCE);
            khachHang.setVaiTro(DEFAULT_ROLE);
            khachHang = khachHangService.saveKhachHang(khachHang);
            gioHangService.createGioHang(khachHang);
            creditCardService.createCreditCard(khachHang);
            _msg = "Tài khoản đã được đăng ký thành công!";
            return REDIRECT_PREFIX + LOGIN_VIEW;
        }
    }
}
