package com.example.weatherapp.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DailyForecastDto {
    private LocalDate date;
    private Double temperatureMax;
    private Double temperatureMin;
    private Integer weatherCode;
    private String weatherDescription;
    private String weatherIcon;
    private Double precipitationSum;
}