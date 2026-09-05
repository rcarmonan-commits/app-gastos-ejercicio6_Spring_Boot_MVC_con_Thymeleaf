package com.ejercicio6.gastos.service;

import com.ejercicio6.gastos.model.Usuario;
import com.ejercicio6.gastos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorId(String id) {
        return usuarioRepository.findById(id);
    }

    public void eliminar(String id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario iniciarSesion(String id, String clave) {
        return usuarioRepository.findByIdAndClave(id, clave).orElse(null);
    }

    // Reportes
    public List<Usuario> listarPorRol(String rol) {
        return usuarioRepository.findByRol(rol);
    }

    public List<Usuario> buscarPorNombre(String nombre) {
        return usuarioRepository.findByNombreContainingIgnoreCase(nombre);
    }
}
