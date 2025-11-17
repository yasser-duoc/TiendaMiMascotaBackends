package com.tiendamascota.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tiendamascota.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByCategory(String category);
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    List<Producto> findByImageUrlIsNullOrImageUrlEquals(String imageUrl);
}
