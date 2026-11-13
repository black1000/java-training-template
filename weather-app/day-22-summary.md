# Day 22 Summary

1. **実装した機能:**
   - HomeController
   - 地域フィルタ機能
   - MockMvcテスト

2. **学んだこと:**
   - Spring MVCの基礎
   - Modelの使い方
   - `@RequestParam` の使い方
   - MockMvcでのテスト方法

3. **困難だった点:**
   - `HelloController` と `HomeController` の両方が `/` にマッピングされ、起動時にエラーが発生した
   - `index.html` と `health.html` のThymeleaf記法や変数名のスペルミスにより、データが正しく表示されなかった
   - Controller側のModel属性名とHTML側の変数名を一致させる必要があることを学んだ

4. **明日への引き継ぎ:**
   - Day 23でWeatherControllerを実装する