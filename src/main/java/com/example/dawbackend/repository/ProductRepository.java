package com.example.dawbackend.repository;

import java.util.List;

import com.example.dawbackend.service.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

    List<Product> findByName(@Param("name") String name);

}
