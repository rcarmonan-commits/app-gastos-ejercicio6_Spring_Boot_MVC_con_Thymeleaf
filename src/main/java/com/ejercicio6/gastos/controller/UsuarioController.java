package com.ejercicio6.gastos.controller;

import com.ejercicio6.gastos.model.Usuario;
import com.ejercicio6.gastos.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Verificar que sea admin (forma manual como se pedía en el proyecto anterior)
    private boolean esAdmin(HttpSession session) {
        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
        return u != null && "Administrador".equals(u.getRol());
    }

    @GetMapping
    public String listarUsuarios(Model model, HttpSession session) {
        if (!esAdmin(session)) {
            return "redirect:/gastos"; // Solo administradores ven usuarios
        }
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "usuarios/list";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model, HttpSession session) {
        // En este sistema, cualquier operador podría no tener permiso, pero dejemos que admin los cree.
        if (!esAdmin(session)) return "redirect:/gastos";
        
        model.addAttribute("usuario", new Usuario());
        return "usuarios/form";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario, 
                                 BindingResult result, 
                                 RedirectAttributes redirectAttributes,
                                 HttpSession session) {
        if (!esAdmin(session)) return "redirect:/gastos";

        if (result.hasErrors()) {
            return "usuarios/form";
        }
        
        usuarioService.guardar(usuario);
        redirectAttributes.addFlashAttribute("exito", "Usuario guardado exitosamente");
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") String id, Model model, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/gastos";

        Usuario usuario = usuarioService.buscarPorId(id).orElse(null);
        if (usuario == null) {
            return "redirect:/usuarios";
        }
        model.addAttribute("usuario", usuario);
        return "usuarios/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable("id") String id, RedirectAttributes redirectAttributes, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/gastos";

        Usuario logueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (logueado.getId().equals(id)) {
            redirectAttributes.addFlashAttribute("error", "No puedes eliminarte a ti mismo");
        } else {
            usuarioService.eliminar(id);
            redirectAttributes.addFlashAttribute("exito", "Usuario eliminado");
        }
        return "redirect:/usuarios";
    }

    // --- Reportes Parametrizados ---
    @GetMapping("/reportes")
    public String mostrarReportes(Model model, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/gastos";
        return "usuarios/reportes";
    }

    @PostMapping("/reportes/rol")
    public String reportePorRol(@RequestParam("rol") String rol, Model model, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/gastos";
        model.addAttribute("usuarios", usuarioService.listarPorRol(rol));
        model.addAttribute("filtroAnterior", "Rol: " + rol);
        return "usuarios/reportes";
    }

    @PostMapping("/reportes/nombre")
    public String reportePorNombre(@RequestParam("nombre") String nombre, Model model, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/gastos";
        model.addAttribute("usuarios", usuarioService.buscarPorNombre(nombre));
        model.addAttribute("filtroAnterior", "Nombre contiene: " + nombre);
        return "usuarios/reportes";
    }
}
