package com.nohit.ecommerce_project.controller;

import lombok.*;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.nohit.ecommerce_project.util.*;

import static com.nohit.ecommerce_project.common.Bean.*;
import static com.nohit.ecommerce_project.constant.ApplicationConstant.Menu.*;
import static com.nohit.ecommerce_project.constant.AttributeConstant.*;
import static com.nohit.ecommerce_project.constant.TemplateConstant.*;
import static com.nohit.ecommerce_project.constant.ViewConstant.*;

/**
 * Controller hiển thị lịch sử mua hàng của khách hàng.
 */
@Controller
@RequestMapping(HISTORY_VIEW)
@RequiredArgsConstructor
public class LichSuController {
    private final AuthenticationUtil authenticationUtil;
    private final ApplicationUtil applicationUtil;

    // Hiển thị lịch sử mua hàng.
    @GetMapping("")
    public ModelAndView history() {
        var client = authenticationUtil.getAccount();
        // Kiểm tra phiên đăng nhập hiện tại còn hợp lệ.
        if (client == null) {
            return new ModelAndView(REDIRECT_PREFIX + LOGOUT_VIEW);
        } else {
            var mav = new ModelAndView(HISTORY_TEMP);
            mav.addObject(TITLE_PARAM, LICH_SU);
            mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
            mav.addObject(LOGIN_PARAM, client != null);
            mav.addObject(ORDERS_PARAM, client.getDsDonHang());
            _isMsgShow = applicationUtil.showMessageBox(mav);
            return mav;
        }
    }
}
