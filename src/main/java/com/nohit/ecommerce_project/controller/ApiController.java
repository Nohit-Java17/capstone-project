package com.nohit.ecommerce_project.controller;

import lombok.*;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.exc.*;
import com.fasterxml.jackson.databind.*;
import com.nohit.ecommerce_project.service.*;

import static com.auth0.jwt.JWT.*;
import static com.auth0.jwt.algorithms.Algorithm.*;
import static com.nohit.ecommerce_project.constant.ApplicationConstant.*;
import static com.nohit.ecommerce_project.constant.AttributeConstant.*;
import static com.nohit.ecommerce_project.constant.ViewConstant.*;
import static java.lang.System.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

/**
 * REST controller xử lý refresh token và trả JWT mới cho client đã có refresh token hợp lệ.
 */
@RestController
@RequestMapping(API_VIEW)
@RequiredArgsConstructor
public class ApiController {
    private final KhachHangService userService;

    // Làm mới access token.
    @GetMapping(TOKEN_VIEW + REFRESH_VIEW)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response)
            throws StreamWriteException, DatabindException, IOException {
        var header = request.getHeader(AUTHORIZATION);
        // Kiểm tra định dạng token trong header Authorization.
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            try {
                var refreshToken = header.substring(TOKEN_PREFIX.length());
                var algorithm = HMAC256(SECRET_KEY.getBytes());
                var user = userService.getKhachHang(require(algorithm).build().verify(refreshToken).getSubject());
                var tokens = new HashMap<>();
                tokens.put(ACCESS_TOKEN_KEY,
                        create().withSubject(user.getEmail())
                                .withExpiresAt(new Date(currentTimeMillis() + EXPIRATION_TIME))
                                .withIssuer(request.getRequestURL().toString())
                                .withClaim(ROLE_CLAIM_KEY, List.of(ROLE_PREFIX + user.getVaiTro().toUpperCase()))
                                .sign(algorithm));
                tokens.put(REFRESH_TOKEN_KEY, refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                var errorMsg = "Refresh token không hợp lệ hoặc đã hết hạn.";
                response.setHeader(ERROR_HEADER_KEY, errorMsg);
                response.setStatus(FORBIDDEN.value());
                var error = new HashMap<>();
                error.put(ERROR_MESSAGE_KEY, errorMsg);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}
