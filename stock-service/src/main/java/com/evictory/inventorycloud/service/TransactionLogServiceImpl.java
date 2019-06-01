package com.evictory.inventorycloud.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evictory.inventorycloud.exception.MessageBodyConstraintViolationException;
import com.evictory.inventorycloud.modal.CurrentStock;
import com.evictory.inventorycloud.modal.TransactionDetails;
import com.evictory.inventorycloud.modal.TransactionLog;
import com.evictory.inventorycloud.repository.CurrentStockRepository;
import com.evictory.inventorycloud.repository.TransactionDetailsRepository;
import com.evictory.inventorycloud.repository.TransactionLogRepository;

@Service
public class TransactionLogServiceImpl implements TransactionLogService {

	@Autowired
	TransactionLogRepository transactionLogRepository;
	@Autowired
	CurrentStockRepository currentStockRepository;
	@Autowired
	TransactionDetailsRepository transactionDetailsRepository;

	// boolean foundCurrentStock = false;

	
	@Override
	public List<CurrentStock> currentStockByDate(String date) { // 2019-02-02T21:44:43+05:30"
		final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
		final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date givenDate = null;
		List<CurrentStock> currentStocks= currentStockRepository.findAll();
		try {
			givenDate = format.parse(date);
			Date transactionDate = null;
			
			List<TransactionLog> transactionlogs= transactionLogRepository.findAll();
			List<TransactionDetails> transactiondetails = new ArrayList<TransactionDetails>();
			for (int i = 0; i < transactionlogs.size(); i++) {
				String logdate = transactionlogs.get(i).getDate().format(dateTimeFormatter);
				transactionDate = dateFormat.parse(logdate);
				if(givenDate.before(transactionDate) || givenDate.equals(transactionDate)) {
					transactiondetails.addAll(transactionlogs.get(i).getTransactionDetails());
					System.out.println("Received Dates from db " + transactionDate + " " + transactiondetails.size());	
				}	
			}
			
			String type = "";
			for (TransactionDetails transactiondetail : transactiondetails) {
				for (CurrentStock currentStock : currentStocks) {
					if(transactiondetail.getUomId() == currentStock.getUomId() && transactiondetail.getItemId() == currentStock.getItemId() && transactiondetail.getBrandId() == currentStock.getBrandId()) {
						for(TransactionLog tlogs : transactionlogs) {
							if(tlogs.getId() == transactiondetail.getTransactionlog().getId()) {
								type = tlogs.getType();
								System.out.println(type);
							}
							
						}
						if(type.equals("issue")) {
							System.out.println(currentStock.getQuantity() +" "+ transactiondetail.getQuantity());
							currentStock.setQuantity(currentStock.getQuantity() + transactiondetail.getQuantity());
							System.out.println(currentStock.getQuantity());
						}
						else if(type.equals("recieve")) {
							System.out.println(currentStock.getQuantity() +" "+ transactiondetail.getQuantity());
							currentStock.setQuantity(currentStock.getQuantity() - transactiondetail.getQuantity());
							System.out.println(currentStock.getQuantity());
						}
					}
				}
				System.out.println(transactiondetail.getId());
			}
			
			System.out.println(givenDate);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		
		
		return currentStocks;
	}
	
	@Override
	public Boolean save(TransactionLog transactionLog) {

		String recieve = "recieve";
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
					if (transactionLog.getType().equals(recieve)) {
						CurrentStock currentStock = new CurrentStock();
						currentStock.setItemId(transactionDetails.getItemId());
						currentStock.setUomId(transactionDetails.getUomId());
						currentStock.setBrandId(transactionDetails.getBrandId());
						currentStock.setQuantity(transactionDetails.getQuantity());
						currentStockRepository.save(currentStock);
					} else if(transactionLog.getType().equals(issue)){
						throw new MessageBodyConstraintViolationException("Not enough stocks");
					}
				}

				else if (currentStockoptional.isPresent()) {
					CurrentStock editedCurrentStock = new CurrentStock();
					editedCurrentStock = currentStockoptional.get();
					if (transactionLog.getType().equals(recieve)) {
						
						editedCurrentStock.setQuantity(editedCurrentStock.getQuantity() + transactionDetails.getQuantity());
						currentStockRepository.save(editedCurrentStock);
					} else if(transactionLog.getType().equals(issue)) {
						Double qty = 0.0;
						qty = editedCurrentStock.getQuantity() - transactionDetails.getQuantity();
						if (qty < 0.0) {
							throw new MessageBodyConstraintViolationException("Not enough stocks");
						} else {
							editedCurrentStock.setQuantity(editedCurrentStock.getQuantity() - transactionDetails.getQuantity());
							currentStockRepository.save(editedCurrentStock);
						}

					}
					else {
						throw new MessageBodyConstraintViolationException("Incorrect Transaction Type");
					}
				}
				else {
					if (transactionLog.getType().equals(recieve)) {
						CurrentStock currentStockNew = new CurrentStock();
						currentStockNew.setItemId(transactionDetails.getItemId());
						currentStockNew.setUomId(transactionDetails.getUomId());
						currentStockNew.setBrandId(transactionDetails.getBrandId());
						currentStockNew.setQuantity(transactionDetails.getQuantity());
						currentStockRepository.save(currentStockNew);
					} else if(transactionLog.getType().equals(issue)){
						throw new MessageBodyConstraintViolationException("Not enough stocks");
					}
					else {
						throw new MessageBodyConstraintViolationException("Incorrect Transaction Type");
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