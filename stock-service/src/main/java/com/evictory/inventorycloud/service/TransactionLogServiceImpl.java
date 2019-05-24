package com.evictory.inventorycloud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evictory.inventorycloud.exception.MessageBodyConstraintViolationException;
import com.evictory.inventorycloud.modal.CurrentStock;
import com.evictory.inventorycloud.modal.TransactionDetails;
import com.evictory.inventorycloud.modal.TransactionLog;
import com.evictory.inventorycloud.repository.CurrentStockRepository;
import com.evictory.inventorycloud.repository.TransactionLogRepository;

@Service
public class TransactionLogServiceImpl implements TransactionLogService {

	@Autowired
	TransactionLogRepository transactionLogRepository;
	@Autowired
	CurrentStockRepository currentStockRepository;

	// boolean foundCurrentStock = false;

	@Override
	public Boolean save(TransactionLog transactionLog) {

		String issue = "issue";
		if (transactionLog == null) {
			throw new NullPointerException("Response body is empty");
		} else {
			for (TransactionDetails transactionDetails : transactionLog.getTransactionDetails()) {
				transactionDetails.setTransactionlog(transactionLog);

				List<CurrentStock> currentStocks = currentStockRepository.findAll();

				Optional<CurrentStock> currentStockoptional = Optional.empty();
				for (CurrentStock currentStock : currentStocks) {
					if (currentStock.getItemId() == transactionDetails.getItemId()
							&& currentStock.getUomId() == transactionDetails.getUomId()
							&& currentStock.getBrandId() == transactionDetails.getBrandId()) {
						currentStockoptional = Optional.of(currentStock);
					}
				}
				if (currentStocks == null || currentStocks.size() == 0) {
					if (transactionLog.getType().equals(issue)) {
						CurrentStock currentStock = new CurrentStock();
						currentStock.setItemId(transactionDetails.getItemId());
						currentStock.setUomId(transactionDetails.getUomId());
						currentStock.setBrandId(transactionDetails.getBrandId());
						currentStock.setQuantity(transactionDetails.getQuantity());
						currentStockRepository.save(currentStock);
					} else {
						throw new MessageBodyConstraintViolationException("Not enough stocks");
					}
				}

				else if (currentStockoptional.isPresent()) {
					CurrentStock cS = new CurrentStock();
					cS = currentStockoptional.get();
					if (transactionLog.getType().equals(issue)) {
						
						cS.setQuantity(cS.getQuantity() + transactionDetails.getQuantity());
						currentStockRepository.save(cS);
					} else {
						Double qty = 0.0;
						qty = cS.getQuantity() - transactionDetails.getQuantity();
						if (qty < 0.0) {
							throw new MessageBodyConstraintViolationException("Not enough stocks");
						} else {
							cS.setQuantity(cS.getQuantity() - transactionDetails.getQuantity());
							currentStockRepository.save(cS);
						}

					}
				}
				else {
					if (transactionLog.getType().equals(issue)) {
						CurrentStock currentStockNew = new CurrentStock();
						currentStockNew.setItemId(transactionDetails.getItemId());
						currentStockNew.setUomId(transactionDetails.getUomId());
						currentStockNew.setBrandId(transactionDetails.getBrandId());
						currentStockNew.setQuantity(transactionDetails.getQuantity());
						currentStockRepository.save(currentStockNew);
					} else {
						throw new MessageBodyConstraintViolationException("Not enough stocks");
					}
				}

			}
		}

		transactionLogRepository.save(transactionLog);
		return true;

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