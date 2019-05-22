package com.evictory.inventorycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evictory.inventorycloud.modal.DraftLog;



public interface DraftLogRepository extends JpaRepository<DraftLog, Integer> {

}
