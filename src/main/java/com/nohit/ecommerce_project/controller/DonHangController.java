package com.nohit.ecommerce_project.controller;

import lombok.*;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.nohit.ecommerce_project.service.*;
import com.nohit.ecommerce_project.util.*;

import static com.nohit.ecommerce_project.common.Bean.*;
import static com.nohit.ecommerce_project.constant.ApplicationConstant.Menu.*;
import static com.nohit.ecommerce_project.constant.AttributeConstant.*;
import static com.nohit.ecommerce_project.constant.TemplateConstant.*;
import static com.nohit.ecommerce_project.constant.ViewConstant.*;

/**
 * Controller hiển thị danh sách đơn hàng của khách hàng đang đăng nhập.
 */
@Controller
@RequestMapping(ORDER_VIEW)
@RequiredArgsConstructor
public class DonHangController {
    private final DonHangService donHangService;
    private final AuthenticationUtil authenticationUtil;
    private final ApplicationUtil applicationUtil;

    @GetMapping("")
    public String order() {
        _isMsgShow = true;
        _msg = "Cần chọn 1 đơn hàng để xem!";
        return REDIRECT_PREFIX + HISTORY_VIEW;
    }

    // Hiển thị danh sách đơn hàng.
    @GetMapping(FIND_VIEW)
    public ModelAndView orderFind(int id) {
        var client = authenticationUtil.getAccount();
        // Kiểm tra phiên đăng nhập hiện tại còn hợp lệ.
        if (client == null) {
            return new ModelAndView(REDIRECT_PREFIX + LOGOUT_VIEW);
        } else {
            var mav = new ModelAndView(ORDER_TEMP);
            mav.addObject(TITLE_PARAM, DON_HANG);
            mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
            mav.addObject(LOGIN_PARAM, client != null);
            mav.addObject(ORDER_PARAM, donHangService.getDonHang(id));
            _isMsgShow = applicationUtil.showMessageBox(mav);
            return mav;
        }
    }
}
