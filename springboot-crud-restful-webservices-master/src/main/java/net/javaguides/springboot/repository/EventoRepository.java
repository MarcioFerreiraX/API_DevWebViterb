package net.javaguides.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.entity.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    // Aqui você pode adicionar métodos de consulta personalizados, se necessário
}
