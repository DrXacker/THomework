package com.example.demo.repository.hibernate;

import com.example.demo.model.db.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CityJpaRepository extends JpaRepository<City, UUID> {

    @Query("SELECT c.id FROM City c WHERE c.name = :name")
    UUID findIdByCity(@Param("name") String name);
}
