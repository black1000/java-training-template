package com.example.weatherapp;

/* @SpringBootApplicationは以下3つのアノテーションの組み合わせ
@SpringBootConfiguration
→ このクラスがSpringの設定クラスであることを示す

@EnableAutoConfiguration
→ 依存関係などを見てSpring Bootが必要な設定を自動で行う

@ComponentScan
→ com.example.weatherapp 以下の
   @Controller / @Service / @Repository / @Component
   などを自動で探して登録する */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeatherAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherAppApplication.class, args);
    }
}