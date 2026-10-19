# DTO設計書

## 1. OpenMeteoResponseDto

APIレスポンス全体を表すDTO

```java
public class OpenMeteoResponseDto {
    private Double latitude;
    private Double longitude;
    private String timezone;
    private Double elevation;
    private CurrentWeatherDto current;
    private DailyWeatherDto daily;
}
```

## 2. CurrentWeatherDto

現在の天気情報

```java
public class CurrentWeatherDto {
    private String time;
    private Double temperature2m;
    private Integer weathercode;
    private Double windspeed10m;
    private Integer relativehumidity2m;
    private Double apparentTemperature;
    private Double precipitation;
    private Integer cloudcover;
}
```

## 3. DailyWeatherDto

日次予報（配列形式）

```java
public class DailyWeatherDto {
    private List<String> time;
    private List<Double> temperature2mMax;
    private List<Double> temperature2mMin;
    private List<Integer> weathercode;
    private List<Double> precipitationSum;
    private List<Double> windspeed10mMax;
    private List<String> sunrise;
    private List<String> sunset;
}
```

## 4. DailyForecastDto
1日分の予報（処理後）

```java
public class DailyForecastDto {
    private String date;
    private Double temperatureMax;
    private Double temperatureMin;
    private Integer weathercode;
    private Double precipitationSum;
    private Double windspeedMax;
    private String sunrise;
    private String sunset;
    
    // 天気コードから天気の説明を取得
    public String getWeatherDescription() {
        return WeatherCodeMapper.getDescription(weathercode);
    }
    
    // 天気コードからアイコンを取得
    public String getWeatherIcon() {
        return WeatherCodeMapper.getIcon(weathercode);
    }
}
```

## 5. WeatherDetailDto

画面表示用の統合DTO

```java
public class WeatherDetailDto {
    // 都道府県情報
    private Long prefectureId;
    private String prefectureName;
    
    // 現在の天気
    private CurrentWeatherDto currentWeather;
    
    // 週間予報
    private List<DailyForecastDto> dailyForecasts;
    
    // 取得日時
    private LocalDateTime fetchedAt;
}
```

## 6. WeatherCodeMapper

天気コードのマッピング

```java
public class WeatherCodeMapper {
    
    private static final Map<Integer, String> DESCRIPTIONS = new HashMap<>();
    private static final Map<Integer, String> ICONS = new HashMap<>();
    
    static {
        // 晴れ系
        DESCRIPTIONS.put(0, "快晴");
        DESCRIPTIONS.put(1, "晴れ");
        DESCRIPTIONS.put(2, "一部曇り");
        DESCRIPTIONS.put(3, "曇り");
        
        ICONS.put(0, "☀️");
        ICONS.put(1, "🌤️");
        ICONS.put(2, "⛅");
        ICONS.put(3, "☁️");
        
        // 雨系
        DESCRIPTIONS.put(61, "小雨");
        DESCRIPTIONS.put(63, "雨");
        DESCRIPTIONS.put(65, "大雨");
        
        ICONS.put(61, "🌧️");
        ICONS.put(63, "🌧️");
        ICONS.put(65, "🌧️");
        
        // 雪系
        DESCRIPTIONS.put(71, "小雪");
        DESCRIPTIONS.put(73, "雪");
        DESCRIPTIONS.put(75, "大雪");
        
        ICONS.put(71, "🌨️");
        ICONS.put(73, "❄️");
        ICONS.put(75, "❄️");
        
        // 雷雨
        DESCRIPTIONS.put(95, "雷雨");
        ICONS.put(95, "⛈️");
        
        // ... 他の天気コードも追加
    }
    
    public static String getDescription(Integer code) {
        return DESCRIPTIONS.getOrDefault(code, "不明");
    }
    
    public static String getIcon(Integer code) {
        return ICONS.getOrDefault(code, "❓");
    }
}
```

## JSON↔DTOのマッピング例

### Jackson使用時
```java
ObjectMapper mapper = new ObjectMapper();
OpenMeteoResponseDto response = mapper.readValue(jsonString, OpenMeteoResponseDto.class);
```

### フィールド名のマッピング
```java
@JsonProperty("temperature_2m")
private Double temperature2m;

@JsonProperty("weathercode")
private Integer weathercode;
```