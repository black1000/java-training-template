package com.example.weatherapp.mapper;

import com.example.weatherapp.dto.PrefectureDto;
import com.example.weatherapp.entity.Prefecture;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Prefecture ↔ PrefectureDto 変換
 */
@Component
public class PrefectureMapper {
    
    /**
     * Entity → DTO
     */
    public PrefectureDto toDto(Prefecture entity) {
        if (entity == null) {
            return null;
        }
        
        return PrefectureDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .nameEn(entity.getNameEn())
            .latitude(entity.getLatitude())
            .longitude(entity.getLongitude())
            .region(entity.getRegion())
            .build();
    }
    
    /**
     * Entity List → DTO List
     */
    public List<PrefectureDto> toDtoList(List<Prefecture> entities) {
        return entities.stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }
    
    /**
     * DTO → Entity（新規作成時）
     */
    public Prefecture toEntity(PrefectureDto dto) {
        if (dto == null) {
            return null;
        }
        
        return Prefecture.builder()
            .id(dto.getId())
            .name(dto.getName())
            .nameEn(dto.getNameEn())
            .latitude(dto.getLatitude())
            .longitude(dto.getLongitude())
            .region(dto.getRegion())
            .build();
    }
}