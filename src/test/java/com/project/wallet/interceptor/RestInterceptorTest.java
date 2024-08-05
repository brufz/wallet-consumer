package com.project.wallet.interceptor;

import com.project.wallet.config.RestInterceptor;
import com.project.wallet.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RestInterceptorTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    TokenService tokenService;

    @InjectMocks
    RestInterceptor restInterceptor;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPreHandle() throws Exception {
        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoiQURNSU4iLCJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.yCdPVMPz5eIv_oZ4Js08DhLE4BoOuuXLOnKm6YIsN6A";
        when(request.getHeader("Authorization")).thenReturn(token);
        restInterceptor.preHandle(request, response, null);
        verify(tokenService).validateToken(token);
    }
}
