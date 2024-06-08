package com.devsu.ClientePersonaService.repository;

public interface UserCliente {
    Long getId();
    String getClienteId();
    String getContrasena();
    boolean isEstado();
}
