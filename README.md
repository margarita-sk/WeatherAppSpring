## WeatherAppSpring
* [Test task](#test-task)
* [Technologies](#technologies)
* [Setup](#setup)

## Test task
The service must implement 2 ednpointa:
1.Saving information about the temperature interval with the appropriate selection of clothing.
2.Getting information about the proposed set of things for a specific city.

Example 1. Saving information about the temperature interval with the appropriate selection of clothes (the operation is available only to the system administrator). Temperature - [0, +10] Clothes - boots, insulated jacket, pants, shirt Temperature - [+ 25, + 35] Clothes - shorts, T-shirt, cap, sneakers, etc.

Example 2. Obtaining information about the proposed set of things for a specific city (the operation is publicly available and does not require authorization / authentication). Input - Miami Output - shorts, T-shirt, cap Input - Vladivostok Output - boots, an insulated jacket, pants, shirt, etc.
	
## Technologies
Project is created with:
* Java version: 15
* Spring version: 5
To deploy was used: 
* Apache Tomcat version: 9
	
## Setup
To run this project, install it locally:

clone the codebase
```
$ git clone 
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
