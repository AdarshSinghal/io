package com.adarsh.io.dao;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.adarsh.io.model.dto.Trend;

public interface TrendRepository extends CrudRepository<Trend, Integer> {

	Optional<Trend> findFirstByOrderByTimestampDesc();

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM trend  WHERE _ROWID < ( SELECT maxr FROM  ( SELECT MAX(_ROWID)-:count AS maxr FROM trend ) AS tmp )", nativeQuery = true)
	void deleteOldRecords(@Param("count") Integer count);

}
