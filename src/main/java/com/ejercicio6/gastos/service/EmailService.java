package com.ejercicio6.gastos.service;

import com.ejercicio6.gastos.model.ConfiguracionSMTP;
import com.ejercicio6.gastos.model.Usuario;
import com.ejercicio6.gastos.repository.ConfiguracionSMTPRepository;
import com.ejercicio6.gastos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Properties;

@Service
public class EmailService {

    @Autowired
    private ConfiguracionSMTPRepository smtpRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean enviarClaveRecuperacion(String email) {
        Optional<Usuario> userOpt = usuarioRepository.findByEmail(email);
        
        if (userOpt.isPresent()) {
            Usuario usuario = userOpt.get();
            try {
                // Obtener configuracion de la BD de forma dinamica
                Optional<ConfiguracionSMTP> configOpt = smtpRepository.findById(1);
                if (configOpt.isEmpty()) {
                    System.err.println("No se encontro configuracion SMTP en la base de datos.");
                    return false;
                }
                
                ConfiguracionSMTP config = configOpt.get();
                
                // Configurar el enviador de correo en caliente
                JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
                mailSender.setHost(config.getHost());
                mailSender.setPort(config.getPuerto());
                mailSender.setUsername(config.getUsuario());
                mailSender.setPassword(config.getClave());
                
                Properties props = mailSender.getJavaMailProperties();
                props.put("mail.transport.protocol", "smtp");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.debug", "false");
                
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(config.getRemitente() != null ? config.getRemitente() : config.getUsuario());
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
