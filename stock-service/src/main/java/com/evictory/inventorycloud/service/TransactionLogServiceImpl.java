package com.evictory.inventorycloud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evictory.inventorycloud.exception.MessageBodyConstraintViolationException;
import com.evictory.inventorycloud.modal.TransactionDetails;
import com.evictory.inventorycloud.modal.TransactionLog;

import com.evictory.inventorycloud.repository.TransactionLogRepository;

@Service
public class TransactionLogServiceImpl implements TransactionLogService {

	@Autowired
	TransactionLogRepository transactionLogRepository;

	@Override
	public Boolean save(TransactionLog transactionLog) {

		if (transactionLog == null) {
			throw new NullPointerException("Response body is empty");
		} else {
			for (TransactionDetails transactionDetails : transactionLog.getTransactionDetails()) {
				transactionDetails.setTransactionlog(transactionLog);
			}
			transactionLogRepository.save(transactionLog);
			return true;
		}
	}

	@Override
	public List<TransactionLog> fetchAll() { // get all stock details with log
		return transactionLogRepository.findAll();
	}

	@Override
	public TransactionLog fetch(Integer id) {
		Optional<TransactionLog> optional = transactionLogRepository.findById(id);

		if (optional.isPresent()) {
			return optional.get();

		} else {
			return null;
		}
	}

	@Override
	public Boolean update(Integer id, TransactionLog transactionLog) {

		if (!transactionLogRepository.existsById(id)) {
			throw new MessageBodyConstraintViolationException("Record does not exist to update");
		} else {

			Optional<TransactionLog> optional = transactionLogRepository.findById(id);
			TransactionLog newTransactionLog = optional.get();
			newTransactionLog.setType(transactionLog.getType());
			newTransactionLog.setUserId(transactionLog.getUserId());
			newTransactionLog.setDate(transactionLog.getDate());
			transactionLogRepository.save(newTransactionLog);
			return true;
		}

	}

	@Override
	public Boolean delete(Integer id) {

		if (!transactionLogRepository.existsById(id)) {
			throw new MessageBodyConstraintViolationException("Record does not exist");
		} else {
			transactionLogRepository.deleteById(id);

			return true;
		}

	}
}

// List<CurrentStock> currentStockList = currentStockRepository.findAll();
// for (int i = 0; i < currentStockList.size(); i++) {
// if (currentStockList.get(i) != null) {
// if (currentStockList.get(i).getId() == id) {
// currentStockList.get(i).setItemId(currentStock.getItemId());
// currentStockList.get(i).setQuantity(currentStock.getQuantity());
// currentStockList.get(i).setTransferRefference(currentStock.getTransferRefference());
// currentStockList.get(i).setUserId(currentStock.getUserId());
// currentStockList.get(i).setDate(currentStock.getDate());
// currentStockRepository.save(currentStockList.get(i));
// return true;
// }
// }
// else {
// throw new MessageBodyConstraintViolationException("Record does not exist to
// update");
// }
// }
// return false;