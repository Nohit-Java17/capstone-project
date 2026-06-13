package com.nohit.ecommerce_project.util;

import lombok.*;

import org.springframework.stereotype.*;
import org.thymeleaf.util.*;

import static org.springframework.util.StringUtils.*;

// Ví dụ: "  6C Đường số 8,phường.linh Tây- Quận  Thủ đức-HCM   "
/**
 * Tiện ích chuẩn hóa địa chỉ tiếng Việt trước khi lưu hoặc hiển thị.
 */
@Component
@RequiredArgsConstructor
public class AddressUtil {
    private final StringUtil stringUtil;

    // Chuẩn hóa địa chỉ có quá nhiều dấu gạch ngang.
    private String reformatViolateOneHyphen(String s) {
        return s.length() - s.replaceAll("-", "").length() > 1 ? s.replaceAll("-", ",") : s;
    }

    // Chuẩn hóa địa chỉ có nhiều dấu chấm liên tiếp.
    private String reformatViolateSingleDot(String s) {
        return s.replaceAll("\\.{2,}", ".");
    }

    // Bổ sung tiền tố tỉnh/thành phố trước tên địa phương cuối.
    private String insertKeywordTinhThanh(String s) {
        return hasText(s) ? s.replaceAll(",(?!.*,)", ",TP.") : s;
    }

    // Chuẩn hóa chuỗi địa chỉ về dạng dễ đọc.
    public String parseToLegalAddress(String s) {
        if (hasText(s)) {
            s = stringUtil.removeWhiteSpaceBeforeAndAfterChar(stringUtil.removeWhiteSpaceBeforeAndAfterChar(
                    StringUtils.capitalizeWords(stringUtil.replaceWordFoundByKeyword(
                            stringUtil.replaceWordFoundByKeyword(
                                    reformatViolateOneHyphen(stringUtil.removeWhiteSpaceBeforeAndAfterChar(
                                            stringUtil.removeWhiteSpaceBeginAndEnd(s), "-")),
                                    ",", ", "),
                            "\\.", "\\. ")),
                    ","), "/"); // Tách riêng bước chuẩn hóa để giữ điều kiện đọc được.
            s = stringUtil.removeWhiteSpaceBeforeAndAfterChar(reformatViolateSingleDot(stringUtil
                    .replaceWordFoundByKeyword(stringUtil.replaceWordFoundByKeyword(
                            stringUtil.replaceWordFoundByKeyword(stringUtil.replaceWordFoundByKeyword(
                                    stringUtil.replaceWordFoundByKeyword(stringUtil.replaceWordFoundByKeyword(stringUtil
                                            .replaceWordFoundByKeyword(stringUtil.replaceWordFoundByKeyword(stringUtil
                                                    .replaceWordFoundByKeyword(stringUtil.replaceWordFoundByKeyword(
                                                            stringUtil.replaceWordFoundByKeyword(
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
                                                                                                                                                                                                                                                                                                                                                                                    .replaceWordFoundByKeyword(
                                                                                                                                                                                                                                                                                                                                                                                            stringUtil
                                                                                                                                                                                                                                                                                                                                                                                                    .replaceWordFoundByKeyword(
                                                                                                                                                                                                                                                                                                                                                                                                            stringUtil
                                                                                                                                                                                                                                                                                                                                                                                                                    .isWordContains(
                                                                                                                                                                                                                                                                                                                                                                                                                            s,
                                                                                                                                                                                                                                                                                                                                                                                                                            ",t.")
                                                                                                                                                                                                                                                                                                                                                                                                                    || stringUtil
                                                                                                                                                                                                                                                                                                                                                                                                                            .isWordContains(
                                                                                                                                                                                                                                                                                                                                                                                                                                    s,
                                                                                                                                                                                                                                                                                                                                                                                                                                    ",tp") ? s
                                                                                                                                                                                                                                                                                                                                                                                                                                            : insertKeywordTinhThanh(
                                                                                                                                                                                                                                                                                                                                                                                                                                                    s),
                                                                                                                                                                                                                                                                                                                                                                                                            ",",
                                                                                                                                                                                                                                                                                                                                                                                                            ", "),
                                                                                                                                                                                                                                                                                                                                                                                            " Số ",
                                                                                                                                                                                                                                                                                                                                                                                            " "),
                                                                                                                                                                                                                                                                                                                                                                            ", Phòng",
                                                                                                                                                                                                                                                                                                                                                                            ", phòng"),
                                                                                                                                                                                                                                                                                                                                                            ", Lầu",
                                                                                                                                                                                                                                                                                                                                                            ", lầu"),
                                                                                                                                                                                                                                                                                                                                            ", Căn",
                                                                                                                                                                                                                                                                                                                                            ", căn"),
                                                                                                                                                                                                                                                                                                                            "Tòa Nhà",
                                                                                                                                                                                                                                                                                                                            "tòa nhà"),
                                                                                                                                                                                                                                                                                                            "Cc",
                                                                                                                                                                                                                                                                                                            "CC"),
                                                                                                                                                                                                                                                                                            "Chung Cư",
                                                                                                                                                                                                                                                                                            "CC"),
                                                                                                                                                                                                                                                                            ", Hẻm",
                                                                                                                                                                                                                                                                            ", hẻm"),
                                                                                                                                                                                                                                                            " Đường",
                                                                                                                                                                                                                                                            " đường"),
                                                                                                                                                                                                                                            ", Tổ",
                                                                                                                                                                                                                                            ", tổ"),
                                                                                                                                                                                                                            "Tndtq",
                                                                                                                                                                                                                            "TNDTQ"),
                                                                                                                                                                                                            "Tổ Nhân Dân Tự Quản",
                                                                                                                                                                                                            "TNDTQ"),
                                                                                                                                                                                            ", Làng",
                                                                                                                                                                                            ", làng"),
                                                                                                                                                                            ", Thôn",
                                                                                                                                                                            ", thôn"),
                                                                                                                                                            ", Xóm",
                                                                                                                                                            ", xóm"),
                                                                                                                                            ", Bản",
                                                                                                                                            ", bản"),
                                                                                                                            ", Buôn",
                                                                                                                            ", buôn"),
                                                                                                            ", Ấp",
                                                                                                            ", ấp"),
                                                                                            "Kp", "KP."),
                                                                                    "Khu Phố", "KP."),
                                                                            ", Xã", ", X."),
                                                                    ", Phường", ", P."),
                                                            "Tt", "TT."), "Thị Trấn", "TT."),
                                                    ", Huyện", ", H."), ", Quận", ", Q."),
                                            "Tx", "TX."), "Thị Xã", "TX."),
                                    ", Tỉnh", ", T."), "Tp", "TP."),
                            "Thành Phố", "TP."), "Hcm", "Hồ Chí Minh")),
                    "\\.");
        }
        return s;
    }
}
