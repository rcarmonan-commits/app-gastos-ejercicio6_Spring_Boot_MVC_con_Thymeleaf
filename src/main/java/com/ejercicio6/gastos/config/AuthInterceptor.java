package com.ejercicio6.gastos.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.ejercicio6.gastos.util.GestorConfiguracion;
import java.io.File;

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

        // Lógica de Instalación (Setup Wizard)
        // Detectar si estamos en un contenedor Docker, en Render o en algún PaaS (donde se usan variables de entorno)
        boolean isCloudOrDocker = new File("/.dockerenv").exists() 
                                || "true".equals(System.getenv("RENDER")) 
                                || System.getenv("PORT") != null;
        
        // Si no estamos en la nube/Docker y no hay archivo de configuración local, forzamos el asistente web
        if (!isCloudOrDocker && !GestorConfiguracion.estaConfigurado()) {
            if (!uri.startsWith("/instalador") && !uri.startsWith("/css") && !uri.startsWith("/js")) {
                response.sendRedirect("/instalador");
                return false;
            }
        } else if (uri.startsWith("/instalador")) {
            // Si ya está instalado o estamos en Docker, proteger el instalador para evitar que lo usen
            response.sendRedirect("/");
            return false;
        }

        // Rutas publicas
        if (uri.equals("/") || uri.startsWith("/login") || uri.startsWith("/recuperar") || uri.startsWith("/registro") || uri.startsWith("/css") || uri.startsWith("/js") || uri.startsWith("/instalador")) {
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
