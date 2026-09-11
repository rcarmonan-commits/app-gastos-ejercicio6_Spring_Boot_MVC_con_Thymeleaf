package com.ejercicio6.gastos.repository;

import com.ejercicio6.gastos.model.ConfiguracionSMTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracionSMTPRepository extends JpaRepository<ConfiguracionSMTP, Integer> {
}
