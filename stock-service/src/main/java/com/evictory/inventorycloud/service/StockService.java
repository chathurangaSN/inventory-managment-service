package com.evictory.inventorycloud.service;

import java.util.List;

import com.evictory.inventorycloud.modal.DraftLog;
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

	Boolean deleteAllDetails(Integer id); // delete all stock details for stock log // pass stock log id



}
