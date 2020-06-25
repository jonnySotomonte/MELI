package com.meli.core;

import com.meli.core.exceptions.RecordNotFoundException;
import com.meli.core.model.Planet;
import com.meli.core.model.PlanetsAlignment;
import com.meli.core.model.SolarSystem;
import com.meli.core.model.SolarSystemWeather;
import com.meli.core.model.WeatherCondition;
import com.meli.core.service.DefaultPlanetFactory;
import com.meli.core.service.DefaultSolarSystemHandler;
import com.meli.core.service.DefaultWeatherPredictor;
import com.meli.core.service.PlanetFactory;
import com.meli.core.service.SolarSystemHandler;
import com.meli.core.service.WeatherPredictor;
import com.meli.core.utils.PlanetLocationUtils;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  private static Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {

    PlanetFactory factory = new DefaultPlanetFactory();
    SolarSystemHandler solarSystemHandler = new DefaultSolarSystemHandler();
    WeatherPredictor weatherPredictor = new DefaultWeatherPredictor();

    Planet vulcanos = factory.createVulcanosPlanet();
    Planet ferengis = factory.createFerengisPlanet();
    Planet betasoides = factory.createBetasoidesPlanet();

    List<Planet> planets = Arrays.asList(vulcanos, ferengis, betasoides);
    SolarSystem solarSystem = new SolarSystem(planets);

    SolarSystemWeather solarSystemWeather;

    for (int day = 1; day <= 3600; day++) {
      solarSystemHandler.rotate(solarSystem);
      WeatherCondition weatherCondition = weatherPredictor.predict(vulcanos, ferengis, betasoides);

      solarSystemWeather = buildSolarSystemWeather(vulcanos, ferengis, betasoides, day,
          weatherCondition);
      solarSystem.addWeather(solarSystemWeather);
    }

    int droughtPeriods = getDroughtPeriods(solarSystem.getWeathers());
    logger.info("Habrán {} periodos de sequía", droughtPeriods);

    int rainyPeriods = getRainyPeriods(solarSystem.getWeathers());
    logger.info("Habrán {} periodos de lluvia", rainyPeriods);

    int dayMaxRain = getDayMaxRain(solarSystem.getWeathers());
    logger.info("EL día más lluvioso será el día {}", dayMaxRain);

    int optimumPeriods = getOptimumPeriods(solarSystem.getWeathers());
    logger.info("Habrán {} periodos de condiciones óptimas", optimumPeriods);

  }

  private static SolarSystemWeather buildSolarSystemWeather(Planet vulcanos, Planet ferengis,
      Planet betasoides, int day,
      WeatherCondition weatherCondition) {

    SolarSystemWeather solarSystemWeather = null;

    if (weatherCondition.equals(WeatherCondition.DROUGHT) || weatherCondition
        .equals(WeatherCondition.OPTIMUM)) {
      solarSystemWeather = new SolarSystemWeather(day, weatherCondition, PlanetsAlignment.LINEAR);
    } else if (weatherCondition.equals(WeatherCondition.RAINY) || weatherCondition
        .equals(WeatherCondition.INCORRECT)) {

      double trianglePerimeter = PlanetLocationUtils
          .calculateTrianglePerimeter(vulcanos.getLocation(), ferengis.getLocation(),
              betasoides.getLocation());

      solarSystemWeather = new SolarSystemWeather(day, weatherCondition,
          PlanetsAlignment.TRIANGULAR,
          trianglePerimeter);
    }
    return solarSystemWeather;
  }

  private static int getDroughtPeriods(List<SolarSystemWeather> weathers) {
    return (int) weathers.stream()
        .filter(weather -> weather.getCondition() == WeatherCondition.DROUGHT)
        .count();
  }

  private static int getRainyPeriods(List<SolarSystemWeather> weathers) {
    return (int) weathers.stream()
        .filter(weather -> weather.getCondition() == WeatherCondition.RAINY)
        .count();
  }

  private static int getOptimumPeriods(List<SolarSystemWeather> weathers) {
    return (int) weathers.stream()
        .filter(weather -> weather.getCondition() == WeatherCondition.OPTIMUM)
        .count();
  }

  private static int getDayMaxRain(List<SolarSystemWeather> weathers) {

    Comparator<SolarSystemWeather> perimeterComparator = Comparator
        .comparing(SolarSystemWeather::getPlanetsTrianglePerimeter)
        .reversed();

    SolarSystemWeather maxRainyDay = weathers.stream()
        .filter(weather -> weather.getCondition() == WeatherCondition.RAINY)
        .min(perimeterComparator).orElseThrow(RecordNotFoundException::new);

    return maxRainyDay.getDay();
  }

}
