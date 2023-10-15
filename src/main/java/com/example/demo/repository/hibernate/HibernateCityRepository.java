package com.example.demo.repository.hibernate;

import com.example.demo.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HibernateCityRepository extends JpaRepository<City, Long> {

    List<City> findAll();

    void deleteById(Long id);

}
