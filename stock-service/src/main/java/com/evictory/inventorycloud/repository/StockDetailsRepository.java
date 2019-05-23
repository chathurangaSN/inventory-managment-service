package com.evictory.inventorycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evictory.inventorycloud.modal.StockDetails;


public interface StockDetailsRepository extends JpaRepository<StockDetails, Integer>{

}
