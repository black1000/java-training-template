# API レスポンス解析

## レスポンス構造

### 1. メタ情報

```json
{
  "latitude": 35.7,
  "longitude": 139.6875,
  "generationtime_ms": 0.6129741668701172,
  "timezone": "Asia/Tokyo",
  "timezone_abbreviation": "GMT+9",
  "elevation": 40.0
}
```

### 2. current（現在の天気）

#### 単位情報

```json
{
  "temperature_2m": "°C",
  "weathercode": "wmo code",
  "windspeed_10m": "km/h",
  "relativehumidity_2m": "%",
  "apparent_temperature": "°C",
  "precipitation": "mm",
  "cloudcover": "%"
}
```

### 風速単位について

このレスポンスはAPIのデフォルト設定で取得したため、風速は `km/h` となっている。

本アプリでは `wind_speed_unit=ms` を指定し、風速を `m/s` に統一する。

#### 実際のデータ

```json
{
  "time": "2026-10-19T11:15",
  "temperature_2m": 24.9,
  "weathercode": 2,
  "windspeed_10m": 9.7,
  "relativehumidity_2m": 62,
  "apparent_temperature": 26.5,
  "precipitation": 0.00,
  "cloudcover": 55
}
```

### Javaでのマッピング

```java
public class CurrentWeather {
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

### 3. daily（日次予報）

#### データ構造の特徴

- 各項目が配列形式
- 同じインデックスが同じ日のデータを表す

```json
{
  "time": [
    "2026-10-19",
    "2026-10-20",
    "2026-10-21",
    "2026-10-22",
    "2026-10-23",
    "2026-10-24",
    "2026-10-25"
  ]
}
```

### Javaでのマッピング

```java
public class DailyForecast {
    private String date;
    private Double temperatureMax;
    private Double temperatureMin;
    private Integer weathercode;
    private Double precipitationSum;
    private Double windspeedMax;
    private String sunrise;
    private String sunset;
}
```