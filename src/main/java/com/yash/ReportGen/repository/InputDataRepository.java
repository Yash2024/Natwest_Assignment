package com.yash.ReportGen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yash.ReportGen.Entity.InputData;

public interface InputDataRepository extends JpaRepository<InputData, Long> {
	
	@Query(value = "SELECT i.id, i.field1, i.field2, i.field3, i.field4, i.field5, r.refdata1, r.refdata2, r.refdata3, r.refdata4 " +
	            			"FROM input_data i INNER JOIN reference_data r ON i.refkey1 = r.refkey1 AND i.refkey2 = r.refkey2",
	            			nativeQuery = true)
		List<Object[]> fetchData();
	
}
