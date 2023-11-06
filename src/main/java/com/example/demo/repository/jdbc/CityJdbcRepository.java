package com.example.demo.repository.jdbc;

import com.example.demo.model.db.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

@Repository
@Qualifier("jdbcCityRepository")
public class CityJdbcRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public CityJdbcRepository(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<City> findAll() {
        String sql = "SELECT * FROM CITY";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(City.class));
    }

    public City findById(UUID id) {
        String sql = "SELECT * FROM CITY WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(City.class));
    }

    public void save(City city) {
        String sql = "INSERT INTO CITY (id, name) VALUES (gen_random_uuid(), :name)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", city.getName());
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void update(City city) {
        String sql = "UPDATE CITY SET name = :name WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", city.getName());
        params.addValue("id", city.getId());
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void delete(String nameCity) {
        String sql = "DELETE FROM CITY WHERE name = :name";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", nameCity);
        namedParameterJdbcTemplate.update(sql, params);
    }


    public UUID findIdByName(String cityName) {
        String sql = "SELECT id FROM CITY WHERE name = :name";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", cityName);

        try {
            return namedParameterJdbcTemplate.queryForObject(sql, params, UUID.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
