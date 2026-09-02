package com.ejercicio6.gastos.controller;

import com.ejercicio6.gastos.model.Gasto;
import com.ejercicio6.gastos.model.Usuario;
import com.ejercicio6.gastos.service.GastoService;
import com.ejercicio6.gastos.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

/**
 * Controlador para la gestión de Gastos.
 * Cumple con los requerimientos de la Unidad 2: "Operaciones CRUD" y "Reportes parametrizados".
 * Delega la lógica de negocio al GastoService y controla el flujo de las vistas Thymeleaf.
 */
@Controller
@RequestMapping("/gastos")
public class GastoController {

    @Autowired
    private GastoService gastoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listarGastos(
            @RequestParam(value = "fechaInicio", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam(value = "fechaFin", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin,
            @RequestParam(value = "lugar", required = false) String lugar,
            Model model) {
        
        if (fechaInicio != null && fechaFin != null) {
            model.addAttribute("gastos", gastoService.listarPorRangoFechas(fechaInicio, fechaFin));
            model.addAttribute("fechaInicio", new java.text.SimpleDateFormat("yyyy-MM-dd").format(fechaInicio));
            model.addAttribute("fechaFin", new java.text.SimpleDateFormat("yyyy-MM-dd").format(fechaFin));
            model.addAttribute("fechaInicioRango", new java.text.SimpleDateFormat("yyyy-MM-dd").format(fechaInicio));
            model.addAttribute("fechaFinRango", new java.text.SimpleDateFormat("yyyy-MM-dd").format(fechaFin));
            
            // Calculate totalRango
            double totalRango = 0.0;
            for(Gasto g : gastoService.listarPorRangoFechas(fechaInicio, fechaFin)) {
                totalRango += g.getValorTotalConIVA();
            }
            model.addAttribute("totalRango", totalRango);
        } else if (lugar != null && !lugar.isEmpty()) {
            Double totalSuma = gastoService.sumarGastosPorLugar(lugar);
            model.addAttribute("totalSuma", totalSuma != null ? totalSuma : 0.0);
            model.addAttribute("lugarSuma", lugar);
            model.addAttribute("lugar", lugar);
            // Optional: Filter the list as well or just show all
            model.addAttribute("gastos", gastoService.listarTodos());
        } else {
            model.addAttribute("gastos", gastoService.listarTodos());
        }
        
        return "gastos/list";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("gasto", new Gasto());
        // Pasamos usuarios para el select del formulario
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "gastos/form";
    }

    @PostMapping("/guardar")
    public String guardarGasto(@Valid @ModelAttribute("gasto") Gasto gasto, 
                               BindingResult result, 
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("usuarios", usuarioService.listarTodos());
            return "gastos/form";
        }
        
        gastoService.guardar(gasto);
        redirectAttributes.addFlashAttribute("exito", "Gasto guardado exitosamente");
        return "redirect:/gastos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer id, Model model) {
        Gasto gasto = gastoService.buscarPorId(id).orElse(null);
        if (gasto == null) {
            return "redirect:/gastos";
        }
        model.addAttribute("gasto", gasto);
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "gastos/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarGasto(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes, HttpSession session) {
        // En el sistema viejo, solo admin elimina
        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
        if (u != null && "Administrador".equals(u.getRol())) {
            gastoService.eliminar(id);
            redirectAttributes.addFlashAttribute("exito", "Gasto eliminado");
        } else {
            redirectAttributes.addFlashAttribute("error", "Solo los administradores pueden eliminar");
        }
        return "redirect:/gastos";
    }

    // --- Reportes Parametrizados ---
    @GetMapping("/reportes")
    public String mostrarReportes(Model model) {
        return "gastos/reportes";
    }

    @PostMapping("/reportes/fechas")
    public String reportePorFechas(
            @RequestParam("fechaInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin,
            Model model) {
        
        model.addAttribute("gastos", gastoService.listarPorRangoFechas(fechaInicio, fechaFin));
        model.addAttribute("filtroAnterior", "Fechas entre: " + fechaInicio + " y " + fechaFin);
        return "gastos/reportes";
    }

    @PostMapping("/reportes/lugar")
    public String reportePorLugar(@RequestParam("lugar") String lugar, Model model) {
        Double total = gastoService.sumarGastosPorLugar(lugar);
        model.addAttribute("mensaje", "El total gastado (con IVA) en " + lugar + " es: $" + total);
        model.addAttribute("filtroAnterior", "Lugar: " + lugar);
        return "gastos/reportes";
    }
}
