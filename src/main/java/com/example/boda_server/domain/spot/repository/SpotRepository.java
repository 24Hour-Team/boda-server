package com.example.boda_server.domain.spot.repository;

import com.example.boda_server.domain.spot.entity.Spot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SpotRepository extends JpaRepository<Spot, Long> {
    Optional<Spot> findByNameAndCityName(String name, String cityName);
//    Page<Spot> findByNameContaining(String name, Pageable pageable);
    // 풀텍스트 인덱스 적용 후 검색 쿼리 개선
    @Query(value = "SELECT * FROM spot WHERE MATCH(name) AGAINST(:name)",
            countQuery = "SELECT COUNT(*) FROM spot WHERE MATCH(name) AGAINST(:name)",
            nativeQuery = true)
    Page<Spot> searchByName(@Param("name") String name, Pageable pageable);
}
