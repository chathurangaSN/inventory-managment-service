package com.evictory.inventorycloud.service;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.aspectj.apache.bcel.classfile.Module.Open;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.evictory.inventorycloud.exception.MessageBodyConstraintViolationException;
import com.evictory.inventorycloud.modal.DraftLog;
import com.evictory.inventorycloud.modal.Stock;
import com.evictory.inventorycloud.modal.StockDetails;
import com.evictory.inventorycloud.modal.TransactionDetails;
import com.evictory.inventorycloud.modal.TransactionLog;
import com.evictory.inventorycloud.modal.DraftDetails;
import com.evictory.inventorycloud.repository.CurrentStockRepository;
import com.evictory.inventorycloud.repository.DraftDetailsRepository;
import com.evictory.inventorycloud.repository.DraftLogRepository;
import com.evictory.inventorycloud.repository.StockRepository;
import com.evictory.inventorycloud.repository.TransactionLogRepository;

@Service
public class StockServiceImpl implements StockService {

	@Autowired
	DraftLogRepository draftLogRepository;

	@Autowired
	DraftDetailsRepository draftDetailsRepository;

	@Autowired
	StockRepository stockRepository;
	
	@Autowired
	TransactionLogRepository transactionLogRepository;
	
	@Autowired
	CurrentStockRepository currentStockRepository;

	@Override
	public Boolean saveAll(DraftLog draftLog) { // save all stock details with log
	
		if (draftLog == null) {
			throw new MessageBodyConstraintViolationException("Response body is empty");
		} else {
			List<DraftDetails> details = draftLog.getDraftDetails();
			for (int i = 0; i < details.size(); i++) {
				if (details.get(i).getItemId() == null || details.get(i).getQuantity() == null
						|| details.get(i).getBrandId() == null || details.get(i).getUomId() == null) {
					throw new MessageBodyConstraintViolationException("Please provide all open stock details.");
				} else if (details.get(i).getItemId() < 1 || details.get(i).getBrandId() < 1
						|| details.get(i).getUomId() < 1) {
					throw new MessageBodyConstraintViolationException("Please provide all open stock details.");
				}
			}
			System.out.println("Get user name " + draftLog.getUserId());
			for (DraftDetails draftDetails : draftLog.getDraftDetails()) {
				draftDetails.setDraftLog(draftLog);
				System.out.println("dasf" + draftLog.getDraftDetails());
			}
			draftLogRepository.save(draftLog);
			return true;
		}
	}

	@Override
	public List<DraftLog> fetchAll() { // get all stock details with log
		
		return draftLogRepository.findAll();
	}

	@Override
	public Boolean saveEntry(DraftLog draftLog) { // save only stock log

		if (draftLog == null) {
			throw new MessageBodyConstraintViolationException("Response body is empty");
		} else {
			draftLogRepository.save(draftLog);
			return true;
		}
	}

	@Override
	public Boolean updateEntry(Integer id, DraftLog draftLog) { // update stock log // pass id of stock log

		boolean isExist = draftLogRepository.existsById(id);
		if (isExist) {
			Optional<DraftLog> optional = draftLogRepository.findById(id);
			DraftLog update = optional.get();
			update.setReason(draftLog.getReason());
			update.setUserId(draftLog.getUserId());

			draftLogRepository.save(update);
			return true;
		} else {
			throw new MessageBodyConstraintViolationException("Stock log entry not available.");
		}
	}

	@Override
	public DraftLog fetchEntry(Integer id) { // get stock log // pass id of stock log
		
		boolean isExist = draftLogRepository.existsById(id);
		if (isExist) {
			System.out.println("have");
			Optional<DraftLog> optional = draftLogRepository.findById(id);
			DraftLog draftLog = optional.get();
			return draftLog;
		} else {
			throw new MessageBodyConstraintViolationException("Stock log entry not available.");
		}
	}

	@Override
	public Boolean deleteEntry(Integer id) { // delete stock log // pass id of stock log
		
		boolean isExist = draftLogRepository.existsById(id);
		if (isExist) {
			System.out.println("have");
			draftLogRepository.deleteById(id);
			return true;
		} else {
			throw new MessageBodyConstraintViolationException("Stock log entry not available.");
		}
	}

	@Override
	public Boolean saveDetails(Integer id, DraftDetails details) { // create stock details for respective stock log //
																	// pass id of stock log
		boolean isExist = draftLogRepository.existsById(id);
		if (isExist) {
			Optional<DraftLog> optional = draftLogRepository.findById(id);
			DraftLog draftLog = optional.get();
			details.setDraftLog(draftLog);
			draftDetailsRepository.save(details);
			return true;
		} else {
			throw new MessageBodyConstraintViolationException("Stock log entry not available.");
		}

	}

	@Override
	public Boolean updateDetails(Integer id, DraftDetails details) { // update stock details for respective stock log //
																		// pass id of stock details
		boolean isExist = draftDetailsRepository.existsById(id);
		System.out.println("isExist" + isExist);
		if (isExist) {
			Optional<DraftDetails> optional = draftDetailsRepository.findById(id);
			DraftDetails draftDetails = optional.get();
			draftDetails.setItemId(details.getItemId());
			draftDetails.setQuantity(details.getQuantity());
			draftDetails.setBrandId(details.getBrandId());
			draftDetails.setUomId(details.getUomId());

			draftDetailsRepository.save(draftDetails);
			return true;
		} else {
			throw new MessageBodyConstraintViolationException("Stock details entry not available.");
		}

	}

	@Override
	public Boolean deleteDetails(Integer id) { // delete stock details // pass id of stock details

		boolean isExist = draftDetailsRepository.existsById(id);
		if (isExist) {
			draftDetailsRepository.deleteById(id);
			return true;
		} else {
			throw new MessageBodyConstraintViolationException("Stock details entry not available.");
		}

	}
	@Override
	public List<DraftDetails> fetchAllDetails(Integer id) { // fetch all stock details by stock log // pass id of stock details
		
		boolean isExist = draftLogRepository.existsById(id);
		if (isExist) {
			Optional<DraftLog> optional = draftLogRepository.findById(id);
			if (optional.isPresent()) {
				
				List<DraftDetails> details = optional.get().getDraftDetails();
				return details;
			}else {
				throw new MessageBodyConstraintViolationException("Draft Details are not available.");
			}
			
		} else {
			throw new MessageBodyConstraintViolationException("Stock log entry not available.");
		}
	}

	@Override
	public Boolean deleteAllDetails(Integer id) { // delete all stock details for stock log // pass stock log id
		
		boolean isExist = draftLogRepository.existsById(id);
		if (isExist) {
			Optional<DraftLog> optional = draftLogRepository.findById(id);
			if (optional.isPresent()) {
				Integer gotId = 0;
				for (int i = 0; i < optional.get().getDraftDetails().size(); i++) {
					gotId = optional.get().getDraftDetails().get(i).getId();

					System.out.println("sdasfdfsd  " + gotId);
					draftDetailsRepository.deleteById(gotId);
//					(optional.get().getStockDetails().get(i));
				}

			}
			return true;
		} else {
			throw new MessageBodyConstraintViolationException("Stock log entry not available.");
		}
	}

	@Override
	public Boolean saveToMaster(Integer id) { // fetch all draft log entry details and push it as a new entry to stock
												// log and delete if existing draft log
		boolean isExist = draftLogRepository.existsById(id);
		System.out.println(isExist);
		if (isExist) {
			Optional<DraftLog> optional = draftLogRepository.findById(id);
			DraftLog draftLog = optional.get();
			Stock stock = new Stock();
			stock.setDate(draftLog.getDate());
			stock.setReason(draftLog.getReason());
			stock.setUserId(draftLog.getUserId());
			List<StockDetails> stockDetails = new ArrayList<StockDetails>();

			for (int i = 0; i < draftLog.getDraftDetails().size(); i++) {
				StockDetails details = new StockDetails();
				details.setBrandId(draftLog.getDraftDetails().get(i).getBrandId());
				details.setItemId(draftLog.getDraftDetails().get(i).getItemId());
				details.setQuantity(draftLog.getDraftDetails().get(i).getQuantity());
				details.setUomId(draftLog.getDraftDetails().get(i).getUomId());
				stockDetails.add(details);
			}
			stock.setStockDetails(stockDetails);

			for (StockDetails details : stock.getStockDetails()) {
				details.setStock(stock);

			}
			stockRepository.save(stock);
			draftLogRepository.deleteById(id);
			return true;
		} else {
			throw new MessageBodyConstraintViolationException("Stock details entry not available.");
		}
	}

	@Override
	public List<Stock> fetchAllMaster() { // fetch all permanent added stock entries with details

		return stockRepository.findAll();
	}

	@Override
	public Stock fetchMaster(Integer id) { // fetch permanent added stock entries with details by id

		boolean isExist = stockRepository.existsById(id);
		if (isExist) {
			Optional<Stock> optional = stockRepository.findById(id);
			Stock stock = optional.get();
			return stock;
		} else {
			throw new MessageBodyConstraintViolationException("Stock log entry is not available.");
		}
	}

	@Override
	public Stock fetchMasterLastEntry(String date) { // 2019-05-23T21:44:43+05:30"
		
		Integer id = 0;
		final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
		final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date passedDate = null;
		try {
			passedDate = format.parse(date);

			System.out.println(passedDate);

			List<Stock> stocks = stockRepository.findAll();
			List<Date> recivedDates = new ArrayList<Date>();

			for (int i = 0; i < stocks.size(); i++) {
				String stockDate = stocks.get(i).getDate().format(dateTimeFormatter);
				Date getDateOfStock = dateFormat.parse(stockDate);
				recivedDates.add(getDateOfStock);
				System.out.println("Recived Dates from db " + getDateOfStock);
			}

			Date nearestIdentifiedDate = findNearest(recivedDates, passedDate);

			for (int i = 0; i < stocks.size(); i++) {
				Date checkDate = dateFormat.parse(stocks.get(i).getDate().format(dateTimeFormatter));
				System.out.println("checkedDate ="+ checkDate);
				if (checkDate.equals(nearestIdentifiedDate)) {
					id = stocks.get(i).getId();
					System.out.println("Nearest selected  id = " +id);
				}
			}
			
			System.out.println("Nearest  id = " +id);
			Optional<Stock> optional = stockRepository.findById(id);
			if (optional.isPresent()) {
				return optional.get();
			} else {
				return null;
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
		}

		
		return null;
	}
	
	// get a list of dates before the sent date then pass it to find the nearest date.
	public Date findNearest(List<Date> dates, Date targetDate) {  
		
		List<Date> newFilteredDates = new ArrayList<Date>();
		for (int i = 0; i < dates.size(); i++) {
			if (dates.get(i).compareTo(targetDate) == 0) {
				System.out.println("nearest = " + dates.get(i));
				return dates.get(i);
			} else {
				if (dates.get(i).before(targetDate)) {
					System.out.println("Added Values to new list "+ dates.get(i));
					newFilteredDates.add(dates.get(i));
				}
			}
		}
		return getNearestDate(newFilteredDates, targetDate);

	}

	public Date getNearestDate(List<Date> dates, Date targetDate) {
		Date nearestDate = null;
		
		long prevDiff = -1;
		long targetTS = targetDate.getTime();
		for (int i = 0; i < dates.size(); i++) {
			Date date = dates.get(i);
			long currDiff = Math.abs(date.getTime() - targetTS);
			if (prevDiff == -1 || currDiff < prevDiff) {
				prevDiff = currDiff;
				nearestDate = date;
			}
		}
		System.out.println("Nearest Date: " + nearestDate);
		return nearestDate;

	}

	@Override
	public ResponseEntity<?> fetchStockMovementReport(String date, Integer itemId, Integer uomId, Integer brandId) {
		final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
		final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		
		Stock lastOpenStock = this.fetchMasterLastEntry(date);
		if(lastOpenStock == null) {
			throw new MessageBodyConstraintViolationException("Stock log entry not available.");
		}
		ZonedDateTime lastOpenStockDate = lastOpenStock.getDate();
	
		
		try {
			
			
			Date getlastDate = dateFormat.parse(lastOpenStockDate.format(dateTimeFormatter));
			
			List<TransactionLog> logs = transactionLogRepository.findAll();
			
			System.out.println(getlastDate);

			

			
			List<TransactionLog> newFilteredAfterDates = new ArrayList<TransactionLog>();
			
			for (int i = 0; i < logs.size(); i++) { // sort the transactions after open stock last date
				Date getDateOfStock = dateFormat.parse(logs.get(i).getDate().format(dateTimeFormatter));
				if (getDateOfStock.compareTo(getlastDate) == 0) {
					System.out.println("nearest = " + getDateOfStock);
				
				} else {
					if (getDateOfStock.after(getlastDate)) {
						System.out.println("Added Values to new list "+ getDateOfStock);
						newFilteredAfterDates.add(logs.get(i));
					}
				}
			}
			

			List<TransactionLog> newFilteredAfterItemSort = new ArrayList<TransactionLog>();
			
			for (int i = 0; i < newFilteredAfterDates.size(); i++) { // sort buy item, brand and umo id
				
				List<TransactionDetails> transactionDetails = newFilteredAfterDates.get(i).getTransactionDetails();
				
				for (int j = 0; j < transactionDetails.size(); j++) {
					if(transactionDetails.get(j).getItemId() == itemId && transactionDetails.get(j).getUomId() == uomId 
							&& transactionDetails.get(j).getBrandId() == brandId ) {
						TransactionLog transactionLog = new TransactionLog();
						List<TransactionDetails> details = new ArrayList<TransactionDetails>();
						transactionLog.setId(newFilteredAfterDates.get(i).getId());
						transactionLog.setDate(newFilteredAfterDates.get(i).getDate());
						transactionLog.setType(newFilteredAfterDates.get(i).getType());
						transactionLog.setUserId(newFilteredAfterDates.get(i).getUserId());
						details.add(transactionDetails.get(j));
						transactionLog.setTransactionDetails(details);
						newFilteredAfterItemSort.add(transactionLog);
					}
				}
				
			}
			List<TransactionLog> transactionLogsIssue = new ArrayList<TransactionLog>();
			List<TransactionLog> transactionLogsRecived = new ArrayList<TransactionLog>();
			
			for (int j = 0; j < newFilteredAfterItemSort.size(); j++) {
				switch (newFilteredAfterItemSort.get(j).getType()) {
				case "issue":
					transactionLogsIssue.add(newFilteredAfterItemSort.get(j));
					break;
				case "receive":
					transactionLogsRecived.add(newFilteredAfterItemSort.get(j));
					break;

				default:
					break;
				}
			}
			Stock stock  = new Stock();
			for (int j = 0; j < lastOpenStock.getStockDetails().size(); j++) {
				if(lastOpenStock.getStockDetails().get(j).getItemId() == itemId 
						&& lastOpenStock.getStockDetails().get(j).getUomId() == uomId 
						&& lastOpenStock.getStockDetails().get(j).getBrandId() == brandId ) {
//					Stock stock  = new Stock();
					List<StockDetails> details = new ArrayList<StockDetails>();
					stock.setId(lastOpenStock.getId());
					stock.setDate(lastOpenStock.getDate());
					stock.setReason(lastOpenStock.getReason());
					stock.setUserId(lastOpenStock.getUserId());
					details.add(lastOpenStock.getStockDetails().get(j));
					stock.setStockDetails(details);
					
				}
			}
			
			StockMovementResponse stockMovementResponse = new StockMovementResponse();
			stockMovementResponse.setResponse("Sucess");
			stockMovementResponse.setStock(stock);
			stockMovementResponse.setTransactionLogsIssue(transactionLogsIssue);
			stockMovementResponse.setTransactionLogsRecived(transactionLogsRecived);
			
			return new ResponseEntity<>(stockMovementResponse, HttpStatus.ACCEPTED);
			
			
		} catch (ParseException ex) {
			ex.printStackTrace();
		}

		Response response = new Response();
		response.setResponse("Failed");
		response.setMessage("Failed to withdraw from database.");
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	class Response {

		private String response;
		private String message;

		public String getResponse() {
			return response;
		}

		public void setResponse(String response) {
			this.response = response;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

	}
	
	class StockMovementResponse{
	   	 
    	private String response;
//    	private String message;
    	private Stock stock;
    	
    	private List<TransactionLog> transactionLogsIssue;
    	
    	private List<TransactionLog> transactionLogsRecived;

		public String getResponse() {
			return response;
		}

		public void setResponse(String response) {
			this.response = response;
		}

		public Stock getStock() {
			return stock;
		}

		public void setStock(Stock stock) {
			this.stock = stock;
		}

		public List<TransactionLog> getTransactionLogsIssue() {
			return transactionLogsIssue;
		}

		public void setTransactionLogsIssue(List<TransactionLog> transactionLogsIssue) {
			this.transactionLogsIssue = transactionLogsIssue;
		}

		public List<TransactionLog> getTransactionLogsRecived() {
			return transactionLogsRecived;
		}

		public void setTransactionLogsRecived(List<TransactionLog> transactionLogsRecived) {
			this.transactionLogsRecived = transactionLogsRecived;
		}
    	
    	
    	
		
    	
    	
    }
}
