package com.nohit.ecommerce_project.config;

import org.springframework.boot.web.server.*;
import org.springframework.boot.web.servlet.server.*;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;

import static com.nohit.ecommerce_project.constant.AttributeConstant.*;
import static com.nohit.ecommerce_project.constant.ViewConstant.*;
import static org.springframework.http.HttpStatus.*;

/**
 * Cấu hình MVC bổ sung view controller và cookie session bảo mật hơn.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(NOT_FOUND_VIEW).setViewName(FORWARD_PREFIX + INDEX_VIEW);
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> containerCustomizer() {
        return container -> {
            container.addErrorPages(new ErrorPage(NOT_FOUND, NOT_FOUND_VIEW));
        };
    }
}
