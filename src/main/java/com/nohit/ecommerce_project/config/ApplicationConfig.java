package com.nohit.ecommerce_project.config;

import org.springframework.context.annotation.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.savedrequest.*;

/**
 * Khai báo bean dùng chung cho ứng dụng, gồm PasswordEncoder và request cache không tự tạo session.
 */
@Configuration
public class ApplicationConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpSessionRequestCache getHttpSessionRequestCache() {
        var httpSessionRequestCache = new HttpSessionRequestCache();
        httpSessionRequestCache.setCreateSessionAllowed(false);
        return httpSessionRequestCache;
    }
}
