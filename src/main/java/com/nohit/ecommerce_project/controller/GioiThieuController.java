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
 * Controller hiển thị trang giới thiệu nhóm và thông tin dự án.
 */
@Controller
@RequestMapping(ABOUT_VIEW)
@RequiredArgsConstructor
public class GioiThieuController {
    private final ApplicationUtil applicationUtil;
    private final AuthenticationUtil authenticationUtil;

    // Hiển thị trang giới thiệu.
    @GetMapping("")
    public ModelAndView about() {
        var mav = new ModelAndView(ABOUT_TEMP);
        var client = authenticationUtil.getAccount();
        mav.addObject(TITLE_PARAM, GIOI_THIEU);
        mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
        mav.addObject(LOGIN_PARAM, client != null);
        _isMsgShow = applicationUtil.showMessageBox(mav);
        return mav;
    }
}
