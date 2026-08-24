package com.ejercicio6.gastos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @Column(length = 50, nullable = false)
    @NotBlank(message = "El ID/Usuario es requerido")
    private String id;

    @Column(nullable = false)
    @NotBlank(message = "La clave es requerida")
    @Size(min = 4, message = "La clave debe tener al menos 4 caracteres")
    private String clave;

    @Column(length = 100, nullable = false)
    @NotBlank(message = "El nombre es requerido")
    private String nombre;

    @Column(length = 150)
    @NotBlank(message = "El correo es requerido")
    @Email(message = "Debe ser un correo válido")
    private String email;

    @Column(length = 50, nullable = false)
    private String rol = "Operador"; // Por defecto Operador

    public Usuario() {
    }

    public Usuario(String id, String clave, String nombre, String email, String rol) {
        this.id = id;
        this.clave = clave;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
