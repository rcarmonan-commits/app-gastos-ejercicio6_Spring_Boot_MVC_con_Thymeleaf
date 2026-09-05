package com.ejercicio6.gastos.service;

import com.ejercicio6.gastos.model.Usuario;
import com.ejercicio6.gastos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean enviarClaveRecuperacion(String email) {
        Optional<Usuario> userOpt = usuarioRepository.findByEmail(email);
        
        if (userOpt.isPresent()) {
            Usuario usuario = userOpt.get();
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(usuario.getEmail());
                message.setSubject("Recuperación de Contraseña - Sistema de Gastos");
                message.setText("Hola " + usuario.getNombre() + ",\n\n"
                        + "Has solicitado la recuperación de tu contraseña.\n"
                        + "Tu contraseña actual es: " + usuario.getClave() + "\n\n"
                        + "Te recomendamos cambiarla por seguridad una vez inicies sesión.\n\n"
                        + "Saludos,\nAdministración del Sistema.");
                
                mailSender.send(message);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
