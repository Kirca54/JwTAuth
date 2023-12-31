package com.example.dt_2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "_product")
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Product(String name) {
        this.name = name;
    }
}
