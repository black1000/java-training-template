# 初期データ投入ガイド

## 概要

Weather Appでは、都道府県マスタの初期データを `data.sql` を使用して登録

登録件数は全国47都道府県

## data.sql

ファイル：

`src/main/resources/data.sql`

`prefectures` テーブルに以下の情報を登録

* 都道府県ID
* 都道府県名
* 英語名
* 緯度
* 経度
* 地域

## MySQLでの確認

使用するデータベースを選択

```sql
USE weather_app;
```

登録件数を確認

```sql
SELECT COUNT(*) FROM prefectures;
```

実行結果：

```text
47
```

登録データを確認

```sql
SELECT * FROM prefectures ORDER BY id LIMIT 10;
```

## Spring Bootによる自動投入

Spring Boot起動時に `schema.sql` と `data.sql` を実行し、テーブル作成と初期データ登録を行う。

起動コマンド：

```powershell
./gradlew bootRun
```

## APIによる確認

全都道府県取得：

```text
http://localhost:8080/api/test/prefectures
```

地域別取得：

```text
http://localhost:8080/api/test/prefectures/region/関東
```

関東を指定した場合、以下の7都県が取得できることを確認した。

* 茨城県
* 栃木県
* 群馬県
* 埼玉県
* 千葉県
* 東京都
* 神奈川県

## 動作確認結果

* MySQLへの47都道府県登録：成功
* Spring Boot起動：成功
* 全都道府県取得API：成功
* 地域別取得API：成功
