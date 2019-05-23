package com.evictory.inventorycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.evictory.inventorycloud.modal.TransactionLog;

public interface TransactionLogRepository  extends JpaRepository<TransactionLog, Integer>{

}