package com.ejercicio6.gastos.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Interceptor de Autenticación.
 * Cumple con el requerimiento de la Unidad 2: "Autenticación".
 * Sustituye los Filtros (Filter) de Servlets de la Unidad 1.
 * Intercepta todas las peticiones HTTP para verificar que exista una sesión válida (HttpSession)
 * antes de permitir el acceso a los controladores protegidos.
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        String uri = request.getRequestURI();
        // Rutas publicas
        if (uri.equals("/") || uri.startsWith("/login") || uri.startsWith("/recuperar") || uri.startsWith("/registro") || uri.startsWith("/css") || uri.startsWith("/js")) {
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
