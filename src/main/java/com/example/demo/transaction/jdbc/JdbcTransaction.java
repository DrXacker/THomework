package com.example.demo.transaction.jdbc;

import com.example.demo.model.Weather;
import com.example.demo.model.db.City;
import com.example.demo.model.db.GuideWeather;
import com.example.demo.model.db.WeatherInCity;
import com.example.demo.service.jdbc.CityJdbcService;
import com.example.demo.service.jdbc.GuideWeatherJdbcService;
import com.example.demo.service.jdbc.WeatherInCityJdbcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;

@Service
public class JdbcTransaction {
    private final DataSource dataSource;
    private final CityJdbcService cityJdbcService;
    private final GuideWeatherJdbcService guideWeatherJdbcService;
    private final WeatherInCityJdbcService weatherInCityJdbcService;

    @Autowired
    public JdbcTransaction(DataSource dataSource, CityJdbcService cityJdbcService, GuideWeatherJdbcService guideWeatherJdbcService, WeatherInCityJdbcService weatherInCityJdbcService) {
        this.dataSource = dataSource;
        this.cityJdbcService = cityJdbcService;
        this.guideWeatherJdbcService = guideWeatherJdbcService;
        this.weatherInCityJdbcService = weatherInCityJdbcService;
    }

    public List<WeatherInCity> getAllDataWeatherInCity() throws SQLException{
        Connection connection = null;
        Savepoint savepoint = null;
        List<WeatherInCity> weatherInCityData = new ArrayList<>();

        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            savepoint = connection.setSavepoint("savepoint");

            weatherInCityJdbcService.getAll();

            connection.commit();
        } catch (SQLException e) {
            if (savepoint != null) {
                connection.rollback(savepoint);
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        return weatherInCityData;
    }

    public List<WeatherInCity> updateDataWeatherInCity(WeatherInCity weatherInCity) throws SQLException{
        Connection connection = null;
        Savepoint savepoint = null;
        List<WeatherInCity> weatherInCityData = new ArrayList<>();

        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            savepoint = connection.setSavepoint("savepoint");

            weatherInCityJdbcService.update(weatherInCity);
            weatherInCityJdbcService.getAll();

            connection.commit();
        } catch (SQLException e) {
            if (savepoint != null) {
                connection.rollback(savepoint);
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        return weatherInCityData;
    }

    public List<City> deleteDataCity(String nameCity)throws SQLException{
        Connection connection = null;
        Savepoint savepoint = null;
        List<City> cityData = new ArrayList<>();

        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            savepoint = connection.setSavepoint("savepoint");

            cityJdbcService.delete(nameCity);
            cityJdbcService.getAll();

            connection.commit();
        } catch (SQLException e) {
            if (savepoint != null) {
                connection.rollback(savepoint);
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        return cityData;
    }

    public void insertDataFromAPI(Weather weather) throws SQLException {
        Connection connection = null;
        Savepoint savepoint = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            savepoint = connection.setSavepoint("savepoint");
            
            City example2City = new City();
            example2City.setName(weather.getCity());
            cityJdbcService.add(example2City);

            GuideWeather example2GW = new GuideWeather();
            example2GW.setDescription(weather.getDescription());
            guideWeatherJdbcService.add(example2GW);

            WeatherInCity example2WC = new WeatherInCity();
            example2WC.setDate(weather.getDateTime());
            example2WC.setTemperature(weather.getTemperature());
            example2WC.setCityId(cityJdbcService.getIdByCity(weather.getCity()));
            example2WC.setGuideId(guideWeatherJdbcService.getIdByDescription(weather.getDescription()));
            weatherInCityJdbcService.add(example2WC);

            connection.commit();
        } catch (SQLException e) {
            if (savepoint != null) {
                connection.rollback(savepoint);
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
