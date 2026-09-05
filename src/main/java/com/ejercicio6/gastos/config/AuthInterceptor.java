package com.ejercicio6.gastos.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        String uri = request.getRequestURI();
        // Rutas publicas
        if (uri.equals("/") || uri.startsWith("/login") || uri.startsWith("/recuperar") || uri.startsWith("/css") || uri.startsWith("/js")) {
            return true;
        }

        // Si no hay usuario logueado en sesion, redirigir al login
        if (request.getSession().getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("/");
            return false;
        }

        return true;
    }
}
