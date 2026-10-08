# 環境構築完了レポート



## インストール済みツール



### JDK

\- バージョン: 17.0.20.1

\- インストールパス: C:\\Program Files\\Eclipse Adoptium\\jdk-17.0.20.101-hotspot



### IntelliJ IDEA

\- バージョン: 2026.2.3

\- 使用JDK: Eclipse Temurin 17.0.20

\- インストール済みプラグイン:

&#x20; - Rainbow Brackets

&#x20; - VS Code Keymap



### Git / GitHub（Day 1で設定済み）

\- Gitのバージョン:  2.49.0.windows.1

\- GitHub CLI（gh）のバージョン: 2.102.0 

\- GitHubのユーザー名: black1000

\- リポジトリのURL: https://github.com/black1000/java-training-template



### MySQL

\- バージョン: 8.0.46

\- ポート: 3306

\- データベース: weather\_app



### Postman

\- バージョン: 12.31.3

\- テスト結果: 成功（Open-Meteo API / 200 OK）



### Chrome

\- バージョン: 155.0.8059.40（64ビット）

\- 研修で使うサイトへの接続:

&#x20; - Spring Initializr: OK

&#x20; - Open-Meteo API: OK

&#x20; - diagrams.net: OK

&#x20; - Maven Central: OK



## 動作確認

\- \[x] Java実行確認

\- \[x] IntelliJ IDEA起動確認

\- \[x] Git操作確認（git --version、gh auth status）

\- \[x] MySQL接続確認

\- \[x] Postman動作確認

\- \[x] Chromeと研修で使うサイトへの接続確認



## 問題と解決策

\- JDK 24が優先されていたため、JDK 17を追加でインストールし、JAVA\_HOMEとPATHをJDK 17に設定した。

\- mysqlコマンドがGit Bashで認識されなかったため、MySQLのbinフォルダをWindowsのPATHに追加した。



## 完了日時

2026/10/08

