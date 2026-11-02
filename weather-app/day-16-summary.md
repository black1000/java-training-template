1. **実装したEntity:**
   - Prefecture
   - WeatherRecord
   - DailyForecast

2. **学んだJPAアノテーション:**
   - @Entity, @Table, @Id, @GeneratedValue
   - @Column, @ManyToOne, @OneToMany
   - @PrePersist, @PreUpdate

3. **困難だった点:**
テストの実行がうまくいかなかった
PrefectureTest.java と WeatherRecordTest.java を src/main/java 配下に作成してしまい、JUnitやAssertJを読み込めずコンパイルエラーになった
テストファイルを src/test/java 配下へ移動することで解決し、./gradlew test が成功した

4. **明日への引き継ぎ:**
   - Repository層の実装準備