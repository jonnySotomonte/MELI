package com.meli.core.model;

public class Planet {

  private String name;
  private int orbitInKm;
  private int angularSpeed;
  private boolean clockwiseRotation;
  private PlanetLocation location;

  public Planet(String name, int orbitInKm, int angularSpeed,
      boolean clockwiseRotation, PlanetLocation location) {
    this.name = name;
    this.orbitInKm = orbitInKm;
    this.angularSpeed = angularSpeed;
    this.clockwiseRotation = clockwiseRotation;
    this.location = location;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getOrbitInKm() {
    return orbitInKm;
  }

  public void setOrbitInKm(int orbitInKm) {
    this.orbitInKm = orbitInKm;
  }

  public int getAngularSpeed() {
    return angularSpeed;
  }

  public void setAngularSpeed(int angularSpeed) {
    this.angularSpeed = angularSpeed;
  }

  public boolean isClockwiseRotation() {
    return clockwiseRotation;
  }

  public void setClockwiseRotation(boolean clockwiseRotation) {
    this.clockwiseRotation = clockwiseRotation;
  }

  public PlanetLocation getLocation() {
    return location;
  }

  public void setLocation(PlanetLocation location) {
    this.location = location;
  }
}
