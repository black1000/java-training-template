package com.example.weatherapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.weatherapp.entity.Prefecture;

@Repository
public interface PrefectureRepository extends JpaRepository<Prefecture, Long> {

    List<Prefecture> findByRegion(String region);

    Prefecture findByName(String name);
}