package com.example.demo.repository.jdbc;

import com.example.demo.model.db.GuideWeather;
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
@Qualifier("jdbcGuideWeatherRepository")
public class GuideWeatherJdbcRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public GuideWeatherJdbcRepository(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);;
    }

    public List<GuideWeather> findAll() {
        String sql = "SELECT * FROM GUIDE_WEATHER";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(GuideWeather.class));
    }

    public GuideWeather findById(UUID id) {
        String sql = "SELECT * FROM GUIDE_WEATHER WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(GuideWeather.class));
    }

    public GuideWeather findByDescription(String description) {
        String sql = "SELECT * FROM GUIDE_WEATHER WHERE description = :description";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("description", description);
        return namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(GuideWeather.class));
    }

    public void save(GuideWeather guideWeather) {
        String sql = "INSERT INTO GUIDE_WEATHER (id, description) VALUES (gen_random_uuid(), :description)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("description", guideWeather.getDescription());
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void update(GuideWeather guideWeather) {
        String sql = "UPDATE GUIDE_WEATHER SET description = :description WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("description", guideWeather.getDescription());
        params.addValue("id", guideWeather.getId());
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void delete(UUID id) {
        String sql = "DELETE FROM GUIDE_WEATHER WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }

    public UUID findIdByDescription(String description) {
        String sql = "SELECT id FROM GUIDE_WEATHER WHERE description = :description";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("description", description);

        try {
            return namedParameterJdbcTemplate.queryForObject(sql, params, UUID.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
