# 設計書サマリー

## プロジェクト概要
47都道府県の天気情報を表示するWebアプリケーション

---

## アーキテクチャ

### レイヤー構成
```
Presentation Layer (View)
    ↓
Controller Layer
    ↓
Service Layer
    ↓
Repository Layer
    ↓
Database Layer

External API Client
    ↓
Open-Meteo API
```

---

## 主要コンポーネント

### Controller
- HomeController: トップページ
- WeatherController: 天気詳細ページ
- ErrorController: エラーページ

### Service
- PrefectureService: 都道府県マスタ管理
- WeatherService: 天気情報取得・保存

### Repository
- PrefectureRepository: JpaRepository<Prefecture, Long>
- WeatherRecordRepository: JpaRepository<WeatherRecord, Long>
- DailyForecastRepository: JpaRepository<DailyForecast, Long>

### External Client
- OpenMeteoClient: Open-Meteo API連携

---

## データフロー

### 1. トップページ表示
```
User → Browser → HomeController 
    → PrefectureService 
    → PrefectureRepository 
    → Database
```

### 2. 天気詳細表示
```
User → Browser → WeatherController
    → WeatherService
        → OpenMeteoClient → Open-Meteo API
        → WeatherRecordRepository → Database
    → View
```

---

## セキュリティ

### 設計方針

- SQLインジェクション対策（Spring Data JPAを使用）
- XSS対策（Thymeleafのエスケープ機能を使用）仮

### 今後の検討

- HTTPS化（本番環境）
- CSRF対策（Spring Security導入時に検討）
- レート制限
- ユーザー認証

---

## パフォーマンス

### 目標値
- トップページ: 300ms以内(3秒以内)
- 詳細ページ: 1000ms以内（API含む） (5秒以内)

### 最適化
- データベースインデックス
- 都道府県マスタのキャッシング（任意）

---

## エラーハンドリング

### エラー種類
1. 404 Not Found: 存在しないページ
2. 500 Internal Server Error: サーバーエラー
3. API Error: 外部API障害

### 対応
- GlobalExceptionHandlerで一元管理
- ユーザーフレンドリーなエラーページ
```
