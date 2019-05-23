package com.evictory.inventorycloud.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.aspectj.apache.bcel.classfile.Module.Open;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evictory.inventorycloud.exception.MessageBodyConstraintViolationException;
import com.evictory.inventorycloud.modal.DraftLog;
import com.evictory.inventorycloud.modal.Stock;
import com.evictory.inventorycloud.modal.StockDetails;
import com.evictory.inventorycloud.modal.DraftDetails;
import com.evictory.inventorycloud.repository.DraftDetailsRepository;
import com.evictory.inventorycloud.repository.DraftLogRepository;
import com.evictory.inventorycloud.repository.StockRepository;

@Service
public class StockServiceImpl implements StockService {
	
	@Autowired
	DraftLogRepository draftLogRepository;
	
	@Autowired
	DraftDetailsRepository draftDetailsRepository;
	
	@Autowired
	StockRepository stockRepository;
	
	@Override
	public Boolean saveAll(DraftLog draftLog) { // save all stock details with log
		if(draftLog == null) {
			throw new MessageBodyConstraintViolationException("Response body is empty");
		}else {
			List<DraftDetails> details = draftLog.getDraftDetails();
			for (int i = 0; i < details.size(); i++) {
				if(details.get(i).getItemId() == null || details.get(i).getQuantity() == null 
						|| details.get(i).getBrandId() == null || details.get(i).getUmoId() == null) {
					throw new MessageBodyConstraintViolationException("Please provide all open stock details.");
				}else if (details.get(i).getItemId() < 1 || details.get(i).getBrandId() < 1 
						|| details.get(i).getUmoId() < 1) {
					throw new MessageBodyConstraintViolationException("Please provide all open stock details.");
				}
//				if(openStock.getOpenStockDetails().get(i).getItemId().toString().contains("[0-9]+")) {
//					System.out.println("string frppunt"+ i);
//				}
				
			}
			System.out.println("Get user name "+draftLog.getUser());
			for(DraftDetails draftDetails:draftLog.getDraftDetails()) {
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
	public Boolean saveEntry(DraftLog draftLog) {  // save only stock log
		
		if(draftLog == null) {
			throw new MessageBodyConstraintViolationException("Response body is empty");
		}else {
	        draftLogRepository.save(draftLog);
	        return true;
		}
	}

	@Override
	public Boolean updateEntry(Integer id, DraftLog draftLog) { // update stock log // pass id of stock log
		
		boolean isExist = draftLogRepository.existsById(id);
		if(isExist) { 
			Optional<DraftLog> optional= draftLogRepository.findById(id);
			DraftLog update = optional.get();
			update.setReason(draftLog.getReason());
			update.setUser(draftLog.getUser());
			
			draftLogRepository.save(update);
			return true;
		}else {
			throw new MessageBodyConstraintViolationException("Stock log entry not available.");
		}
	}

	@Override
	public DraftLog fetchEntry(Integer id) {  // get stock log  // pass id of stock log
		boolean isExist = draftLogRepository.existsById(id);
		if(isExist) {
			System.out.println("have");
			Optional<DraftLog> optional= draftLogRepository.findById(id);
			DraftLog draftLog = optional.get();
			return draftLog;
		}else {
			throw new MessageBodyConstraintViolationException("Stock log entry not available.");
		}
	}

	@Override
	public Boolean deleteEntry(Integer id) { // delete stock log  // pass id of stock log
		boolean isExist = draftLogRepository.existsById(id);
		if(isExist) {
			System.out.println("have");
			draftLogRepository.deleteById(id);
			return true;
		}else {
			throw new MessageBodyConstraintViolationException("Stock log entry not available.");
		}
	}

	@Override
	public Boolean saveDetails(Integer id, DraftDetails details) {  // create stock details for respective stock log // pass id of stock log

		boolean isExist = draftLogRepository.existsById(id);
		if(isExist) {
			Optional<DraftLog> optional= draftLogRepository.findById(id);
			DraftLog draftLog = optional.get();
			details.setDraftLog(draftLog);
			draftDetailsRepository.save(details);
			return true;
		}else {
			throw new MessageBodyConstraintViolationException("Stock log entry not available.");
		}
		
	}

	@Override
	public Boolean updateDetails(Integer id, DraftDetails details) {  // update stock details for respective stock log // pass id of stock details
		
		boolean isExist = draftDetailsRepository.existsById(id);
		if(isExist) {
			Optional<DraftDetails> optional= draftDetailsRepository.findById(id);
			DraftDetails draftDetails = optional.get();
			draftDetails.setItemId(details.getItemId());
			draftDetails.setQuantity(details.getQuantity());
			draftDetails.setBrandId(details.getBrandId());
			draftDetails.setUmoId(details.getUmoId());
			
			draftDetailsRepository.save(draftDetails);
			return true;
		}else {
			throw new MessageBodyConstraintViolationException("Stock details entry not available.");
		}
		  
	
	}

	@Override
	public Boolean deleteDetails(Integer id) {  // delete stock details // pass id of stock details
		
		boolean isExist = draftDetailsRepository.existsById(id);
		if(isExist) {
			draftDetailsRepository.deleteById(id);
			return true;
		}else {
			throw new MessageBodyConstraintViolationException("Stock details entry not available.");
		}
		
	}

	@Override
	public Boolean deleteAllDetails(Integer id) { // delete all stock details for stock log // pass stock log id
		boolean isExist = draftLogRepository.existsById(id);
		if(isExist) {
			Optional<DraftLog> optional = draftLogRepository.findById(id);
			if(optional.isPresent()) {
				Integer gotId = 0;
				for (int i = 0; i < optional.get().getDraftDetails().size(); i++) {
					gotId= optional.get().getDraftDetails().get(i).getId();
					
					System.out.println("sdasfdfsd  " +gotId);
					draftDetailsRepository.deleteById(gotId);
//					(optional.get().getStockDetails().get(i));
				}
			
			}
			return true;
		}else {
			throw new MessageBodyConstraintViolationException("Stock log entry not available.");
		}
	}

	@Override
	public Boolean saveToMaster(Integer id) { // fetch all draft log entry details and push it as a new entry to stock log and delete if existing draft log
		boolean isExist = draftLogRepository.existsById(id);
		if(isExist) {
			Optional<DraftLog> optional= draftLogRepository.findById(id);
			DraftLog draftLog = optional.get();
			Stock stock = new Stock();
			stock.setDate(draftLog.getDate());
			stock.setReason(draftLog.getReason());
			stock.setUser(draftLog.getUser());
			List<StockDetails> stockDetails= new ArrayList<StockDetails>();
			
			for (int i = 0; i < draftLog.getDraftDetails().size(); i++) {
				StockDetails details = new StockDetails();
				details.setBrandId(draftLog.getDraftDetails().get(i).getBrandId());
				details.setItemId(draftLog.getDraftDetails().get(i).getItemId());
				details.setQuantity(draftLog.getDraftDetails().get(i).getQuantity());
				details.setUmoId(draftLog.getDraftDetails().get(i).getUmoId());
				stockDetails.add(details);
			}
			stock.setStockDetails(stockDetails);
			
			for(StockDetails details:stock.getStockDetails()) {
				details.setStock(stock);
				
	        }
			stockRepository.save(stock);
			draftLogRepository.deleteById(id);
			return true;
		}else {
			throw new MessageBodyConstraintViolationException("Stock details entry not available.");
		}
	}
	
	
	@Override
	public List<Stock> fetchAllMaster() { // fetch all permanent added stock entries with details
		
		return stockRepository.findAll();
	}
	
	@Override
	public Stock fetchMaster(Integer id) { // fetch  permanent added stock entries with details by id
		
		boolean isExist = stockRepository.existsById(id);
		if(isExist) {
			Optional<Stock> optional= stockRepository.findById(id);
			Stock stock = optional.get();
			return stock;
		}else {
			throw new MessageBodyConstraintViolationException("Stock log entry is not available.");
		}
	}

}
