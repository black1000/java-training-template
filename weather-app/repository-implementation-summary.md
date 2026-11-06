# Repository実装サマリー
## 実装したRepository
### 1. PrefectureRepository

都道府県マスタを操作するRepositoryを実装した。

主な処理:
- 全件取得
- ID検索
- 都道府県名検索
- 地域検索
- 英語名検索
- 部分一致検索
- 複数地域検索
- 地域一覧取得
- 緯度・経度範囲検索
- 地域ごとの件数取得

Spring Data JPAのメソッド名規約と `@Query` を利用して検索処理を実装

---

### 2. WeatherRecordRepository
天気記録を操作するRepositoryを実装

主な処理:

- 都道府県ごとの天気記録取得
- 最新の天気記録取得
- 日時範囲検索
- 気温範囲検索
- 天気コード検索
- 古い天気記録の削除
- 都道府県ごとの記録件数取得
- DailyForecastを含めた取得

関連データをまとめて取得するため、`JOIN FETCH` を利用する設計について学んだ。

---

### 3. DailyForecastRepository

日次予報を操作するRepositoryを実装

主な処理:

- WeatherRecordごとの予報取得
- 予報日検索
- 日付範囲検索
- 天気コード検索
- 最高・最低気温による検索
- 降水量による検索
- 都道府県と日付範囲による検索

---

## Spring Data JPAで学んだこと
### JpaRepository

`JpaRepository<Entity, ID型>` を継承することで、以下のような基本的なCRUD処理を自分で実装せずに利用できる

- `save()`
- `findById()`
- `findAll()`
- `delete()`
- `count()`

### メソッド名によるクエリ生成

Repositoryのメソッド名からSpring Data JPAが自動的にクエリを生成できる。

例:

```java
findByName(String name)
findByRegionOrderById(String region)
findByFetchedAtBetweenOrderByFetchedAtDesc(...)
```

### @Query

メソッド名だけでは表現しにくい検索処理では、`@Query` を使用してJPQLやネイティブSQLを記述できることを学んだ。

---

## Repositoryテスト

Repository層の動作確認には `@DataJpaTest` を使用した。
本番のMySQLを直接使用せず、テスト専用のH2データベースを利用した。

### テスト環境
`build.gradle` にH2を追加

```gradle
testRuntimeOnly 'com.h2database:h2'
```

テスト専用設定として以下を作成

```text
src/test/resources/application.properties
```

H2ではEntityからテーブルを自動作成し、テスト終了後に削除するよう設定

### テストデータ

```text
src/test/resources/test-data.sql
```

に47都道府県のテストデータを用意し、`@Sql` を使用してテスト時に投入

---

## テスト結果

以下の処理についてRepositoryテストを実施

- 全件取得
- ID検索
- 存在しないIDの検索
- 都道府県名検索
- 地域検索
- 部分一致検索
- 複数地域検索
- 地域一覧取得
- 保存
- 更新
- 削除

最終的に、

```text
BUILD SUCCESSFUL
```

となり、全テストが成功した

---

## 発生した問題と解決方法
### 1. Repositoryのメソッド名変更によるコンパイルエラー

`PrefectureRepository` の地域検索メソッドを

```java
findByRegion(String region)
```

から

```java
findByRegionOrderById(String region)
```

へ変更したため、既存の `PrefectureTestController` が古いメソッドを呼び出してコンパイルエラーになった。

Controller側も新しいメソッド名に変更することで解決した。

### 2. test-data.sqlの文字化け

Repositoryテストで、

```text
expected: "東京都"
but was: "譚ｱ莠ｬ驛ｽ"
```

となり、日本語データが文字化けした。

原因は `test-data.sql` の文字コードとテスト実行時の文字コードが一致していなかったことだった。

以下の設定を追加してUTF-8で読み込むようにした。

```java
@Sql(
    scripts = "/test-data.sql",
    config = @SqlConfig(encoding = "UTF-8")
)
```

これにより日本語データが正しく読み込まれ、テストが成功した。

---

## N+1問題

関連データを取得するときに、最初の1回のSQLに加えて、取得件数分のSQLが追加で実行される問題をN+1問題という。

例えば47都道府県を取得したあと、それぞれの天気情報を個別に取得すると、

```text
1 + 47 = 48回
```

のSQLが実行される可能性がある。

対策として以下を学んだ。

- `JOIN FETCH`
- `@EntityGraph`
- Batch Fetch Size
- 必要のない関連データは `FetchType.LAZY` を使用する

---

## 今回の学び

- `JpaRepository` を使うことでCRUD処理を簡単に実装できる
- メソッド名から検索クエリを自動生成できる
- 複雑な検索では `@Query` を利用できる
- Repositoryテストには `@DataJpaTest` を利用できる
- H2を使うことで本番DBを変更せず安全にテストできる
- テストデータでは文字コードにも注意する必要がある
- Repositoryのメソッド名を変更した場合、呼び出し側の修正も必要
- N+1問題を意識して関連データの取得方法を設計することが重要