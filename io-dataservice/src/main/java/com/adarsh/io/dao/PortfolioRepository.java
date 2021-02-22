package com.adarsh.io.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.adarsh.io.model.dto.PortfolioEntry;
import com.adarsh.io.model.dto.PortfolioEntryKey;

public interface PortfolioRepository extends CrudRepository<PortfolioEntry, PortfolioEntryKey> {

	List<PortfolioEntry> findByPid(Long pid);

}
