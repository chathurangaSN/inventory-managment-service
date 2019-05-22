package com.evictory.inventorycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evictory.inventorycloud.modal.Stock;

//----- Sahan Part -----

public interface StockRepository extends JpaRepository<Stock, Integer> {

}
