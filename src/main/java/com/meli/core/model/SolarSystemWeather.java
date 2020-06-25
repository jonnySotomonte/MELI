package com.meli.core.model;

public class SolarSystemWeather {

  private final int day;
  private final WeatherCondition condition;
  private final PlanetsAlignment alignment;
  private final double planetsTrianglePerimeter;

  public SolarSystemWeather(int day, WeatherCondition condition, PlanetsAlignment alignment,
      double planetsTrianglePerimeter) {
    this.day = day;
    this.condition = condition;
    this.alignment = alignment;
    this.planetsTrianglePerimeter = planetsTrianglePerimeter;
  }

  public SolarSystemWeather(int day, WeatherCondition condition, PlanetsAlignment alignment) {
    this.day = day;
    this.condition = condition;
    this.alignment = alignment;
    this.planetsTrianglePerimeter = 0;
  }

  public int getDay() {
    return day;
  }

  public WeatherCondition getCondition() {
    return condition;
  }

  public PlanetsAlignment getAlignment() {
    return alignment;
  }

  public double getPlanetsTrianglePerimeter() {
    return planetsTrianglePerimeter;
  }
}
