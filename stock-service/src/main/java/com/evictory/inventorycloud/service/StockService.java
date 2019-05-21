package com.evictory.inventorycloud.service;

import java.util.List;

import com.evictory.inventorycloud.modal.Stock;
import com.evictory.inventorycloud.modal.StockDetails;

public interface StockService {

	// Sahan Add your stock controller methods

	Boolean saveAll(Stock stock); // save all stock details with log

	List<Stock> fetchAll(); // get all stock details with log

	Boolean saveEntry(Stock stock); // save only stock log

	Boolean updateEntry(Integer id, Stock stock); // update stock log // pass id of stock log

	Stock fetchEntry(Integer id); // get stock log  // pass id of stock log

	Boolean deleteEntry(Integer id); // delete stock log  // pass id of stock log

	Boolean saveDetails(Integer id, StockDetails details); // create stock details for respective stock log // pass id of stock log

	Boolean updateDetails(Integer id, StockDetails details); // update stock details for respective stock log // pass id of stock details

	Boolean deleteDetails(Integer id); // delete stock details // pass id of stock details

	Boolean deleteAllDetails(Integer id); // delete all stock details for stock log // pass stock log id

	StockDetails fetchAllDetails(Integer id); // get all stock details for stock log  // pass stock log id

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
