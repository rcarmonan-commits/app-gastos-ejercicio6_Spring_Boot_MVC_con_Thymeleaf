package com.ejercicio6.gastos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "gastos")
public class Gasto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gasto")
    private Integer idGasto;

    @NotNull(message = "La fecha es requerida")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private Date fecha;

    @NotNull(message = "El valor sin IVA es requerido")
    @Min(value = 0, message = "El valor no puede ser negativo")
    @Column(name = "valor_total_sin_iva", nullable = false)
    private Double valorTotalSinIVA;

    @NotNull(message = "El IVA es requerido")
    @Min(value = 0, message = "El IVA no puede ser negativo")
    @Column(name = "iva_total", nullable = false)
    private Double ivaTotal;

    @NotNull(message = "El valor total con IVA es requerido")
    @Min(value = 0, message = "El valor total no puede ser negativo")
    @Column(name = "valor_total_con_iva", nullable = false)
    private Double valorTotalConIVA;

    // Relación ManyToOne con Usuario, mapeada por la columna nombre_usuario que es llave foránea a usuarios.id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nombre_usuario", nullable = false)
    private Usuario usuario;

    @NotBlank(message = "El lugar es requerido")
    @Column(length = 150, nullable = false)
    private String lugar;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    public Gasto() {
    }

    public Integer getIdGasto() { return idGasto; }
    public void setIdGasto(Integer idGasto) { this.idGasto = idGasto; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public Double getValorTotalSinIVA() { return valorTotalSinIVA; }
    public void setValorTotalSinIVA(Double valorTotalSinIVA) { this.valorTotalSinIVA = valorTotalSinIVA; }

    public Double getIvaTotal() { return ivaTotal; }
    public void setIvaTotal(Double ivaTotal) { this.ivaTotal = ivaTotal; }

    public Double getValorTotalConIVA() { return valorTotalConIVA; }
    public void setValorTotalConIVA(Double valorTotalConIVA) { this.valorTotalConIVA = valorTotalConIVA; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
