package com.example.demo.repository.jdbc;

import com.example.demo.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CityRepository {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<City> findAll() {
        String sql = "SELECT * FROM city";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(City.class));
    }

    public City findById(Long id) {
        String sql = "SELECT * FROM city WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(City.class));
    }

    public void save(City city) {
        String sql = "INSERT INTO city (name) VALUES (:name)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", city.getName());
        jdbcTemplate.update(sql, params);
    }

    public void update(City city) {
        String sql = "UPDATE city SET name = :name WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", city.getName());
        params.addValue("id", city.getId());
        jdbcTemplate.update(sql, params);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM city WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        jdbcTemplate.update(sql, params);
    }
}
