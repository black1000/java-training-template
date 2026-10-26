# テーブル定義書

## prefectures（都道府県マスタ）

| 列名 | データ型 | NULL | デフォルト | 説明 |
|---|---|---|---|---|
| id | BIGINT | NO | AUTO_INCREMENT | 主キー |
| name | VARCHAR(10) | NO | - | 都道府県名 |
| name_en | VARCHAR(50) | NO | - | 英語名 |
| latitude | DOUBLE | NO | - | 緯度 |
| longitude | DOUBLE | NO | - | 経度 |
| region | VARCHAR(20) | NO | - | 地域区分 |
| created_at | TIMESTAMP | NO | CURRENT_TIMESTAMP | 作成日時 |
| updated_at | TIMESTAMP | NO | ON UPDATE | 更新日時 |

---

## daily_forecasts（日次予報）

| 列名 | データ型 | NULL | デフォルト | 説明 |
|---|---|---|---|---|
| id | BIGINT | NO | AUTO_INCREMENT | 主キー |
| weather_record_id | BIGINT | NO | - | 天気記録ID |
| forecast_date | DATE | NO | - | 予報日 |
| temperature_max | DOUBLE | YES | - | 最高気温（℃） |
| temperature_min | DOUBLE | YES | - | 最低気温（℃） |
| weather_code | INT | YES | - | 天気コード |
| precipitation_sum | DOUBLE | YES | - | 降水量合計（mm） |
| wind_speed_max | DOUBLE | YES | - | 最大風速（m/s） |
| sunrise | TIME | YES | - | 日の出時刻 |
| sunset | TIME | YES | - | 日の入り時刻 |
| created_at | TIMESTAMP | NO | CURRENT_TIMESTAMP | 作成日時 |