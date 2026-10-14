# Day 5 学習まとめ

## 学んだこと

### Spring Bootの仕組み
- @SpringBootApplicationの3つの役割
- DIコンテナの仕組み
- 自動設定の動作原理

### アノテーション
- @Controller, @Service, @Repository, @Component
- @GetMapping, @PostMapping
- @RequestParam, @PathVariable
- @ResponseBody

### Modelの使い方
- 基本データ型の渡し方
- リスト、Mapの渡し方
- オブジェクトの渡し方

### application.properties
- サーバー設定
- データベース設定
- JPA設定
- ログ設定
- プロファイル別設定

### ロギング
- SLF4Jの使い方
- ログレベルの使い分け
- Lombokの@Slf4j

## 成果物

1. spring-boot-architecture.md
2. PracticeController.java
3. ModelPracticeController.java
4. LoggingController.java
5. 各種テンプレートファイル
6. application-dev.properties
7. application-prod.properties
8. application-properties-guide.md

## 感想・気づき
PracticeController.javaの
return "{\"message\": \"Hello JSON\"}";で、
JSON文字列をJavaで返す際に、ダブルクォートをそのまま書いてコンパイルエラーになった。Javaの文字列内でダブルクォートを使う場合は、
\" のようにエスケープする必要があることを学んだ。

## 明日への準備

- Open-Meteo APIの公式ドキュメントを読む
- 要件定義の書き方を調べる


1.Spring Bootのどの機能が最も便利だと思いましたか？
@SpringBootApplication による自動設定が便利だと思いました

2.アノテーションの使い分けで困ったことは？
アノテーションの役割だけでなく書き方も理解する必要があると感じました。

3.application.propertiesで設定したい項目は他にありますか？
特にありません。今は基本的な設定項目を理解することを優先したいです。

4.明日からの開発で活用したい知識は？
エラーが発生したときにログを確認して原因を探すことも意識したいです。