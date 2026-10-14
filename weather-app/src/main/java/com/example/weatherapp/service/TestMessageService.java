package com.example.weatherapp.service;

import com.example.weatherapp.entity.TestMessage;
import com.example.weatherapp.repository.TestMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
@RequiredArgsConstructor: finalフィールドのコンストラクタを自動生成（DI）
@Transactional(readOnly = true): 読み取り専用トランザクション
@Transactional: 書き込み可能トランザクション
 */

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestMessageService {
    
    private final TestMessageRepository repository;
    
    public List<TestMessage> getAllMessages() {
        return repository.findAll();
    }
    
    @Transactional
    public TestMessage createMessage(String messageText) {
        TestMessage message = TestMessage.builder()
            .message(messageText)
            .build();
        return repository.save(message);
    }
}