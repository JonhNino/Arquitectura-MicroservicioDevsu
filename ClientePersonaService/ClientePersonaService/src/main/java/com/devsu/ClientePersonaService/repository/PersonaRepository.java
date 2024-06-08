package com.devsu.ClientePersonaService.repository;


import com.devsu.ClientePersonaService.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
}
