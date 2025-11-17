package com.tiendamascota.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tiendamascota.model.Orden;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {
    List<Orden> findByUsuarioIdOrderByFechaDesc(Long usuarioId);

    @Query("select distinct o from Orden o left join fetch o.items")
    List<Orden> findAllWithItems();

    @Query("select o from Orden o left join fetch o.items where o.id = :id")
    Optional<Orden> findByIdWithItems(@Param("id") Long id);
}
