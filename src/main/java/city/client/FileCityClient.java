package city.client;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import city.entity.City;

public class FileCityClient implements CityClient {

//	private String path = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "city.json";
	private String path;

	public FileCityClient(String path) {
		this.path = path;
	}

	@Override
	public City recieveCity(String searchedCityName) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		City city = mapper.readValue(new File(path), City.class);
		city.setSearchedCityName(searchedCityName);
		return city;
	}

}
