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
}
