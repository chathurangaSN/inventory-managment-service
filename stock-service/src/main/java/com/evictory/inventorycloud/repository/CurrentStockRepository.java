package com.evictory.inventorycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evictory.inventorycloud.modal.CurrentStock;

public interface CurrentStockRepository extends JpaRepository<CurrentStock, Integer> {

}
