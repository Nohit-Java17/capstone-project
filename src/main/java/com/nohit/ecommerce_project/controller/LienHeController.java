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
 * Controller hiển thị trang liên hệ và lưu thư phản hồi từ khách hàng.
 */
@Controller
@RequestMapping(CONTACT_VIEW)
@RequiredArgsConstructor
public class LienHeController {
    private final ThuPhanHoiService thuPhanHoiService;
    private final ApplicationUtil applicationUtil;
    private final AuthenticationUtil authenticationUtil;

    // Hiển thị trang liên hệ.
    @GetMapping("")
    public ModelAndView contact() {
        var mav = new ModelAndView(CONTACT_TEMP);
        var client = authenticationUtil.getAccount();
        mav.addObject(TITLE_PARAM, LIEN_HE);
        mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
        mav.addObject(LOGIN_PARAM, client != null);
        mav.addObject(CLIENT_PARAM, client);
        _isMsgShow = applicationUtil.showMessageBox(mav);
        return mav;
    }

    // Lưu thông tin đăng ký/phản hồi.
    @PostMapping("")
    public String contact(ThuPhanHoi thuPhanHoi) {
        thuPhanHoiService.saveThuPhanHoi(thuPhanHoi);
        _isMsgShow = true;
        _msg = "Cảm ơn quý khách đã liên hệ với chúng tôi!";
        return REDIRECT_PREFIX + CONTACT_VIEW;
    }
}
