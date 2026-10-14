package com.example.weatherapp.repository;

import com.example.weatherapp.entity.TestMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*JpaRepositoryが提供するメソッド:
save(entity): 保存
findById(id): IDで検索
findAll(): 全件取得
deleteById(id): 削除
count(): 件数取得
*/

@Repository
public interface TestMessageRepository extends JpaRepository<TestMessage, Long> {
}