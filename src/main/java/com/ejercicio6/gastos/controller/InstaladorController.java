package com.ejercicio6.gastos.controller;

import com.ejercicio6.gastos.util.GestorConfiguracion;
import com.ejercicio6.gastos.util.InstaladorBD;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Controller
public class InstaladorController {

    @GetMapping("/instalador")
    public String mostrarInstalador(Model model) {
        if (GestorConfiguracion.estaConfigurado()) {
            // Si ya está configurado, redirigimos
            return "redirect:/";
        }
        return "instalador";
    }

    @PostMapping("/instalador")
    public String procesarInstalacion(
            @RequestParam("motor") String motor,
            @RequestParam("host") String host,
            @RequestParam("puerto") String puerto,
            @RequestParam("rootUsuario") String rootUsuario,
            @RequestParam("rootClave") String rootClave,
            @RequestParam("nombreNuevaBD") String nombreNuevaBD,
            @RequestParam("appUsuario") String appUsuario,
            @RequestParam("appClave") String appClave,
            Model model) {
        
        try {
            // Extraer schema.sql del classpath a un archivo temporal para el InstaladorBD
            ClassPathResource resource = new ClassPathResource("schema.sql");
            File tempFile = File.createTempFile("schema", ".sql");
            Files.copy(resource.getInputStream(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            boolean exito = InstaladorBD.instalar(
                motor, host, puerto, rootUsuario, rootClave, 
                nombreNuevaBD, appUsuario, appClave, tempFile.getAbsolutePath()
            );

            tempFile.delete();

            if (exito) {
                model.addAttribute("exito", "Base de datos instalada exitosamente. DEBES REINICIAR LA APLICACIÓN para que los cambios tengan efecto.");
                model.addAttribute("instalado", true);
            } else {
                model.addAttribute("error", "No se pudo configurar la base de datos. Verifica las credenciales y el estado del motor.");
            }
            return "instalador";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error interno durante la instalación: " + e.getMessage());
            return "instalador";
        }
    }
}
