package com.example.jt808.repository;

import com.example.jt808.domain.LocationRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRecordRepository extends JpaRepository<LocationRecord, Long> {
    List<LocationRecord> findTop50ByOrderByCreatedAtDesc();
}
