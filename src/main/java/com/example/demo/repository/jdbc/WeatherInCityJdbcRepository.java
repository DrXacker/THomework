package com.example.demo.repository.jdbc;

import com.example.demo.model.db.WeatherInCity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

@Repository
@Qualifier("jdbcWeatherInCityRepository")
public class WeatherInCityJdbcRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public WeatherInCityJdbcRepository(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);;
    }

    public List<WeatherInCity> findAll() {
        String sql = "SELECT * FROM WEATHER_IN_CITY";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(WeatherInCity.class));
    }

    public WeatherInCity findById(UUID id) {
        String sql = "SELECT * FROM WEATHER_IN_CITY WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(WeatherInCity.class));
    }

    public void save(WeatherInCity weather) {
        String sql = "INSERT INTO WEATHER_IN_CITY (id, city_id, guide_id, date , temperature) " +
                "VALUES (UUID(), :city_id, :guide_id, :date, :temperature)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("city_id", weather.getCityId());
        params.addValue("guide_id", weather.getGuideId());
        params.addValue("date", weather.getDate());
        params.addValue("temperature", weather.getTemperature());
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void update(WeatherInCity weather) {
        String sql = "UPDATE WEATHER_IN_CITY SET city_id = :city_id, guide_id = :guide_id, " +
                "date = :date, temperature = :temperature WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("city_id", weather.getCityId());
        params.addValue("guide_id", weather.getGuideId());
        params.addValue("date", weather.getDate());
        params.addValue("temperature", weather.getTemperature());
        params.addValue("id", weather.getId());
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void delete(UUID id) {
        String sql = "DELETE FROM WEATHER_IN_CITY WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }
}
