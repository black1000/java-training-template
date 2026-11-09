package com.example.weatherapp.mapper;

import com.example.weatherapp.dto.CurrentWeatherDto;
import com.example.weatherapp.dto.DailyForecastDto;
import com.example.weatherapp.dto.WeatherDetailDto;
import com.example.weatherapp.entity.DailyForecast;
import com.example.weatherapp.entity.WeatherRecord;
import com.example.weatherapp.util.WeatherCodeMapper;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * WeatherRecord/DailyForecast ↔ DTO 変換
 */
@Component
public class WeatherMapper {
    
    private final PrefectureMapper prefectureMapper;
    
    public WeatherMapper(PrefectureMapper prefectureMapper) {
        this.prefectureMapper = prefectureMapper;
    }
    
    /**
     * WeatherRecord → CurrentWeatherDto
     */
    public CurrentWeatherDto toCurrentDto(WeatherRecord record) {
        if (record == null) {
            return null;
        }
        
        return CurrentWeatherDto.builder()
            .time(record.getFetchedAt())
            .temperature(record.getTemperature())
            .weatherCode(record.getWeatherCode())
            .weatherDescription(WeatherCodeMapper.getDescription(record.getWeatherCode()))
            .weatherIcon(WeatherCodeMapper.getIcon(record.getWeatherCode()))
            .windSpeed(record.getWindSpeed())
            .humidity(record.getHumidity())
            .apparentTemperature(record.getApparentTemperature())
            .precipitation(record.getPrecipitation())
            .cloudCover(record.getCloudCover())
            .build();
    }
    
    /**
     * DailyForecast → DailyForecastDto
     */
    public DailyForecastDto toDailyDto(DailyForecast forecast) {
        if (forecast == null) {
            return null;
        }
        
        // 曜日を日本語で取得
        String[] weekNames = {"月", "火", "水", "木", "金", "土", "日"};
        //String dayOfWeek = weekNames[forecast.getForecastDate().getDayOfWeek().getValue()];
        /*
        getValue()月=1～日=7、配列の添字 0～6 と1つズレ 
        配列の添字に合わせて-1にする*/

    String dayOfWeek = weekNames[
        forecast.getForecastDate().getDayOfWeek().getValue() - 1
    ];


        // 降水確率を計算（降水量から推定）
        Integer precipProb = calculatePrecipitationProbability(
            forecast.getPrecipitationSum()
        );
        
        return DailyForecastDto.builder()
            .date(forecast.getForecastDate())
            .dayOfWeek(dayOfWeek)
            .temperatureMax(forecast.getTemperatureMax())
            .temperatureMin(forecast.getTemperatureMin())
            .weatherCode(forecast.getWeatherCode())
            .weatherDescription(WeatherCodeMapper.getDescription(forecast.getWeatherCode()))
            .weatherIcon(WeatherCodeMapper.getIcon(forecast.getWeatherCode()))
            .precipitationSum(forecast.getPrecipitationSum())
            .precipitationProbability(precipProb)
            .windSpeedMax(forecast.getWindSpeedMax())
            .sunrise(forecast.getSunrise())
            .sunset(forecast.getSunset())
            .build();
    }
    
    /**
     * DailyForecast List → DailyForecastDto List
     */
    public List<DailyForecastDto> toDailyDtoList(List<DailyForecast> forecasts) {
        return forecasts.stream()
            .map(this::toDailyDto)
            .collect(Collectors.toList());
    }
    
    /**
     * WeatherRecord → WeatherDetailDto（完全版）
     */
    public WeatherDetailDto toDetailDto(WeatherRecord record) {
        if (record == null) {
            return null;
        }
        
        return WeatherDetailDto.builder()
            .prefecture(prefectureMapper.toDto(record.getPrefecture()))
            .current(toCurrentDto(record))
            .dailyForecasts(toDailyDtoList(record.getDailyForecasts()))
            .fetchedAt(record.getFetchedAt())
            .build();
    }
    
    /**
     * 降水量から降水確率を推定（簡易計算）
     */
    private Integer calculatePrecipitationProbability(Double precipSum) {
        if (precipSum == null || precipSum <= 0) {
            return 0;
        } else if (precipSum < 1.0) {
            return 20;
        } else if (precipSum < 5.0) {
            return 50;
        } else if (precipSum < 10.0) {
            return 70;
        } else {
            return 90;
        }
    }
}