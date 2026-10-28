# トップページのシーケンス図

## 1. 基本フロー（全都道府県表示）

### 処理概要

1. ユーザーがトップページにアクセス
2. データベースから47都道府県を取得
3. 取得した都道府県をトップページに一覧表示

### 参加オブジェクト

* **ユーザー**: トップページを利用する利用者
* **ブラウザ**: ユーザーインターフェース
* **HomeController**: リクエスト処理
* **PrefectureService**: ビジネスロジック
* **PrefectureRepository**: データアクセス
* **Database**: データ永続化

### 詳細フロー

#### 1. リクエスト受付

```java
@GetMapping("/")
public String index(Model model) {
```

#### 2. Service呼び出し

```java
List<PrefectureDto> prefectures =
    prefectureService.getAllPrefectures();
```

#### 3. Repository呼び出し

```java
public List<PrefectureDto> getAllPrefectures() {
    List<Prefecture> entities = prefectureRepository.findAll();

    return entities.stream()
        .map(this::toDto)
        .collect(Collectors.toList());
}
```

#### 4. データベースアクセス

`PrefectureRepository` を通して `prefectures` テーブルから都道府県データを取得する。

イメージ：

```sql
SELECT * FROM prefectures;
```

※ `findAll()` の場合、並び順は明示的には保証されない。ID順が必要な場合は、ソートを指定する。

#### 5. DTO変換

```java
private PrefectureDto toDto(Prefecture entity) {
    return PrefectureDto.builder()
        .id(entity.getId())
        .name(entity.getName())
        .latitude(entity.getLatitude())
        .longitude(entity.getLongitude())
        .region(entity.getRegion())
        .build();
}
```

#### 6. Modelに追加

```java
model.addAttribute("prefectures", prefectures);
return "index";
```

#### 7. Thymeleafレンダリング

```html
<div th:each="pref : ${prefectures}">
    <a th:href="@{/weather/{id}(id=${pref.id})}"
       th:text="${pref.name}"></a>
</div>
```

---

## 2. 地域フィルタフロー（オプション）

### 処理概要

ユーザーが地域を選択した場合、その地域に属する都道府県のみを表示する。

例として「関東」を選択した場合を想定する。

### URLパラメータ

```text
/?region=関東
```

### Controllerでの処理

```java
@GetMapping("/")
public String index(
    @RequestParam(name = "region", required = false) String region,
    Model model
) {
    List<PrefectureDto> prefectures;

    if (region != null && !region.isEmpty()) {
        prefectures =
            prefectureService.getPrefecturesByRegion(region);
    } else {
        prefectures =
            prefectureService.getAllPrefectures();
    }

    model.addAttribute("prefectures", prefectures);
    model.addAttribute("selectedRegion", region);

    return "index";
}
```

### Repositoryメソッド

```java
List<Prefecture> findByRegion(String region);
```

### SQLイメージ

「関東」を指定した場合：

```sql
SELECT *
FROM prefectures
WHERE region = '関東';
```

---

## 3. エラーハンドリングフロー

### エラーケース

1. データベース接続エラー
2. データが0件
3. 予期しない例外

### データベース接続エラー時の流れ

1. ユーザーがトップページへアクセス
2. `HomeController` が `PrefectureService` を呼び出す
3. `PrefectureService` が `PrefectureRepository` を呼び出す
4. データベースへのアクセス時にエラーが発生
5. 例外処理を行う
6. エラー画面をブラウザへ返す
7. ユーザーにエラーメッセージを表示する

### 例外処理例

```java
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataAccessException.class)
    public String handleDataAccessException(
        DataAccessException e,
        Model model
    ) {
        log.error("Database error", e);

        model.addAttribute(
            "errorMessage",
            "データベースエラーが発生しました"
        );

        return "error/500";
    }
}
```

---

## 4. パフォーマンス考察

### データ量

* 都道府県データは47件
* 1リクエストあたり最大47件程度を取得
* データ量は小さく、現時点では大きな負荷は想定されない

### レスポンスタイム目標

設計上の目標値として以下を想定する。

* データベースクエリ: 10ms以内
* DTO変換: 5ms以内
* HTMLレンダリング: 50ms以内
* 合計: 100ms以内

※ 実測値ではなく、現時点での目標値とする。

### 最適化ポイント

1. **キャッシング**

   * 都道府県マスタは変更頻度が低いため、将来的にキャッシュを利用できる。

2. **インデックス**

   * 地域検索を頻繁に行う場合は、`prefectures.region` へのインデックス追加を検討する。

3. **N+1問題**

   * 現在の `Prefecture` 単体取得では関連Entityを使用していないため、N+1問題は発生しない。
