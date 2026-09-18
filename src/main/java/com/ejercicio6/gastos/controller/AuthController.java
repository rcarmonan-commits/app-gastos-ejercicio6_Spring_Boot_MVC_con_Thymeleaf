package com.ejercicio6.gastos.controller;

import com.ejercicio6.gastos.model.Usuario;
import com.ejercicio6.gastos.service.EmailService;
import com.ejercicio6.gastos.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controlador de Autenticación.
 * Cumple con el requerimiento de la Unidad 2: "Aplicación web MVC tradicional".
 * Utiliza @Controller en lugar de @RestController para devolver vistas HTML (Thymeleaf)
 * renderizadas del lado del servidor, en lugar de datos JSON.
 * 
 * Gestiona el inicio de sesión, cierre de sesión y recuperación de contraseñas.
 */
@Controller
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/")
    public String index(HttpSession session) {
        if (session.getAttribute("usuarioLogueado") != null) {
            return "redirect:/gastos";
        }
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("id") String id, 
                        @RequestParam("clave") String clave, 
                        HttpSession session, 
                        Model model) {
        
        Usuario usuario = usuarioService.iniciarSesion(id, clave);
        if (usuario != null) {
            session.setAttribute("usuarioLogueado", usuario);
            return "redirect:/gastos";
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "auth/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/recuperar")
    public String mostrarRecuperar() {
        return "auth/recuperar";
    }

    @PostMapping("/recuperar")
    public String procesarRecuperar(@RequestParam("email") String email, Model model) {
        boolean enviado = emailService.enviarClaveRecuperacion(email);
        if (enviado) {
            model.addAttribute("exito", "Si el correo está registrado, recibirás tu contraseña.");
        } else {
            model.addAttribute("error", "Ocurrió un error al enviar el correo o no está registrado.");
        }
        return "auth/recuperar";
    }

    @GetMapping("/acerca-de")
    public String acercaDe() {
        return "layout/acerca_de";
    }

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth/registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(@org.springframework.web.bind.annotation.ModelAttribute("usuario") Usuario usuario, Model model) {
        if (usuarioService.buscarPorId(usuario.getId()).isPresent()) {
            model.addAttribute("error", "El ID de usuario ya está en uso.");
            return "auth/registro";
        }
        
        // Asignamos rol Operador por defecto a los registrados públicamente
        if (usuario.getRol() == null || usuario.getRol().isEmpty()) {
            usuario.setRol("Operador");
        }
        
        usuarioService.guardar(usuario);
        model.addAttribute("exito", "Registro exitoso. Ahora puedes iniciar sesión.");
        return "auth/registro";
    }
}
