package net.javaguides.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.entity.Espaco;

@Repository
public interface EspacoRepository extends JpaRepository<Espaco, Long>{

}
