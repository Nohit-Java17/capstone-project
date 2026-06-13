package com.nohit.ecommerce_project.security;

import lombok.*;

import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.savedrequest.*;

import com.nohit.ecommerce_project.filter.*;

import static com.nohit.ecommerce_project.constant.ApplicationConstant.Role.*;
import static com.nohit.ecommerce_project.constant.AttributeConstant.*;
import static com.nohit.ecommerce_project.constant.ViewConstant.*;

/**
 * Cấu hình Spring Security cho đăng nhập form, JWT API, phân quyền giỏ hàng và hồ sơ khách hàng.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final HttpSessionRequestCache httpSessionRequestCache;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        var authenticationFilter = new AuthenticationFilter(authenticationManagerBean());
        authenticationFilter.setFilterProcessesUrl(API_VIEW + LOGIN_VIEW);
        http.csrf().disable().cors().disable().requestCache().requestCache(httpSessionRequestCache).and()
                .authorizeHttpRequests()
                .antMatchers(API_VIEW + LOGIN_VIEW + FREE_VIEW, API_VIEW + TOKEN_VIEW + REFRESH_VIEW + FREE_VIEW,
                        REGISTER_VIEW + FREE_VIEW, PRODUCT_VIEW + FREE_VIEW, CATEGORY_VIEW + FREE_VIEW,
                        ABOUT_VIEW + FREE_VIEW, NOT_FOUND_VIEW + FREE_VIEW, BLANK_VIEW + FREE_VIEW,
                        INDEX_VIEW + FREE_VIEW, PASSWORD_RESET_VIEW + FREE_VIEW, CONTACT_VIEW + FREE_VIEW,
                        DETAIL_VIEW + FREE_VIEW, "/css/**", "/assets/**", "/js/**", "/img/**")
                .permitAll()
                .antMatchers(CART_VIEW + FREE_VIEW, CHECKOUT_VIEW + FREE_VIEW, HISTORY_VIEW + FREE_VIEW,
                        ORDER_VIEW + FREE_VIEW, PROFILE_VIEW + FREE_VIEW)
                .hasRole(CLIENT).anyRequest().permitAll().and().formLogin().loginPage(LOGIN_VIEW)
                .loginProcessingUrl(LOGIN_VIEW).defaultSuccessUrl(INDEX_VIEW).failureUrl(LOGIN_VIEW + "?error=true")
                .permitAll().and().logout().invalidateHttpSession(true).clearAuthentication(true).permitAll().and()
                .exceptionHandling().accessDeniedPage(LOGIN_VIEW).and().rememberMe()
                .rememberMeParameter(REMEMBER_ME_PARAM).and().addFilter(authenticationFilter)
                .addFilterBefore(new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
