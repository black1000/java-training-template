# APIテストシナリオと結果

## テスト実施日
2026-10-19

## シナリオ1: 北海道（寒冷地）

### リクエスト
```text
GET https://api.open-meteo.com/v1/forecast?latitude=43.064&longitude=141.347&current=temperature_2m,weathercode&daily=temperature_2m_max,temperature_2m_min,weathercode&timezone=Asia/Tokyo&forecast_days=7
```

### 結果
- Status: 200 OK
- Response Time: なし
- 現在気温: 19.3℃
- 天気コード: 2（一部曇り）
- 週間最高気温: 8.8℃ ～ 19.6℃
- 週間最低気温: 3.2℃ ～ 10.7℃

### 学び
- 寒冷地でも、その日の天候によって気温が高い場合がある

---

## シナリオ2: 沖縄（温暖地）


### リクエスト
```text
https://api.open-meteo.com/v1/forecast?latitude=26.212&longitude=127.681&current=temperature_2m,weathercode&daily=temperature_2m_max,temperature_2m_min,weathercode&timezone=Asia/Tokyo&forecast_days=7
```

### 結果

- Status: 200 OK
- Response Time: なし
- 現在気温: 29.7℃
- 天気コード: 0（快晴）
- 週間最高気温: 26.7℃ ～ 30.8℃
- 週間最低気温: 23.8℃ ～ 26.7℃

### 週間天気コード

- 10/19: 3（曇り）
- 10/20: 51（軽い霧雨）
- 10/21: 80（にわか雨）
- 10/22: 80（にわか雨）
- 10/23: 95（雷雨）
- 10/24: 95（雷雨）
- 10/25: 53（霧雨）

### 学び

- 晴れだけでなく、にわか雨や雷雨などの天気コードも確認できた
- 地域によって気温や天気の傾向が大きく異なることが分かった

## シナリオ3: 長野（山間部）

### リクエスト

```text
https://api.open-meteo.com/v1/forecast?latitude=36.651&longitude=138.181&current=temperature_2m,weathercode&daily=temperature_2m_max,temperature_2m_min,weathercode&timezone=Asia/Tokyo&forecast_days=7
```

### 結果

- Status: 200 OK
- Response Time: なし
- 現在気温: 21.5℃
- 天気コード: 3（曇り）
- 週間最高気温: 16.0℃ ～ 29.0℃
- 週間最低気温: 6.3℃ ～ 15.9℃
- 標高: 373.0m

### 週間天気コード

- 10/19: 53（霧雨）
- 10/20: 63（雨）
- 10/21: 61（小雨）
- 10/22: 45（霧）
- 10/23: 45（霧）
- 10/24: 53（霧雨）
- 10/25: 53（霧雨）

### 学び

- 週間予報では最高気温が16.0℃～29.0℃と大きく変化している
- 最低気温は6.3℃まで下がる日があり、気温差が大きい
- 雨・霧・霧雨などの天気コードを確認できた

---

## シナリオ4: 連続取得パフォーマンステスト

### テスト内容
東京 → 大阪 → 福岡 を連続で3回取得（計9リクエスト）

### 結果
| 都道府県 | 試行1 | 試行2 | 試行3 | 平均 |
|---------|------|------|------|------|
| 東京 | 286ms | 1190ms | 305ms | 594ms |
| 大阪 | 1200ms | 1190ms | 297ms | 896ms |
| 福岡 | 1170ms | 1150ms | 289ms | 870ms |

### 学び
- レスポンスタイムにはばらつきがある
- 9回の連続取得でもエラーなく取得できた

---

## シナリオ5: エラーケース

### 5-1. 無効な緯度

```text
https://api.open-meteo.com/v1/forecast?latitude=200&longitude=139.6917
```
- Status: 400 Bad Request
- Error Message: "Latitude must be in range of -90 to 90°. Given: 200.0."

### 5-2. 無効な経度

```text
https://api.open-meteo.com/v1/forecast?latitude=35.6895&longitude=200
```
- Status: 400 Bad Request
- Error Message: "Longitude must be in range of -180 to 180°. Given: 200.0."

### 5-3. パラメータなし

```text
https://api.open-meteo.com/v1/forecast
```
Status: 200 OK
Response: 本文なし

### 学び
- 無効な緯度・経度では400 Bad Requestが返った
- エラーメッセージから原因を確認できた
- パラメータなしでは200 OKだがレスポンス本文は空だった

---

## 結論

### APIの信頼性
✅ 9回の連続取得でもエラーなく取得できた
✅ レスポンスタイムにはばらつきがある

### 実装時の考慮事項
1. タイムアウト設定: 5秒
2. リトライ: 3回まで
3. エラーハンドリング: 400/500エラーの処理
4. ローディング表示: 必須