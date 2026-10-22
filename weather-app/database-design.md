-- weather_recordsの検索を高速化
CREATE INDEX idx_weather_records_prefecture 
ON weather_records(prefecture_id);

CREATE INDEX idx_weather_records_fetched 
ON weather_records(fetched_at);

-- daily_forecastsの検索を高速化
CREATE INDEX idx_daily_forecasts_record 
ON daily_forecasts(weather_record_id);

CREATE INDEX idx_daily_forecasts_date 
ON daily_forecasts(forecast_date);


### 目的
- prefecture_id: 都道府県ごとの天気履歴検索を高速化
- fetched_at: 取得日時での検索・並び替えを高速化
- weather_record_id: 天気記録と日次予報の結合を高速化
- forecast_date: 日付による予報検索を高速化