package com.evictory.inventorycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evictory.inventorycloud.modal.TransactionDetails;



public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Integer> {

}
