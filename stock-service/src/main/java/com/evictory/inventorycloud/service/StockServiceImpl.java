package com.evictory.inventorycloud.service;
import java.util.List;

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
	

	// Sahan Add your stock controller methods
	
	@Override
	public Boolean saveAll(Stock stock) { // save all stock details with log
		if(stock == null) {
			throw new NullPointerException("Response body is empty");
		}else {
			for (int i = 0; i < stock.getStockDetails().size(); i++) {
				if(stock.getStockDetails().get(i).getItemId() == null || stock.getStockDetails().get(i).getQuantity() == null) {
					throw new MessageBodyConstraintViolationException("Please provide all open stock details.");
				}else if (stock.getStockDetails().get(i).getItemId()<1) {
					throw new MessageBodyConstraintViolationException("Please provide valid item id details for stock details.");
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean updateEntry(Integer id, Stock stock) { // update stock log // pass id of stock log
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stock fetchEntry(Integer id) {  // get stock log  // pass id of stock log
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteEntry(Integer id) { // delete stock log  // pass id of stock log
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean saveDetails(Integer id, StockDetails details) {  // create stock details for respective stock log // pass id of stock log
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean updateDetails(Integer id, StockDetails details) {  // update stock details for respective stock log // pass id of stock details
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteDetails(Integer id) {  // delete stock details // pass id of stock details
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteAllDetails(Integer id) { // delete all stock details for stock log // pass stock log id
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StockDetails fetchAllDetails(Integer id) {  // get all stock details for stock log  // pass stock log id
		// TODO Auto-generated method stub
		return null;
	}
	
	// Sahan methods End...................................................

	// Sachith Add your stock controller methods

	
	// Sachith methods End.................................................

	// Chamila Add your stock controller methods

	
	// Chamila methods End.................................................

	// Dilshan Add your stock controller methods

	
	// Dilshan methods End.................................................

	// Kavishka Add your stock controller methods

	
	// Kavishka methods End.................................................

}
