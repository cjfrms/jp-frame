package com.jptech.jpframe.jpauth.conf;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        if("XMLHttpRequest".equals(httpServletRequest.getHeader("X-Requested-With")) ||
                httpServletRequest.getHeader("Accept").contains("application/json")){
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }else{
            httpServletResponse.sendRedirect( httpServletRequest.getContextPath() + "/login");
        }

    }
}
