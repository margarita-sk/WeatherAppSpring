package config.spring;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import city.client.CityClient;
import city.client.YandexCityClient;
import core.db.pool.ConnectionPool;
import core.db.pool.CustomConnectionPool;
import core.db.util.CustomDataSource;
import liquibase.integration.spring.SpringLiquibase;
import weather.client.WeatherClient;
import weather.client.YandexWeatherClient;
import weather.dao.WeatherRepository;
import weather.dao.WeatherRepositoryImpl;

@EnableWebMvc
@Configuration
@ComponentScans({@ComponentScan("city"), @ComponentScan("weather"), @ComponentScan("outfit"),
    @ComponentScan("core.db"), @ComponentScan("user")})
@PropertySource("classpath:application.properties")
public class AppBeansConfiguration {

  @Bean
  public CustomDataSource customDataSource(@Value("${db.driver}") String driverClassName,
      @Value("${db.host}") String url, @Value("${db.username}") String username,
      @Value("${DATABASE_PASS}") char[] password, @Value("${db.maxIdle}") int maxIdle,
      @Value("${db.maxWaitTime}") int maxWaitTime) {
    return new CustomDataSource(driverClassName, url, username, password, maxIdle, maxWaitTime);
  }

  @Bean
  public DataSource dataSource(CustomDataSource source) {
    var dataSource = new DataSource();
    dataSource.setDriverClassName(source.getDriverClassName());
    dataSource.setUrl(source.getUrl());
    dataSource.setUsername(source.getUsername());
    dataSource.setPassword(source.getPassword().toString());
    dataSource.setMaxIdle(source.getMaxIdle());
    dataSource.setMaxWait(source.getMaxWaitTime());
    return dataSource;
  }

  @Bean
  public SpringLiquibase liquibase(DataSource source,
      @Value("${liquibase.change-log}") String changeLogPath) {
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setChangeLog(changeLogPath);
    liquibase.setDataSource(source);
    return liquibase;
  }

  @Bean
  public ConnectionPool connectionPool(CustomDataSource source) {
    return CustomConnectionPool.getInstance(source);
  }

  @Bean
  public WeatherRepository weatherRepository(ConnectionPool pool) {
    return new WeatherRepositoryImpl(pool);
  }

  @Bean
  public CityClient сityClient(@Value("${geocoder.url}") String url,
      @Value("${CITY_KEY}") String key, @Value("${geocoder.format}") String format,
      @Value("${geocoder.language}") String language) {
    return new YandexCityClient(url, key, format, language);
  }

  @Bean
  public WeatherClient weatherClient(WeatherRepository repository,
      @Value("${weather.url}") String url, @Value("${WEATHER_KEY}") String key,
      @Value("${weather.language}") String language) {
    return new YandexWeatherClient(repository, url, key, language);
  }

}
