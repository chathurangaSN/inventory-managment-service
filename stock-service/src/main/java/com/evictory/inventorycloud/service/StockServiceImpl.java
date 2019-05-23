package com.evictory.inventorycloud.service;
import java.util.List;
import java.util.Optional;

import org.aspectj.apache.bcel.classfile.Module.Open;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evictory.inventorycloud.exception.MessageBodyConstraintViolationException;
import com.evictory.inventorycloud.modal.Stock;
import com.evictory.inventorycloud.modal.StockDetails;
import com.evictory.inventorycloud.repository.StockDetailsRepository;
import com.evictory.inventorycloud.repository.StockRepository;

@Service
public class StockServiceImpl implements StockService {
	
	@Autowired
	StockRepository stockRepository;
	
	@Autowired
	StockDetailsRepository stockDetailsRepository;
	
	
	@Override
	public Boolean saveAll(Stock stock) { // save all stock details with log
		if(stock == null) {
			throw new MessageBodyConstraintViolationException("Response body is empty");
		}else {
			List<StockDetails> details = stock.getStockDetails();
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
			System.out.println("Get user name "+stock.getUser());
			for(StockDetails stockDetails:stock.getStockDetails()) {
				stockDetails.setStock(stock);
	            System.out.println("dasf" + stock.getStockDetails());
	        }
	        stockRepository.save(stock);
	        return true;
		}
	}

	@Override
	public List<Stock> fetchAll() { // get all stock details with log
		return stockRepository.findAll();
	}

	@Override
	public Boolean saveEntry(Stock stock) {  // save only stock log
		
		if(stock == null) {
			throw new MessageBodyConstraintViolationException("Response body is empty");
		}else {
	        stockRepository.save(stock);
	        return true;
		}
	}

	@Override
	public Boolean updateEntry(Integer id, Stock stock) { // update stock log // pass id of stock log
		
		boolean isExist = stockRepository.existsById(id);
		if(isExist) { 
			Optional<Stock> optional= stockRepository.findById(id);
			Stock update = optional.get();
			update.setReason(stock.getReason());
			update.setUser(stock.getUser());
			
			stockRepository.save(update);
			return true;
		}else {
			throw new MessageBodyConstraintViolationException("Stock log entry not available.");
		}
	}

	@Override
	public Stock fetchEntry(Integer id) {  // get stock log  // pass id of stock log
		boolean isExist = stockRepository.existsById(id);
		if(isExist) {
			System.out.println("have");
			Optional<Stock> optional= stockRepository.findById(id);
			Stock stock = optional.get();
			return stock;
		}else {
			throw new MessageBodyConstraintViolationException("Stock log entry not available.");
		}
	}

	@Override
	public Boolean deleteEntry(Integer id) { // delete stock log  // pass id of stock log
		boolean isExist = stockRepository.existsById(id);
		if(isExist) {
			System.out.println("have");
			stockRepository.deleteById(id);
			return true;
		}else {
			throw new MessageBodyConstraintViolationException("Stock log entry not available.");
		}
	}

	@Override
	public Boolean saveDetails(Integer id, StockDetails details) {  // create stock details for respective stock log // pass id of stock log

		boolean isExist = stockRepository.existsById(id);
		if(isExist) {
			Optional<Stock> optional= stockRepository.findById(id);
			Stock stock = optional.get();
			details.setStock(stock);
			stockDetailsRepository.save(details);
			return true;
		}else {
			throw new MessageBodyConstraintViolationException("Stock log entry not available.");
		}
		
	}

	@Override
	public Boolean updateDetails(Integer id, StockDetails details) {  // update stock details for respective stock log // pass id of stock details
		
		boolean isExist = stockDetailsRepository.existsById(id);
		if(isExist) {
			Optional<StockDetails> optional= stockDetailsRepository.findById(id);
			StockDetails stockDetails = optional.get();
			stockDetails.setItemId(details.getItemId());
			stockDetails.setQuantity(details.getQuantity());
			stockDetails.setBrandId(details.getBrandId());
			stockDetails.setUmoId(details.getUmoId());
			
			stockDetailsRepository.save(stockDetails);
			return true;
		}else {
			throw new MessageBodyConstraintViolationException("Stock details entry not available.");
		}
		  
	
	}

	@Override
	public Boolean deleteDetails(Integer id) {  // delete stock details // pass id of stock details
		
		boolean isExist = stockDetailsRepository.existsById(id);
		if(isExist) {
			stockDetailsRepository.deleteById(id);
			return true;
		}else {
			throw new MessageBodyConstraintViolationException("Stock details entry not available.");
		}
		
	}

	@Override
	public Boolean deleteAllDetails(Integer id) { // delete all stock details for stock log // pass stock log id
		boolean isExist = stockRepository.existsById(id);
		if(isExist) {
			Optional<Stock> optional = stockRepository.findById(id);
			if(optional.isPresent()) {
				Integer gotId = 0;
//				for (int i = 0; i < optional.get().getStockDetails().size(); i++) {
//					gotId= optional.get().getStockDetails().get(i).getId();
//					
//					System.out.println("sdasfdfsd  " +gotId);
//					
////					(optional.get().getStockDetails().get(i));
//				}
				stockDetailsRepository.deleteById(1);
			}
			return true;
		}else {
			throw new MessageBodyConstraintViolationException("Stock log entry not available.");
		}
	}

	
	

}
