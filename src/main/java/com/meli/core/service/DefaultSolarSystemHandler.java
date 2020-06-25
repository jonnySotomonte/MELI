package com.meli.core.service;

import com.meli.core.model.Planet;
import com.meli.core.model.PlanetLocation;
import com.meli.core.model.SolarSystem;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import org.springframework.stereotype.Service;

/*
* Clase que se encarga de realizar la rotacion de los planetas al rededor del sol
* */
@Service
public class DefaultSolarSystemHandler implements SolarSystemHandler {

  @Override
  public void rotate(SolarSystem solarSystem) {
    List<Planet> planets = solarSystem.getPlanets();
    for (Planet planet : planets) {
      PlanetLocation location = planet.getLocation();

      int newRotationAngle = getNewRotationAngle(planet);

      double newAxisXPosition = getNewAxisXPosition(newRotationAngle, planet);
      double newAxisYPosition = getNewAxisYPosition(newRotationAngle, planet);

      location.setRotationAngle(newRotationAngle);
      location.setX(newAxisXPosition);
      location.setY(newAxisYPosition);

      planet.setLocation(location);
    }

  }

  /**
   * Cada planeta tiene una orientacion de rotacion:
   *
   * En caso de ser contraria a las manecillas del reloj el angulo de rotacion aumenta tantos grados
   * como velocidad angular tenga el planeta.
   *
   * En caso de ser igual a las manecillas del reloj el angulo de rotacion disminuye tantos grados
   * como velocidad angular tenga el planeta.
   *
   * El angulo de rotacion es la amplitud del angulo formado
   * entre la posicion del planeta y la posicion del sol, teniendo en cuenta la funcion
   * de la circunferencia unitaria la ubicacion del sol siempre sera (X,Y) = (0,0)
   *
   *
   * Si el angulo de rotacion > 360 || angulo de rotacion < 0 se realiza la operacion necesaria
   * para expresar dicho angulo en el rango 0< angulo de rotacion < 360
   * */
  private int getNewRotationAngle(Planet planet) {
    PlanetLocation location = planet.getLocation();
    int angularSpeed = planet.getAngularSpeed();
    int rotationAngle = location.getRotationAngle();
    int newRotationAngle;
    if (!planet.isClockwiseRotation()) {
      newRotationAngle = (rotationAngle + angularSpeed) % 360;
    } else {
      newRotationAngle = rotationAngle - angularSpeed;
      if (newRotationAngle < 0)
        newRotationAngle = newRotationAngle + 360;
    }
    return newRotationAngle;
  }


  /*
  * Para calcular cual es la nueva posicion del planeta con respecto al eje X luego de realizar
  * la rotación se utilizan las identidades trigonometricas, en este caso en particular la
  * siguiente formula:
  *
  * cos(θ) = X * R
  *
  * θ es el angulo de rotacion explicado anteriormente
  * R es el radio de la circunferencia, en este caso la distancia entre el planeta y el sol
  * */
  private double getNewAxisXPosition(int newRotationAngle, Planet planet) {
    int orbitInKm = planet.getOrbitInKm();
    DecimalFormat df = new DecimalFormat("#.##");
    df.setRoundingMode(RoundingMode.DOWN);
    String valueAsString = df.format(Math.cos(Math.toRadians(newRotationAngle)) * orbitInKm);
    return Double.parseDouble(valueAsString);
  }

  /*
   * Para calcular cual es la nueva posicion del planeta con respecto al eje Y luego de realizar
   * la rotación se utilizan las identidades trigonometricas, en este caso en particular la
   * siguiente formula:
   *
   * sin(θ) = Y * R
   *
   * θ es el angulo de rotacion explicado anteriormente
   * R es el radio de la circunferencia, en este caso la distancia entre el planeta y el sol
   * */
  private double getNewAxisYPosition(int newRotationAngle, Planet planet) {
    int orbitInKm = planet.getOrbitInKm();
    DecimalFormat df = new DecimalFormat("#.##");
    String valueAsString = df.format(Math.sin(Math.toRadians(newRotationAngle)) * orbitInKm);
    return Double.parseDouble(valueAsString);
  }
}
