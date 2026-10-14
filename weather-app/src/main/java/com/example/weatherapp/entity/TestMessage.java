package com.example.weatherapp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/*
Lombokアノテーションの説明:
@Data: getter/setter/toString/equals/hashCodeを自動生成
@NoArgsConstructor: 引数なしコンストラクタ
@AllArgsConstructor: 全フィールドを引数に持つコンストラクタ
@Builder: ビルダーパターンを自動生成
 */

@Entity
@Table(name = "test_messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestMessage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
