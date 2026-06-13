package com.nohit.ecommerce_project.controller;

import lombok.*;

import org.springframework.security.crypto.password.*;
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
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Controller quản lý hồ sơ, mật khẩu và thông tin thẻ của khách hàng.
 */
@Controller
@RequestMapping(PROFILE_VIEW)
@RequiredArgsConstructor
public class HoSoController {
    private final KhachHangService khachHangService;
    private final CreditCardService creditCardService;
    private final TinhThanhService tinhThanhService;
    private final AuthenticationUtil authenticationUtil;
    private final ApplicationUtil applicationUtil;
    private final PasswordEncoder passwordEncoder;

    // Hiển thị hồ sơ khách hàng.
    @GetMapping("")
    public ModelAndView profile() {
        var client = authenticationUtil.getAccount();
        // Kiểm tra phiên đăng nhập hiện tại còn hợp lệ.
        if (client == null) {
            return new ModelAndView(REDIRECT_PREFIX + LOGOUT_VIEW);
        } else {
            var mav = new ModelAndView(PROFILE_TEMP);
            mav.addObject(TITLE_PARAM, THONG_TIN);
            mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
            mav.addObject(LOGIN_PARAM, client != null);
            mav.addObject(CLIENT_PARAM, client);
            mav.addObject(CREDIT_CARD_PARAM, client.getCreditCard());
            mav.addObject(PROVINCES_PARAM, tinhThanhService.getDsTinhThanh());
            _isMsgShow = applicationUtil.showMessageBox(mav);
            return mav;
        }
    }

    // Cập nhật thông tin cá nhân.
    @RequestMapping(value = INFO_VIEW, method = { GET, PUT })
    public String profileInfo(KhachHang khachHang) {
        // Kiểm tra phiên đăng nhập hiện tại còn hợp lệ.
        if (authenticationUtil.getAccount() == null) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            khachHangService.saveKhachHangWithoutPassword(khachHang);
            _isMsgShow = true;
            _msg = "Thông tin cơ bản đã được cập nhật thành công!";
            return REDIRECT_PREFIX + PROFILE_VIEW;
        }
    }

    // Cập nhật mật khẩu.
    @PostMapping(PASSWORD_VIEW)
    public String profilePassword(String oldPassword, String newPassword, String rePassword) {
        var client = authenticationUtil.getAccount();
        // Kiểm tra phiên đăng nhập hiện tại còn hợp lệ.
        if (client == null) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            _isMsgShow = true;
            // Kiểm tra mật khẩu cũ và xác nhận mật khẩu mới.
            if (passwordEncoder.matches(oldPassword, client.getMatKhau()) && rePassword.equals(newPassword)) {
                khachHangService.updatePassword(client.getId(), newPassword);
                _msg = "Mật khẩu đã được cập nhật thành công!";
            } else {
                _msg = "Mật khẩu chưa chính xác!";
            }
            return REDIRECT_PREFIX + PROFILE_VIEW;
        }
    }

    // Cập nhật thông tin thẻ.
    @PostMapping(CARD_VIEW)
    public String profileCard(CreditCard creditCard) {
        // Kiểm tra phiên đăng nhập hiện tại còn hợp lệ.
        if (authenticationUtil.getAccount() == null) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            creditCardService.saveCreditCard(creditCard);
            _isMsgShow = true;
            _msg = "Thanh toán qua thẻ được cập nhật thành công!";
            return REDIRECT_PREFIX + PROFILE_VIEW;
        }
    }
}
