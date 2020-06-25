package com.meli.core.model;

public class WeatherResponse {

  private final int dia;
  private final String clima;

  public WeatherResponse(int dia, String clima) {
    this.dia = dia;
    this.clima = clima;
  }

  public int getDia() {
    return dia;
  }

  public String getClima() {
    return clima;
  }
}
