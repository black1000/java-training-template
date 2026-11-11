package com.example.weatherapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Incubating;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import static org.assertj.core.api.Assertions.*;

import com.example.weatherapp.client.OpenMeteoClient;
import com.example.weatherapp.dto.WeatherDetailDto;
import com.example.weatherapp.entity.WeatherRecord;
import com.example.weatherapp.exception.ExternalApiException;
import com.example.weatherapp.mapper.WeatherMapper;
import com.example.weatherapp.repository.WeatherRecordRepository;
import com.example.weatherapp.service.impl.WeatherServiceImpl;

@ExtendWith(MockitoExtension.class)
class WeatherServiceImplTest {
    @Mock 
    private OpenMeteoClient openMeteoClient;

    @Mock 
    private WeatherRecordRepository weatherRecordRepository;
    
    @Mock 
    private PrefectureService prefectureService;

    @Mock
    private WeatherMapper weatherMapper;

    @InjectMocks 
    private WeatherServiceImpl weatherService;

    @Test 
    @DisplayName("DBに保存済みの最新データがあればDTOに変換して返す")
    void getLatesWeatherFromDb_found() {
        // Given
        WeatherRecord record = new WeatherRecord();
        WeatherDetailDto dto = WeatherDetailDto.builder().build();
        when(weatherRecordRepository.findLatestWithForecasts(13L))
            .thenReturn(Optional.of(record));
        when(weatherMapper.toDetailDto(record)).thenReturn(dto);

        // When
        WeatherDetailDto result = weatherService.getLatestWeatherFromDb(13L);

        // Then
        assertThat(result).isSameAs(dto);
    }

    @Test 
    @DisplayName ("DBにデータがなければnullを返す")
    void getLatestWeatherFromDb_notFound() {
        // Given
        when(weatherRecordRepository.findLatestWithForecasts(13L))
            .thenReturn (Optional.empty());

        // When & Then
        assertThat(weatherService.getLatestWeatherFromDb(13L)).isNull();
    }

    @Test 
    @DisplayName ("外部APIが失敗したら例外が伝わり、DBには保存されない")
    void getWeatherByPrefectureId_apiError() {
        // Given: 都道府県の取得は成功し、API呼び出しだけ失敗する
        com.example.weatherapp.entity.Prefecture tokyo = new com.example.weatherapp.entity.Prefecture();
        tokyo.setLatitude(35.689);
        tokyo.setLongitude(139.692);
        when(prefectureService.findEntityById(13L)).thenReturn(tokyo);
        when(openMeteoClient.fetchWeather(35.689,139.692))
            .thenThrow(new ExternalApiException("天気情報の取得に失敗しました"));

        // When & Then
        assertThatThrownBy(() -> weatherService.getWeatherByPrefectureId(13L))
            .isInstanceOf(ExternalApiException.class);
        verify(weatherRecordRepository, never()).save(any());
    }
}
