package com.meli.core.model;

public class PlanetLocation {

  private int rotationAngle;
  private double X;
  private double Y;

  public PlanetLocation(int rotationAngle, double X, double Y) {
    this.rotationAngle = rotationAngle;
    this.X = X;
    this.Y = Y;
  }

  public int getRotationAngle() {
    return rotationAngle;
  }

  public void setRotationAngle(int rotationAngle) {
    this.rotationAngle = rotationAngle;
  }

  public double getX() {
    return X;
  }

  public void setX(double x) {
    this.X = x;
  }

  public double getY() {
    return Y;
  }

  public void setY(double y) {
    this.Y = y;
  }
}
