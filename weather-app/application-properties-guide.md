# Server Configuration
server.port=8080
server.servlet.context-path=/

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/weather_app
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Thymeleaf Configuration
spring.thymeleaf.cache=false
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html

# Logging
logging.level.org.springframework.web=DEBUG
logging.level.com.example.weatherapp=DEBUG
各設定の詳細:

1. サーバー設定

# ポート番号変更
server.port=8080

# コンテキストパス（アプリケーションのルートパス）
server.servlet.context-path=/

# セッションタイムアウト（秒）
server.servlet.session.timeout=1800

# 最大HTTPヘッダーサイズ
server.max-http-header-size=8KB
2. データベース設定

# 接続プール設定
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=20000

# データベース初期化
spring.sql.init.mode=always
spring.sql.init.schema-locations=classpath:schema.sql
spring.sql.init.data-locations=classpath:data.sql
3. JPA/Hibernate設定

# DDL自動生成モード
# none: 何もしない
# validate: スキーマを検証のみ
# update: スキーマを更新（本番非推奨）
# create: 起動時にスキーマを作成（既存データ削除）
# create-drop: 終了時にスキーマを削除
spring.jpa.hibernate.ddl-auto=update

# SQL出力
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# ネーミング戦略
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
4. ログ設定

# ログレベル（TRACE, DEBUG, INFO, WARN, ERROR）
logging.level.root=INFO
logging.level.com.example.weatherapp=DEBUG
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE

# ログファイル出力
logging.file.name=logs/application.log
logging.file.max-size=10MB
logging.file.max-history=30
演習:

環境別の設定ファイルを作成する：

application-dev.properties（開発環境）:

# Development環境設定
server.port=8080
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
logging.level.com.example.weatherapp=DEBUG
application-prod.properties（本番環境）:

# Production環境設定
server.port=80
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
logging.level.com.example.weatherapp=INFO
起動時にプロファイルを指定:

# 開発環境で起動
./gradlew bootRun --args='--spring.profiles.active=dev'

# 本番環境で起動
./gradlew bootRun --args='--spring.profiles.active=prod'