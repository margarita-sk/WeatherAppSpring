## WeatherAppSpring
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)

## General info
Web application implements 2 ednpoints:
1.Getting information about the proposed set of things for a specific city.
2.Saving information about the temperature interval with the appropriate selection of clothing.
	
## Technologies
Project is created with:
* Java version: 15
* Spring version: 5
* Apache Tomcat version 9 was used for deploynment
	
## Setup
To run this project, install it locally:

clone the codebase
```
$ git clone https://github.com/margarita-sk/WeatherAppSpring.git
```

to install keys for yandex_geocoder and yandex_weather api run a command line and enter commands:
```
$ setx CITY_KEY %KEY%
$ setx WEATHER_KEY %KEY%
```

compile, test, package
```
$ mvn clean package
```

deploy
```
$ mvn tomcat9:deploy
```

