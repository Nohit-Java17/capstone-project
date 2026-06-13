package com.nohit.ecommerce_project.util;

import org.springframework.stereotype.*;
import org.thymeleaf.util.*;

import static org.springframework.util.StringUtils.*;

/**
 * Tiện ích chuẩn hóa chuỗi, tên, email và khoảng trắng cho dữ liệu đầu vào.
 */
@Component
public class StringUtil {
    // Kiểm tra chuỗi có chứa từ khóa.
    public boolean isWordContains(String s, String word) {
        return hasText(s) ? s.toUpperCase().contains(word.toUpperCase()) : false;
    }

    // Thay từ tìm thấy bằng từ khóa chuẩn hóa.
    public String replaceWordFoundByKeyword(String s, String word, String keyword) {
        return hasText(s) ? s.replaceAll("(?i)" + word + "", keyword) : s;
    }

    // Rút gọn nhiều khoảng trắng thành một khoảng trắng.
    public String replaceMultiBySingleWhitespace(String s) {
        return hasText(s) ? s.replaceAll("\\s+", " ") : s;
    }

    // Chuẩn hóa họ tên tiếng Việt.
    public String parseName(String s) {
        return hasText(s) ? titleCase(replaceMultiBySingleWhitespace(removeNumAndWhiteSpaceBeginAndEnd(s))) : s;
    }

    // Chuẩn hóa tên theo dạng quốc tế.
    public String parseNameInternational(String s) {
        return hasText(s)
                ? titleCase(
                        replaceMultiBySingleWhitespace(removeSpCharsBeginAndEnd(removeNumAndWhiteSpaceBeginAndEnd(s))))
                : s;
    }

    // Chuẩn hóa tên in trên thẻ.
    public String parseNameOnCard(String s) {
        return hasText(s)
                ? upperCase(
                        replaceMultiBySingleWhitespace(removeSpCharsBeginAndEnd(removeNumAndWhiteSpaceBeginAndEnd(s))))
                : s;
    }

    // Chuẩn hóa email.
    public String parseEmail(String s) {
        return hasText(s) ? lowerCase(removeSpCharsBeginAndEnd(removeNumAndWhiteSpaceEnd(s))) : s;
    }

    // Chuyển về chữ thường khi có nội dung.
    public String lowerCase(String s) {
        return hasText(s) ? s.toLowerCase() : s;
    }

    // Chuyển về chữ hoa khi có nội dung.
    public String upperCase(String s) {
        return hasText(s) ? s.toUpperCase() : s;
    }

    // Viết hoa ký tự đầu khi có nội dung.
    public String capitalizeCase(String s) {
        return hasText(s) ? capitalize(s) : s;
    }

    // Chuẩn hóa dạng câu.
    public String sentenceCase(String s) {
        return hasText(s) ? capitalize(s.toLowerCase()) : s;
    }

    // Chuẩn hóa dạng tiêu đề.
    public String titleCase(String s) {
        return hasText(s) ? StringUtils.capitalizeWords(s.toLowerCase()) : s;
    }

    // Viết hoa ký tự sau dấu phân tách.
    public String capitalizeAfterChar(String s, String character) {
        if (hasText(s)) {
            var index = s.indexOf(character);
            if (index != -1) {
                s = s.substring(0, index) + character + StringUtils.capitalize(s.substring(index + 1));
            }
        }
        return s;
    }

    // Xóa khoảng trắng đầu chuỗi.
    public String removeWhiteSpaceBegin(String s) {
        return hasText(s) ? s.replaceAll("^\\s+", "") : s;
    }

    // Xóa khoảng trắng cuối chuỗi.
    public String removeWhiteSpaceEnd(String s) {
        return hasText(s) ? s.replaceAll("\\s+$", "") : s;
    }

    // Xóa khoảng trắng ở cả đầu và cuối chuỗi.
    public String removeWhiteSpaceBeginAndEnd(String s) {
        return hasText(s) ? s.replaceAll("^\\s+|\\s+$", "") : s;
    }

    // Xóa ký tự đặc biệt ở đầu chuỗi.
    public String removeSpCharsBegin(String s) {
        return hasText(s) ? s.replaceAll("^[^a-zA-Z0-9]+", "") : s;
    }

    // Xóa ký tự đặc biệt ở cuối chuỗi.
    public String removeSpCharsEnd(String s) {
        return hasText(s) ? s.replaceAll("[^a-zA-Z0-9]+$", "") : s;
    }

    // Xóa ký tự đặc biệt ở cả đầu và cuối chuỗi.
    public String removeSpCharsBeginAndEnd(String s) {
        return hasText(s) ? s.replaceAll("^[^a-zA-Z0-9]+|[^a-zA-Z0-9]+$", "") : s;
    }

    // Xóa số và khoảng trắng ở đầu chuỗi.
    public String removeNumAndWhiteSpaceBegin(String s) {
        return hasText(s) ? s.replaceAll("^[0-9\\s]+", "") : s;
    }

    // Xóa số và khoảng trắng ở cuối chuỗi.
    public String removeNumAndWhiteSpaceEnd(String s) {
        return hasText(s) ? s.replaceAll("[0-9\\s]+$", "") : s;
    }

    // Xóa số và khoảng trắng ở cả đầu và cuối chuỗi.
    public String removeNumAndWhiteSpaceBeginAndEnd(String s) {
        return hasText(s) ? s.replaceAll("^[0-9\\s]+|[0-9\\s]+$", "") : s;
    }

    // Xóa khoảng trắng trước ký tự.
    public String removeWhiteSpaceBeforeChar(String s, String character) {
        return hasText(s) ? s.replaceAll("\\s+" + character, character) : s;
    }

    // Xóa khoảng trắng sau ký tự.
    public String removeWhiteSpaceAfterChar(String s, String character) {
        return hasText(s) ? s.replaceAll(character + "\\s+", character) : s;
    }

    // Xóa khoảng trắng trước và sau ký tự.
    public String removeWhiteSpaceBeforeAndAfterChar(String s, String character) {
        return hasText(s) ? s.replaceAll("\\s*" + character + "\\s*", character) : s;
    }
}
