package com.ejercicio6.gastos.controller;

import com.ejercicio6.gastos.model.ConfiguracionSMTP;
import com.ejercicio6.gastos.repository.ConfiguracionSMTPRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/smtp")
public class ConfiguracionSMTPController {

    @Autowired
    private ConfiguracionSMTPRepository smtpRepository;

    @GetMapping
    public String verFormulario(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/";
        }
        
        Optional<ConfiguracionSMTP> configOpt = smtpRepository.findById(1);
        ConfiguracionSMTP config = configOpt.orElseGet(() -> {
            ConfiguracionSMTP nueva = new ConfiguracionSMTP();
            nueva.setId(1);
            return nueva;
        });
        
        model.addAttribute("smtp", config);
        return "smtp/form";
    }

    @PostMapping("/guardar")
    public String guardarConfiguracion(ConfiguracionSMTP config, HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/";
        }
        
        config.setId(1); // Siempre usamos el registro 1
        smtpRepository.save(config);
        
        model.addAttribute("smtp", config);
        model.addAttribute("exito", "Configuración SMTP actualizada correctamente.");
        return "smtp/form";
    }
}
