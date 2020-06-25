package com.meli.core.service;

import com.meli.core.model.Planet;
import com.meli.core.model.PlanetLocation;
import org.springframework.stereotype.Service;


/*
 * Clase que se encarga de la creacion de los planetas con las condiciones
 * planteadas en el enunciado del ejercicio
 * */
@Service
public class DefaultPlanetFactory implements PlanetFactory {

  /*
  * Por defecto los planetas inician en la posicion cero (0) con respecto al eje Y
  * dato que la posicion de los planetas puede ser observada por unas coordenadas (X, Y)
  * en el plano cartesiano
  *
  * Para el eje X el valor inicial para cada planeta sera los Km de distancia que tiene
  * con respecto al Sol
  * */
  private final double axisY = 0.0;

  @Override
  public Planet createVulcanosPlanet() {

    int rotationAngle = 360;
    int orbitInKm = 1000;
    PlanetLocation location = new PlanetLocation(rotationAngle, orbitInKm, axisY);

    String name = "Vulcanos";
    int angularSpeed = 5;
    return new Planet(name, orbitInKm, angularSpeed, false, location);
  }

  @Override
  public Planet createFerengisPlanet() {

    int rotationAngle = 0;
    int orbitInKm = 500;
    PlanetLocation location = new PlanetLocation(rotationAngle, orbitInKm, axisY);

    String name = "Ferengis";
    int angularSpeed = 1;
    return new Planet(name, orbitInKm, angularSpeed, true, location);
  }

  @Override
  public Planet createBetasoidesPlanet() {
    int rotationAngle = 0;
    int orbitInKm = 2000;
    PlanetLocation location = new PlanetLocation(rotationAngle, orbitInKm, axisY);

    String name = "Betasoide";
    int angularSpeed = 3;
    return new Planet(name, orbitInKm, angularSpeed, true, location);
  }
}
