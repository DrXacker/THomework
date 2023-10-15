package com.example.demo.repository.jdbc;

import com.example.demo.model.WeatherInCity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("jdbcWeatherInCityRepository")
public class WeatherInCityRepository {
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<WeatherInCity> findAll() {
        String sql = "SELECT * FROM weatherincity";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(WeatherInCity.class));
    }

    public WeatherInCity findById(Long id) {
        String sql = "SELECT * FROM weatherincity WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(WeatherInCity.class));
    }

    public void save(WeatherInCity weather) {
        String sql = "INSERT INTO weatherincity (city_id, guide_id, date , temperature) " +
                "VALUES (:city_id, :guide_id, :date, :temperature)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("city_id", weather.getCity_id());
        params.addValue("guide_id", weather.getGuide_id());
        params.addValue("date", weather.getDate());
        params.addValue("temperature", weather.getTemperature());
        jdbcTemplate.update(sql, params);
    }

    public void update(WeatherInCity weather) {
        String sql = "UPDATE weatherincity SET city_id = :city_id, guide_id = :guide_id, " +
                "date = :date, temperature = :temperature WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("city_id", weather.getCity_id());
        params.addValue("guide_id", weather.getGuide_id());
        params.addValue("date", weather.getDate());
        params.addValue("temperature", weather.getTemperature());
        params.addValue("id", weather.getId());
        jdbcTemplate.update(sql, params);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM weatherincity WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        jdbcTemplate.update(sql, params);
    }
}
