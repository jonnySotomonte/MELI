package com.meli.core.model;

import java.util.ArrayList;
import java.util.List;

public class SolarSystem {

  List<Planet> planets;
  List<SolarSystemWeather> weathers;

  public SolarSystem(List<Planet> planets) {
    this.planets = planets;
    this.weathers = new ArrayList<>();
  }

  public List<Planet> getPlanets() {
    return planets;
  }

  public List<SolarSystemWeather> getWeathers() {
    return weathers;
  }

  public void addWeather(SolarSystemWeather weather) {
    weathers.add(weather);
  }
}
