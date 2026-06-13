package com.nohit.ecommerce_project;

import org.springframework.boot.builder.*;
import org.springframework.boot.web.servlet.support.*;

/**
 * Cấu hình khởi tạo ứng dụng khi đóng gói WAR và chạy trên servlet container ngoài.
 */
public class ServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(EcommerceProjectApplication.class);
    }
}
