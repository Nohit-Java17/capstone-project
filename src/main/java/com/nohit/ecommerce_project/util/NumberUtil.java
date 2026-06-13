package com.nohit.ecommerce_project.util;

import org.springframework.stereotype.*;

import static java.lang.Integer.*;

/**
 * Tiện ích kiểm tra chuỗi có thể chuyển thành số nguyên hay không.
 */
@Component
public class NumberUtil {
    // Kiểm tra chuỗi có phải số nguyên hay không.
    public boolean isNumeric(String s) {
        try {
            parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
