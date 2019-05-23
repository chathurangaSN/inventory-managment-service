package com.evictory.inventorycloud.service;

import java.util.List;

import com.evictory.inventorycloud.modal.TransactionLog;

public interface TransactionLogService {

	Boolean save(TransactionLog transactionLog);

	List<TransactionLog> fetchAll();

	TransactionLog fetch(Integer id);

	Boolean update(Integer id, TransactionLog transactionLog);

	Boolean delete(Integer id);

}