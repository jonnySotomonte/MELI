package com.meli.core.service;

import com.meli.core.model.Planet;
import com.meli.core.model.PlanetLocation;
import com.meli.core.model.WeatherCondition;
import com.meli.core.utils.PlanetLocationUtils;
import org.springframework.stereotype.Service;

/*
* Clase que se encarga de predecir cual sera la condicion climatica dependiendo de la posicion
* de los planetas y la figura formada por sí mismos
* */
@Service
public class DefaultWeatherPredictor implements WeatherPredictor {

  private final PlanetLocation sunLocation = new PlanetLocation(0, 0.0, 0.0);

  @Override
  public WeatherCondition predict(Planet vulcanos, Planet ferengis, Planet betasoides) {
    PlanetLocation vulcanosLocation = vulcanos.getLocation();
    PlanetLocation ferengisLocation = ferengis.getLocation();
    PlanetLocation betasoidesLocation = betasoides.getLocation();

    boolean planetsAreAligned = PlanetLocationUtils
        .planetsAreAligned(vulcanosLocation, ferengisLocation, betasoidesLocation);

    if (planetsAreAligned) {
      return validateSunAlignment(vulcanosLocation, ferengisLocation);
    } else {
      boolean planetsCanFormATriangle = PlanetLocationUtils
          .planetsCanFormATriangle(vulcanosLocation, ferengisLocation, betasoidesLocation);

      if (planetsCanFormATriangle) {
        return validateSunInsideTheTriangle(vulcanosLocation, ferengisLocation, betasoidesLocation);
      } else {
        return WeatherCondition.UNDEFINED;
      }
    }
  }

  private WeatherCondition validateSunAlignment(PlanetLocation vulcanosLocation,
      PlanetLocation ferengisLocation) {
    boolean planetsAreAlignedWithSun = PlanetLocationUtils
        .planetsAreAligned(vulcanosLocation, ferengisLocation, sunLocation);
    if (planetsAreAlignedWithSun) {
      return WeatherCondition.DROUGHT;
    } else {
      return WeatherCondition.OPTIMUM;
    }
  }

  private WeatherCondition validateSunInsideTheTriangle(PlanetLocation vulcanosLocation,
      PlanetLocation ferengisLocation, PlanetLocation betasoidesLocation) {

    boolean sunIsInsideTheTriangle = PlanetLocationUtils
        .sunIsInsideTheTriangle(vulcanosLocation, ferengisLocation, betasoidesLocation,
            sunLocation);

    if(sunIsInsideTheTriangle) {
      return WeatherCondition.RAINY;
    }
    else {
      return WeatherCondition.INCORRECT;
    }
  }


}
