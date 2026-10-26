# データベースセットアップガイド

## 1. データベース作成

MySQL Workbenchで `schema.sql` を実行

## 2. 作成されるテーブル

- prefectures
- weather_records
- daily_forecasts

## 3. 確認SQL

USE weather_app;

SHOW TABLES;

DESCRIBE prefectures;
DESCRIBE weather_records;
DESCRIBE daily_forecasts;

## 4. Spring Boot設定

`application.properties` に以下を設定

```properties
spring.sql.init.mode=always
spring.sql.init.schema-locations=classpath:schema.sql
spring.sql.init.continue-on-error=false

spring.jpa.hibernate.ddl-auto=none