package com.mechanical.mechanicalsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mechanical.mechanicalsite.model.Peca;

@Repository
public interface PecaRepository extends JpaRepository<Peca, Integer> {

}
