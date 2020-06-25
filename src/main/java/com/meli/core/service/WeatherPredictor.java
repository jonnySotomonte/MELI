package com.meli.core.service;

import com.meli.core.model.Planet;
import com.meli.core.model.SolarSystem;
import com.meli.core.model.WeatherCondition;

public interface WeatherPredictor {

  WeatherCondition predict(Planet vulcanos, Planet ferengis, Planet betasoides);

}
