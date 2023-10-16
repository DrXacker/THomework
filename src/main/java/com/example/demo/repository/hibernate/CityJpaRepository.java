package com.example.demo.repository.hibernate;

import com.example.demo.model.db.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CityJpaRepository extends JpaRepository<City, UUID> {
}
