package com.ejercicio6.gastos.service;

import com.ejercicio6.gastos.model.Gasto;
import com.ejercicio6.gastos.model.Usuario;
import com.ejercicio6.gastos.repository.GastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;

    public List<Gasto> listarTodos() {
        return gastoRepository.findAll();
    }

    public Gasto guardar(Gasto gasto) {
        return gastoRepository.save(gasto);
    }

    public Optional<Gasto> buscarPorId(Integer id) {
        return gastoRepository.findById(id);
    }

    public void eliminar(Integer id) {
        gastoRepository.deleteById(id);
    }

    // Reportes
    public List<Gasto> listarPorRangoFechas(Date inicio, Date fin) {
        return gastoRepository.findByFechaBetween(inicio, fin);
    }

    public Double sumarGastosPorLugar(String lugar) {
        Double total = gastoRepository.sumarGastosConIVAPorLugar(lugar);
        return total != null ? total : 0.0;
    }
    
    public List<Gasto> listarPorUsuario(Usuario usuario) {
        return gastoRepository.findByUsuario(usuario);
    }
}
