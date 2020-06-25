package com.meli.core.utils;

import com.meli.core.model.PlanetLocation;
import java.text.DecimalFormat;

public class PlanetLocationUtils {


  /*
  * Para saber si los 3 palanetas estan alineados se utiliza la formula que permite determinar
  * si 3 puntos pertenecen a la misma recta en el plano cartesiano, asi que teniendo 3 puntos
  * expresados así:
  * P1 = (X1, Y1)
  * P2 = (X2, Y2)
  * P3 = (X3, Y3)
  *
  * La formula es:
  *
  * x2 - X1       y2 - y1
  * -------  =  ----------
  * x3 - x2       y3 - y2
  *
  *
  *
  * En caso de que los 3 puntos tengan el mismo valor en el eje X o en el eje Y no hay necesidad
  * de hacer el calculo y ya se sabe que los puntos están alineados
  *
  * Se da un margen de 0.1 ya que los planetas tiene un diametro por lo tanto los planetas pueden
  * estar alineados aunque no perfectamente
  * */
  public static boolean planetsAreAligned(PlanetLocation locationA,
      PlanetLocation locationB, PlanetLocation locationC) {

    boolean planetsAreAlignedInX = planetsAreAlignedInX(locationA, locationB, locationC);
    boolean planetsAreAlignedInY = planetsAreAlignedInY(locationA, locationB, locationC);
    if (planetsAreAlignedInX || planetsAreAlignedInY)
      return true;
    else {
      DecimalFormat df = new DecimalFormat("#.##");
      double orientationX = Double.parseDouble(df.format((locationB.getX() - locationA.getX()) / (locationC.getX() - locationB.getX())));
      double orientationY = Double.parseDouble(df.format((locationB.getY() - locationA.getY()) / (locationC.getY() - locationB.getY())));
      return Math.abs(orientationX - orientationY) <= 0.1;
    }


  }

  /*
  * Para saber si 3 p[untos pueden formar un triangulo se calcula distancia entre las 3 segmentos de recta
  * posibles y se valida que cada segmento de recta debe ser menor a la suma de los otros 2 segmentos de recta
  *
  * En caso de que un segmento de recta sea mayor a la suma de los otros 2 quiere decir que esos 3 puntos
  * no pueden formar un triangulo
  * */
  public static boolean planetsCanFormATriangle(PlanetLocation locationA,
      PlanetLocation locationB, PlanetLocation locationC) {

    double distanceAB = calculateDistance(locationA, locationB);

    double distanceAC = calculateDistance(locationA, locationC);

    double distanceBC = calculateDistance(locationB, locationC);

    return (distanceAB < (distanceAC + distanceBC)) && (distanceAC < (distanceAB + distanceBC))
        && (distanceBC < (distanceAB + distanceAC));

  }

  /*
   * Para calcular el perimetro de un triangulo se suman las distancias de los 3 segmentos de recta
   * formados por el triangulo
   * */
  public static double calculateTrianglePerimeter(PlanetLocation locationA,
      PlanetLocation locationB, PlanetLocation locationC) {

    double distanceAB = calculateDistance(locationA, locationB);

    double distanceAC = calculateDistance(locationA, locationC);

    double distanceBC = calculateDistance(locationB, locationC);

    return distanceAB + distanceAC + distanceBC;

  }


  /*
   * La orientación de un triángulo es la misma que la orientación de sus tres vértices,
   * así que se puede establecer un algoritmo sencillo para decidir si un punto está o no en el interior de un triángulo.
   *
   * Considerando el triángulo A1A2A3 y el punto P, el algoritmo queda como se muestra a continuación:

   *   1. Calcular la orientación del triángulo A1A2A3.

   *   El cálculo de la orientación de un triángulo se puede realizar según la siguiente fórmula:

   *   (A1.x - A3.x) * (A2.y - A3.y) - (A1.y - A3.y) * (A2.x - A3.x)

   *   Si el resultado es mayor o igual que 0, la orientación del triángulo será positiva.
   * En caso contrario, la orientación del triángulo será negativa.

   *   2. Calcular la orientación de los triángulos que forma el punto P con los vértices del triángulo A1A2A3.
   *   Se calcula la orientación de los triángulos A1A2P, A2A3P, A3A1P, con el método explicado en el punto 1.

   *   3. En el caso de que la orientación del triángulo A1A2A3 sea positiva.
   *   Si las orientaciones de los tres triángulos que tienen como vértice el punto P,
   *   calculadas en el punto 2, son positivas el punto está dentro del triángulo.
   *   En caso contrario el punto está situado fuera del triángulo

   *   4. En el caso de que la orientación del triángulo A1A2A3 sea negativa:
   *   Si las orientaciones de los tres triángulos que triángulos que tienen como vértice el punto P
   *   son negativas, el punto está dentro del triángulo
      En caso contrario el punto está situado fuera del triángulo.
   * */
  public static boolean sunIsInsideTheTriangle(PlanetLocation locationA,
      PlanetLocation locationB, PlanetLocation locationC, PlanetLocation sunLocation) {

    double ABCOrientation = ((locationA.getX() - locationC.getX()) * (locationB.getY() - locationC.getY()))
        - ((locationA.getY() - locationC.getY()) * (locationB.getX() - locationC.getX()));

    double ABSunOrientation = ((locationA.getX() - sunLocation.getX()) * (locationB.getY() - sunLocation.getY()))
        - ((locationA.getY() - sunLocation.getY()) * (locationB.getX() - sunLocation.getX()));

    double BCSunOrientation = ((locationB.getX() - sunLocation.getX()) * (locationC.getY() - sunLocation.getY()))
        - ((locationB.getY() - sunLocation.getY()) * (locationC.getX() - sunLocation.getX()));

    double CASunOrientation = ((locationC.getX() - sunLocation.getX()) * (locationA.getY() - sunLocation.getY()))
        - ((locationC.getY() - sunLocation.getY()) * (locationA.getX() - sunLocation.getX()));

    if (ABCOrientation >= 0) {
      return ABSunOrientation >= 0 && CASunOrientation >= 0 && BCSunOrientation >= 0;
    } else {
      return ABSunOrientation < 0 && CASunOrientation < 0 && BCSunOrientation < 0;
    }
  }

  private static boolean planetsAreAlignedInX(PlanetLocation locationA,
      PlanetLocation locationB, PlanetLocation locationC) {
    return locationA.getX() == locationB.getX() && locationB.getX() == locationC.getX();
  }

  private static boolean planetsAreAlignedInY(PlanetLocation locationA,
      PlanetLocation locationB, PlanetLocation locationC) {
    return locationA.getY() == locationB.getY() && locationB.getY() == locationC.getY();
  }

  private static double calculateDistance(PlanetLocation pointA, PlanetLocation pointB) {
    return Math.sqrt(Math.pow((pointB.getX() - pointA.getX()), 2) + Math
        .pow((pointB.getY() - pointA.getY()), 2));
  }

}
