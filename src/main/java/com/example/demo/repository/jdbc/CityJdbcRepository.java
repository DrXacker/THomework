package com.example.demo.repository.jdbc;

import com.example.demo.model.db.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
@Qualifier("jdbcCityRepository")
public class CityJdbcRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public CityJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<City> findAll() {
        String sql = "SELECT * FROM CITY";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(City.class));
    }

    public City findById(UUID id) {
        String sql = "SELECT * FROM CITY WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(City.class));
    }

    public void save(City city) {
        String sql = "INSERT INTO CITY (id, name) VALUES (UUID() , :name)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", city.getName());
        jdbcTemplate.update(sql, params);
    }

    public void update(City city) {
        String sql = "UPDATE CITY SET name = :name WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", city.getName());
        params.addValue("id", city.getId());
        jdbcTemplate.update(sql, params);
    }

    public void delete(UUID id) {
        String sql = "DELETE FROM CITY WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        jdbcTemplate.update(sql, params);
    }


    public UUID findIdByName(String cityName) {
        String sql = "SELECT id FROM CITY WHERE name = :name";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", cityName);

        try {
            return jdbcTemplate.queryForObject(sql, params, UUID.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
