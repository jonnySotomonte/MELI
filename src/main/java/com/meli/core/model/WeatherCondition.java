package com.meli.core.model;

public enum WeatherCondition {

  DROUGHT("sequía"),
  RAINY("lluvia"),
  OPTIMUM("óptimo"),
  UNDEFINED("indefinido"),
  INCORRECT("incorrecto");

  private final String weather;

  WeatherCondition(String weather) {
    this.weather = weather;
  }

  public String getWeather() {
    return weather;
  }

}
