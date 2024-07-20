package com.yash.ReportGen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.yash.ReportGen.Entity.ReferenceData;

public interface ReferenceDataRepository extends JpaRepository<ReferenceData, Long> {
}