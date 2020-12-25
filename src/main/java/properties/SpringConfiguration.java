package properties;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import city.client.YandexCityClient;
import weather.client.YandexWeatherClient;

@EnableWebMvc
@Configuration
@ComponentScans({ @ComponentScan("city"), @ComponentScan("weather"), @ComponentScan("outfit"),
		@ComponentScan("core.db") })
@PropertySource("classpath:properties.properties")
public class SpringConfiguration {
	private Environment env;

	@Autowired
	public SpringConfiguration(Environment env) {
		this.env = env;
	}

	@Bean
	public YandexCityClient yandexCityClient() {
		return new YandexCityClient(env.getProperty("geocoder.url"), System.getenv("CITY_KEY"),
				env.getProperty("geocoder.format"), env.getProperty("geocoder.language"));
	}

	@Bean
	public YandexWeatherClient yandexWeatherClient() {
		return new YandexWeatherClient(env.getProperty("weather.url"), System.getenv("WEATHER_KEY"),
				env.getProperty("weather.language"));
	}

	@Bean
	public DataSource dataSource() {
		var source = new DataSource();
		source.setDriverClassName(env.getProperty("db.driver"));
		source.setUrl(env.getProperty("db.host"));
		source.setUsername(env.getProperty("db.username"));
		source.setPassword(env.getProperty("DATABASE_PASS"));
		source.setMinIdle(Integer.parseInt(env.getProperty("db.minIdle")));
		source.setMaxIdle(Integer.parseInt(env.getProperty("db.maxIdle")));
		return source;
	}

}