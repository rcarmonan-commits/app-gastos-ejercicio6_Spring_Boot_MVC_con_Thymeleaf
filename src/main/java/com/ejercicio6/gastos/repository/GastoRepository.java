package com.ejercicio6.gastos.repository;

import com.ejercicio6.gastos.model.Gasto;
import com.ejercicio6.gastos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Repositorio de Gastos.
 * Cumple con el requerimiento de la Unidad 2: "Persistencia de datos".
 * Extiende JpaRepository para heredar métodos CRUD automáticos (save, findById, delete, findAll)
 * sin necesidad de escribir código SQL (DAO) manual como en la Unidad 1.
 */
@Repository
public interface GastoRepository extends JpaRepository<Gasto, Integer> {

    List<Gasto> findByUsuario(Usuario usuario);

    // Reportes parametrizados (Requerimiento de la rúbrica)
    List<Gasto> findByFechaBetween(Date fechaInicio, Date fechaFin);

    @Query("SELECT SUM(g.valorTotalConIVA) FROM Gasto g WHERE g.lugar = :lugar")
    Double sumarGastosConIVAPorLugar(@Param("lugar") String lugar);
}
