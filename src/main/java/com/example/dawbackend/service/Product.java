package com.example.dawbackend.service;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity
@Table(name = "product")
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private String description;


    private Long price;
    private int stock;

    public Product() {

    }

    public Product(Long id, String name, String description, Long price, int stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock= stock;
    }


    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + ", descripcion=" + description + ", precio=" + price + ", stock=" + stock + "]";
    }

}