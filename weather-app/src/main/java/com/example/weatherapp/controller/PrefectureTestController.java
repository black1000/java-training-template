package com.example.weatherapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.weatherapp.entity.Prefecture;
import com.example.weatherapp.repository.PrefectureRepository;

@RestController
@RequestMapping("/api/test")
public class PrefectureTestController {

    @Autowired
    private PrefectureRepository prefectureRepository;

    @GetMapping("/prefectures")
    public List<Prefecture> getAllPrefectures() {
        return prefectureRepository.findAll();
    }

    @GetMapping("/prefectures/region/{region}")
    public List<Prefecture> getByRegion(@PathVariable("region") String region) {
        return prefectureRepository.findByRegion(region);
    }
}