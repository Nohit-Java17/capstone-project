package com.nohit.ecommerce_project.util;

import lombok.*;

import org.springframework.security.authentication.*;
import org.springframework.stereotype.*;

import com.nohit.ecommerce_project.model.*;
import com.nohit.ecommerce_project.service.*;

import static org.springframework.security.core.context.SecurityContextHolder.*;

/**
 * Tiện ích lấy tài khoản khách hàng hiện tại từ Spring SecurityContext.
 */
@Component
@RequiredArgsConstructor
public class AuthenticationUtil {
    private final KhachHangService khachHangService;

    // Lấy tài khoản hiện tại từ SecurityContextHolder.
    public KhachHang getAccount() {
        var authentication = getContext().getAuthentication();
        return authentication == null || authentication instanceof AnonymousAuthenticationToken ? null
                : khachHangService.getKhachHang(authentication.getName());
    }
}
