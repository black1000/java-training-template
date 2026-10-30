# テーブル定義書

## prefectures（都道府県マスタ）

| 列名         | データ型        | NULL | デフォルト                                           | 説明    |
| ---------- | ----------- | ---- | ----------------------------------------------- | ----- |
| id         | BIGINT      | NO   | AUTO_INCREMENT                                  | 主キー   |
| name       | VARCHAR(10) | NO   | -                                               | 都道府県名 |
| name_en    | VARCHAR(50) | NO   | -                                               | 英語名   |
| latitude   | DOUBLE      | NO   | -                                               | 緯度    |
| longitude  | DOUBLE      | NO   | -                                               | 経度    |
| region     | VARCHAR(20) | NO   | -                                               | 地域区分  |
| created_at | TIMESTAMP   | NO   | CURRENT_TIMESTAMP                               | 作成日時  |
| updated_at | TIMESTAMP   | NO   | CURRENT_TIMESTAMP / ON UPDATE CURRENT_TIMESTAMP | 更新日時  |

### 制約・インデックス

* 主キー: `id`
* UNIQUE制約: `name`

---

## weather_records（天気記録）

| 列名                   | データ型      | NULL | デフォルト             | 説明       |
| -------------------- | --------- | ---- | ----------------- | -------- |
| id                   | BIGINT    | NO   | AUTO_INCREMENT    | 主キー      |
| prefecture_id        | BIGINT    | NO   | -                 | 都道府県ID   |
| fetched_at           | TIMESTAMP | NO   | -                 | 天気情報取得日時 |
| temperature          | DOUBLE    | YES  | -                 | 気温（℃）    |
| weather_code         | INT       | YES  | -                 | 天気コード    |
| wind_speed           | DOUBLE    | YES  | -                 | 風速（m/s）  |
| humidity             | INT       | YES  | -                 | 湿度（%）    |
| apparent_temperature | DOUBLE    | YES  | -                 | 体感温度（℃）  |
| precipitation        | DOUBLE    | YES  | -                 | 降水量（mm）  |
| cloud_cover          | INT       | YES  | -                 | 雲量（%）    |
| created_at           | TIMESTAMP | NO   | CURRENT_TIMESTAMP | 作成日時     |

### 制約・インデックス

* 主キー: `id`
* 外部キー: `prefecture_id` → `prefectures.id`
* 削除ルール: `ON DELETE CASCADE`
* インデックス: `prefecture_id`
* インデックス: `fetched_at`

---

## daily_forecasts（日次予報）

| 列名                | データ型      | NULL | デフォルト             | 説明        |
| ----------------- | --------- | ---- | ----------------- | --------- |
| id                | BIGINT    | NO   | AUTO_INCREMENT    | 主キー       |
| weather_record_id | BIGINT    | NO   | -                 | 天気記録ID    |
| forecast_date     | DATE      | NO   | -                 | 予報日       |
| temperature_max   | DOUBLE    | YES  | -                 | 最高気温（℃）   |
| temperature_min   | DOUBLE    | YES  | -                 | 最低気温（℃）   |
| weather_code      | INT       | YES  | -                 | 天気コード     |
| precipitation_sum | DOUBLE    | YES  | -                 | 降水量合計（mm） |
| wind_speed_max    | DOUBLE    | YES  | -                 | 最大風速（m/s） |
| sunrise           | TIME      | YES  | -                 | 日の出時刻     |
| sunset            | TIME      | YES  | -                 | 日の入り時刻    |
| created_at        | TIMESTAMP | NO   | CURRENT_TIMESTAMP | 作成日時      |

### 制約・インデックス

* 主キー: `id`
* 外部キー: `weather_record_id` → `weather_records.id`
* 削除ルール: `ON DELETE CASCADE`
* インデックス: `weather_record_id`
* インデックス: `forecast_date`