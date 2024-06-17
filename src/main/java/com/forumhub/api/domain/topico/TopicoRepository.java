package com.forumhub.api.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.UUID;

public interface TopicoRepository extends JpaRepository<Topico, UUID> {
    Page<Topico> findAllByStatusTrue(Pageable page);

    Topico findByCurso(String curso);

    @Query("SELECT t FROM Topico t WHERE YEAR(t.dataCriacao) = :ano")
    Page<Topico> findByAno(int ano, Pageable pageable);
}
