package com.nohit.ecommerce_project.util;

import lombok.*;

import org.springframework.stereotype.*;

import static org.springframework.util.StringUtils.*;

// Ví dụ: " ai?đứng như bóng dừa!tóc(dài )bay( trong gió).  có phải,người còn đó:là con gái của Bến Tre thời 4.0. "
/**
 * Tiện ích chuẩn hóa đoạn mô tả sản phẩm và nội dung văn bản tiếng Việt.
 */
@Component
@RequiredArgsConstructor
public class TextUtil {
    private final StringUtil stringUtil;
    private final NumberUtil numberUtil;

    // Thêm khoảng trắng sau ký tự khi cần.
    public String charWithSpace(String s, String character) {
        // Chỉ xử lý khi chuỗi có nội dung.
        if (hasText(s)) {
            var index = s.indexOf(character);
            // Đã tìm thấy ký tự cần chuẩn hóa.
            if (index != -1) {
                var iStart = index + 1;
                // Không thêm khoảng trắng khi ký tự kế tiếp là số.
                if (iStart < s.length() && !numberUtil.isNumeric(s.substring(iStart, iStart + 1))) {
                    s = s.substring(0, index) + character + " " + s.substring(index + 1);
                }
            }
        }
        return s;
    }

    // Chuẩn hóa văn bản mô tả về dạng dễ đọc.
    public String parseToLegalText(String s) {
        return capitalize(stringUtil.replaceMultiBySingleWhitespace(stringUtil.removeWhiteSpaceBeginAndEnd(
                charWithSpace(charWithSpace(charWithSpace(charWithSpace(charWithSpace(charWithSpace(stringUtil
                        .capitalizeAfterChar(stringUtil.capitalizeAfterChar(stringUtil.capitalizeAfterChar(stringUtil
                                .removeWhiteSpaceBeforeAndAfterChar(stringUtil.removeWhiteSpaceBeforeAndAfterChar(
                                        stringUtil.removeWhiteSpaceBeforeAndAfterChar(
                                                stringUtil.removeWhiteSpaceBeforeAndAfterChar(
                                                        stringUtil.removeWhiteSpaceBeforeAndAfterChar(
                                                                stringUtil.removeWhiteSpaceBeforeAndAfterChar(
                                                                        stringUtil.replaceWordFoundByKeyword(stringUtil
                                                                                .replaceWordFoundByKeyword(stringUtil
                                                                                        .replaceWordFoundByKeyword(
                                                                                                stringUtil
                                                                                                        .replaceWordFoundByKeyword(
                                                                                                                stringUtil
                                                                                                                        .replaceWordFoundByKeyword(
                                                                                                                                stringUtil
                                                                                                                                        .replaceWordFoundByKeyword(
                                                                                                                                                stringUtil
                                                                                                                                                        .replaceWordFoundByKeyword(
                                                                                                                                                                stringUtil
                                                                                                                                                                        .replaceWordFoundByKeyword(
                                                                                                                                                                                stringUtil
                                                                                                                                                                                        .removeWhiteSpaceBeforeAndAfterChar(
                                                                                                                                                                                                stringUtil
                                                                                                                                                                                                        .removeWhiteSpaceBeforeAndAfterChar(
                                                                                                                                                                                                                stringUtil
                                                                                                                                                                                                                        .removeWhiteSpaceBeforeAndAfterChar(
                                                                                                                                                                                                                                stringUtil
                                                                                                                                                                                                                                        .removeWhiteSpaceBeforeAndAfterChar(
                                                                                                                                                                                                                                                stringUtil
                                                                                                                                                                                                                                                        .removeWhiteSpaceBeforeAndAfterChar(
                                                                                                                                                                                                                                                                stringUtil
                                                                                                                                                                                                                                                                        .removeWhiteSpaceBeforeAndAfterChar(
                                                                                                                                                                                                                                                                                stringUtil
                                                                                                                                                                                                                                                                                        .removeWhiteSpaceBeforeAndAfterChar(
                                                                                                                                                                                                                                                                                                stringUtil
                                                                                                                                                                                                                                                                                                        .removeWhiteSpaceBeforeAndAfterChar(
                                                                                                                                                                                                                                                                                                                stringUtil
                                                                                                                                                                                                                                                                                                                        .removeWhiteSpaceBeginAndEnd(
                                                                                                                                                                                                                                                                                                                                s),
                                                                                                                                                                                                                                                                                                                "\\("),
                                                                                                                                                                                                                                                                                                "\\)"),
                                                                                                                                                                                                                                                                                "\\["),
                                                                                                                                                                                                                                                                "\\]"),
                                                                                                                                                                                                                                                "\\{"),
                                                                                                                                                                                                                                "\\}"),
                                                                                                                                                                                                                "<"),
                                                                                                                                                                                                ">"),
                                                                                                                                                                                "\\(",
                                                                                                                                                                                " \\("),
                                                                                                                                                                "\\)",
                                                                                                                                                                "\\) "),
                                                                                                                                                "\\[",
                                                                                                                                                " \\["),
                                                                                                                                "\\]",
                                                                                                                                "\\] "),
                                                                                                                "\\{",
                                                                                                                " \\{"),
                                                                                                "\\}", "\\} "),
                                                                                        "<", " <"),
                                                                                ">", "> "),
                                                                        "\\."),
                                                                ","),
                                                        ";"),
                                                ":"),
                                        "\\?"), "!"),
                                "."), "?"), "!"),
                        "."), ","), ";"), ":"), "?"), "!"))));
    }
}
