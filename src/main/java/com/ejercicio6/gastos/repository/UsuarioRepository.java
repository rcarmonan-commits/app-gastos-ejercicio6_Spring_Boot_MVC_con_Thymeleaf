package com.ejercicio6.gastos.repository;

import com.ejercicio6.gastos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    
    // Para el login
    Optional<Usuario> findByIdAndClave(String id, String clave);
    
    // Para la recuperación de contraseña
    Optional<Usuario> findByEmail(String email);

    // Reportes parametrizados (Requerimiento de la rúbrica)
    List<Usuario> findByRol(String rol);
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
}
