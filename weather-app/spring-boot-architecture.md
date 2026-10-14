# Spring Boot アーキテクチャ

## 1. MVCパターン

MVCは、アプリケーションの役割を3つに分けて考える設計方法。

- Model: データやビジネスロジックを扱う
- View: 画面表示を担当する
- Controller: リクエストを受け取り、処理結果をViewに渡す

### 処理の流れ

ブラウザ
↓
Controller
↓
Service
↓
Repository
↓
Database

ControllerからViewにデータを渡し、Thymeleafで画面に表示する。

---

## 2. Spring Bootの主要コンポーネント

### @Controller
HTTPリクエストを受け取り、画面表示やレスポンスを制御する。

### @Service
ビジネスロジックを実装する。

### @Repository
データベースへのアクセスを担当する。

### @Entity
データベースのテーブルとJavaクラスを対応させる。

---

## 3. DI（依存性注入）

DIは、必要なオブジェクトを自分で生成せず、Springに用意してもらう仕組み。

### コンストラクタインジェクション

```java
private final TestMessageService testMessageService;

public TestController(TestMessageService testMessageService) {
    this.testMessageService = testMessageService;
}


## 4. Spring Boot アーキテクチャの理解

## @SpringBootApplicationの役割

### @SpringBootConfiguration
- このクラスが設定クラスであることをSpringに伝える
- @Configurationと同じ役割

### @EnableAutoConfiguration
- クラスパスに基づいて自動的にBeanを設定
- 例：MySQLドライバがあれば自動的にDataSourceを設定

### @ComponentScan
- 同じパッケージ以下の@Component、@Service、@Repository、@Controllerを自動検出
- DIコンテナに登録

## DIコンテナとは

Dependency Injection（依存性注入）を管理するコンテナ
- Beanのライフサイクル管理
- 依存関係の自動解決
- シングルトンパターンの実装

## 起動プロセス

1. main()メソッド実行
2. SpringApplication.run()呼び出し
3. コンポーネントスキャン
4. Bean登録
5. 自動設定実行
6. 組み込みTomcat起動
7. アプリケーション起動完了