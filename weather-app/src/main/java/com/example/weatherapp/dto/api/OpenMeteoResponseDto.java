package com.example.weatherapp.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Open-Meteo APIのレスポンス全体を受け取るDTO
 *
 * JSONの項目名(snake_case)とJavaのフィールド名(camelCase)は、
 * @JsonProperty で対応づける
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)  // DTOにない項目があってもエラーにしない
public class OpenMeteoResponseDto {

    private Double latitude;
    private Double longitude;
    private String timezone;

    /** 現在の天気 */
    private Current current;

    /** 日次予報（項目ごとの配列形式） */
    private Daily daily;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Current {
        private String time;

        @JsonProperty("temperature_2m")
        private Double temperature2m;

        private Integer weathercode;

        @JsonProperty("windspeed_10m")
        private Double windspeed10m;

        @JsonProperty("relativehumidity_2m")
        private Integer relativehumidity2m;

        @JsonProperty("apparent_temperature")
        private Double apparentTemperature;

        private Double precipitation;

        @JsonProperty("cloud_cover")
        private Integer cloudCover;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Daily {
        private List<String> time;

        @JsonProperty("temperature_2m_max")
        private List<Double> temperature2mMax;

        @JsonProperty("temperature_2m_min")
        private List<Double> temperature2mMin;

        private List<Integer> weathercode;

        @JsonProperty("precipitation_sum")
        private List<Double> precipitationSum;

        @JsonProperty("windspeed_10m_max")
        private List<Double> windspeed10mMax;

        private List<String> sunrise;
        private List<String> sunset;
    }
}