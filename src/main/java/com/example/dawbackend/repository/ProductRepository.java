package com.example.dawbackend.repository;

import java.util.List;

import com.example.dawbackend.service.ProductService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<ProductService, Long>{

    List<ProductService> findByName(@Param("name") String name);

}
