package com.evictory.inventorycloud.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.evictory.inventorycloud.modal.DraftLog;
import com.evictory.inventorycloud.modal.Stock;
import com.evictory.inventorycloud.modal.DraftDetails;

public interface StockService {


	Boolean saveAll(DraftLog draftLog); // save all stock details with log

	List<DraftLog> fetchAll(); // get all stock details with log

	Boolean saveEntry(DraftLog draftLog); // save only stock log

	Boolean updateEntry(Integer id, DraftLog draftLog); // update stock log // pass id of stock log

	DraftLog fetchEntry(Integer id); // get stock log  // pass id of stock log

	Boolean deleteEntry(Integer id); // delete stock log  // pass id of stock log

	Boolean saveDetails(Integer id, DraftDetails details); // create stock details for respective stock log // pass id of stock log

	Boolean updateDetails(Integer id, DraftDetails details); // update stock details for respective stock log // pass id of stock details

	Boolean deleteDetails(Integer id); // delete stock details // pass id of stock details
	
	List<DraftDetails> fetchAllDetails(Integer id); // fetch all stock details by stock log // pass id of stock details

	Boolean deleteAllDetails(Integer id); // delete all stock details for stock log // pass stock log id

	Boolean saveToMaster(Integer id);  // fetch all draft log entry details and push it as a new entry to stock log and delete if existing draft log

	List<Stock> fetchAllMaster(); // fetch all permanent added stock entries with details
	
	Stock fetchMaster(Integer id); // fetch  permanent added stock entries with details by id
	
	Stock fetchMasterLastEntry(String date); // fetch  the last entry on open stock by date 
	
	ResponseEntity<?> fetchStockMovementReport(String date,Integer itemId,Integer uomId,Integer brandId);
}
