package com.adarsh.io.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.adarsh.io.model.dto.Stock;

public interface StockRepository extends CrudRepository<Stock, String> {

	Iterable<Stock> findByCodeIn(List<String> codes);

}
