package com.project.wallet.config;

import com.project.wallet.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

@Service
@RequiredArgsConstructor
public class RestInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        tokenService.validateToken(token);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

}
