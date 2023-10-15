package com.example.demo.repository.jdbc;

import com.example.demo.model.GuideWeather;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("jdbcGuideWeatherRepository")
public class GuideWeatherRepository {
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<GuideWeather> findAll() {
        String sql = "SELECT * FROM guideweather";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(GuideWeather.class));
    }

    public GuideWeather findById(Long id) {
        String sql = "SELECT * FROM guideweather WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(GuideWeather.class));
    }

    public GuideWeather findByName(String name) {
        String sql = "SELECT * FROM guideweather WHERE description = :description";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);
        return jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(GuideWeather.class));
    }

    public void save(GuideWeather guideWeather) {
        String sql = "INSERT INTO guideweather (description) VALUES (:description)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", guideWeather.getDescription());
        jdbcTemplate.update(sql, params);
    }

    public void update(GuideWeather guideWeather) {
        String sql = "UPDATE GuideWeather SET description = :description WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", guideWeather.getDescription());
        params.addValue("id", guideWeather.getId());
        jdbcTemplate.update(sql, params);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM GuideWeather WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        jdbcTemplate.update(sql, params);
    }
}
