package com.meli.core.controllers;

import com.meli.core.model.Planet;
import com.meli.core.model.SolarSystem;
import com.meli.core.model.WeatherCondition;
import com.meli.core.model.WeatherResponse;
import com.meli.core.service.PlanetFactory;
import com.meli.core.service.SolarSystemHandler;
import com.meli.core.service.WeatherPredictor;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

  private final PlanetFactory factory;

  private final SolarSystemHandler solarSystemHandler;

  private final WeatherPredictor weatherPredictor;

  public WeatherController(PlanetFactory factory,
      SolarSystemHandler solarSystemHandler, WeatherPredictor weatherPredictor) {
    this.factory = factory;
    this.solarSystemHandler = solarSystemHandler;
    this.weatherPredictor = weatherPredictor;
  }

  @GetMapping("/health")
  public ResponseEntity<String> health() {
    return new ResponseEntity<>("OK!!!", HttpStatus.OK);
  }

  @GetMapping("/clima")
  public ResponseEntity<Object> getWeatherByDay(@RequestParam("dia") int day) {

    if (day < 0 || day > 3600)
      return new ResponseEntity<>("El día ingresado es inválido", HttpStatus.BAD_REQUEST);

    Planet vulcanos = factory.createVulcanosPlanet();
    Planet ferengis = factory.createFerengisPlanet();
    Planet betasoides = factory.createBetasoidesPlanet();

    List<Planet> planets = Arrays.asList(vulcanos, ferengis, betasoides);
    SolarSystem solarSystem = new SolarSystem(planets);

    for (int i = 1; i <= day; i++)
      solarSystemHandler.rotate(solarSystem);

    WeatherCondition weatherCondition = weatherPredictor.predict(vulcanos, ferengis, betasoides);
    WeatherResponse weatherResponse = new WeatherResponse(day, weatherCondition.getWeather());
    return new ResponseEntity<>(weatherResponse, HttpStatus.OK);
  }

}
