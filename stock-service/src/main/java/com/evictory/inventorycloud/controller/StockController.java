package com.evictory.inventorycloud.controller;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import javax.management.RuntimeErrorException;
import javax.validation.Valid;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.evictory.inventorycloud.exception.MessageBodyConstraintViolationException;
import com.evictory.inventorycloud.modal.DraftLog;
import com.evictory.inventorycloud.modal.Stock;
import com.evictory.inventorycloud.modal.DraftDetails;
import com.evictory.inventorycloud.service.StockService;

@RestController
@CrossOrigin()
@RequestMapping(value = "/stock")
public class StockController {

	@Autowired
	StockService stockService;

	public final String responseSuccess = "Success";
	public final String responseFailed = "Failed";
	public final String messageSuccessPOST = "Succesfully added into database.";
	public final String messageFailedPOST = "Failed to add values into database.";
	public final String messageSuccessGET = "Succesfully withdrawed from database.";
	public final String messageFailedGET = "Failed to withdraw from database.";
	public final String messageSuccessPUT = "Succesfully updated database.";
	public final String messageFailedPUT = "Failed to update database.";
	public final String messageSuccessDELETE = "Succesfully delete from database.";
	public final String messageFailedDELETE = "Failed to Delete from database.";

	@RequestMapping(value = "/openstock/draft", method = RequestMethod.POST) // create Draft log with all its respective
																				// details
	public ResponseEntity<?> saveAll(@Valid @RequestBody DraftLog draftLog) {

		draftLog.setDate(ZonedDateTime.now(ZoneId.of("UTC-4")));
		if (stockService.saveAll(draftLog)) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(oncall(true, "POST"));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(oncall(false, "POST"));
		}

	}

	@RequestMapping(value = "/openstock/draft", method = RequestMethod.GET) // fetch all Draft logs with its respective
																			// stock details
	public ResponseEntity<?> fetchAll() {

		System.out.println("Insude the get method");
		List<DraftLog> openStocks = stockService.fetchAll();
		if (openStocks == null || openStocks.size() == 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(oncall(false, "GET"));
		} else {
			return ResponseEntity.ok(openStocks);
		}
	}

	@RequestMapping(value = "/openstock/draft/entry", method = RequestMethod.POST) // create a new Draft log only
	public ResponseEntity<?> saveEntry(@RequestBody DraftLog draftLog) {

		draftLog.setDate(ZonedDateTime.now(ZoneId.of("UTC-4")));
		if (stockService.saveEntry(draftLog)) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(oncall(true, "POST"));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(oncall(false, "POST"));
		}
	}

	@RequestMapping(value = "/openstock/draft/entry/{id}", method = RequestMethod.PUT) // update existing Draft details
																						// entry
	public ResponseEntity<?> updateEntry(@PathVariable Integer id, @RequestBody DraftLog draftLog) { // open Draft log
																										// id

		if (stockService.updateEntry(id, draftLog)) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(oncall(true, "PUT"));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(oncall(false, "PUT"));
		}
	}

	@RequestMapping(value = "/openstock/draft/entry/{id}", method = RequestMethod.GET) // fetch a Draft log by id
	public ResponseEntity<?> fetchEntry(@PathVariable Integer id) {
		DraftLog draftLog = stockService.fetchEntry(id);
		if (draftLog == null) {
			throw new MessageBodyConstraintViolationException("Stock log entry not available.");
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(draftLog);

	}

	@RequestMapping(value = "/openstock/draft/entry/{id}", method = RequestMethod.DELETE) // delete existing Draft log
																							// with its details
	public ResponseEntity<?> deleteEntry(@PathVariable Integer id) {

		if (stockService.deleteEntry(id)) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(oncall(true, "DELETE"));
		} else {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(oncall(false, "DELETE"));
		}

	}

	// create a new open Draft detail entry for an existing Draft log
	@RequestMapping(value = "/openstock/draft/details/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> saveDetails(@PathVariable Integer id, @RequestBody DraftDetails draftDetails) {

		if (stockService.saveDetails(id, draftDetails)) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(oncall(true, "POST"));
		} else {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(oncall(false, "POST"));
		}
	}

	// update existing Draft details entry
	@RequestMapping(value = "/openstock/draft/details/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateDetails(@Valid @PathVariable Integer id, @RequestBody DraftDetails details) {
//		int id;
//		if (!NumberUtils.isCreatable(sid)) {
//			throw new RuntimeException("ID should be an Interger");
//		} else {
//			id = Integer.valueOf(sid);
//		}

		if (stockService.updateDetails(id, details)) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(oncall(true, "PUT"));
		} else {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(oncall(false, "PUT"));
		}
	}

	// delete existing Draft details entry
	@RequestMapping(value = "/openstock/draft/details/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteDetails(@PathVariable Integer id) {

		if (stockService.deleteDetails(id)) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(oncall(true, "DELETE"));
		} else {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(oncall(false, "DELETE"));
		}
	}

	// fetch all Draft details by Draft log by id
	@RequestMapping(value = "/openstock/draft/detailsAll/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> fetchAllDetails(@PathVariable Integer id) {
		List<DraftDetails> draftDetails = stockService.fetchAllDetails(id);
//		if (draftDetails == null) {
//			throw new MessageBodyConstraintViolationException("Stock log entry not available.");
//		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(draftDetails);

	}
	// delete all details of draft details by draft log id
	@RequestMapping(value = "/openstock/draft/detailsAll/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteAllDetails(@PathVariable Integer id) {

		if (stockService.deleteAllDetails(id)) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(oncall(true, "DELETE"));
		} else {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(oncall(false, "DELETE"));
		}
	}

	// fetch all draft log entry details and push it as a new entry to stock log and
	// delete if existing draft log
	@RequestMapping(value = "/openstock/master", method = RequestMethod.POST)
	public ResponseEntity<?> saveToMaster(@RequestBody Integer id ) { // draft log id

		if (stockService.saveToMaster(id)) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(oncall(true, "POST"));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(oncall(false, "POST"));
		}

	}

	@RequestMapping(value = "/openstock/master", method = RequestMethod.GET) // fetch all permanent added stock entries
																				// with details
	public ResponseEntity<?> fetchAllMaster() { // stock log id
		List<Stock> stock = stockService.fetchAllMaster();

		if (stock == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(oncall(true, "GET"));
		} else {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(stock);
		}

	}

	@RequestMapping(value = "/openstock/master/{id}", method = RequestMethod.GET) // fetch permanent added stock entries
																					// with details by id
	public ResponseEntity<?> fetchMaster(@PathVariable Integer id) { // stock log id
		Stock stock = stockService.fetchMaster(id);
		if (stock == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(oncall(false, "GET"));
		} else {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(stock);
		}

	}

	@RequestMapping(value = "/openstock/master/{date}/date", method = RequestMethod.GET) // fetch permanent added stock entries
	// with details by id
	public ResponseEntity<?> fetchMasterLastEntry(@PathVariable String date) { // stock log id
		Stock stock = null;
//		String date1 = "2019-05-25";
		stock = stockService.fetchMasterLastEntry(date);
		
		if (stock == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(oncall(false, "GET"));
		} else {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(stock);
		}

	}
	
	@RequestMapping(value = "/openstock/master/{date}/{brandId}/{itemId}/{uomId}", method = RequestMethod.GET) 
	// fetch Stock Movement Report
	
	public ResponseEntity<?> fetchStockMovement(@PathVariable String date, @PathVariable Integer itemId,
			@PathVariable Integer uomId, @PathVariable Integer brandId) { 
		
			return stockService.fetchStockMovementReport(date, itemId, uomId, brandId);

	}

	public Response oncall(boolean ifsuccess, String type) {
		Response response = new Response();
		String messagefailed = "";
		String messagesuccess = "";
		switch (type) {
		case "POST":
			messagefailed = messageFailedPOST;
			messagesuccess = messageSuccessPOST;
			break;
		case "GET":
			messagefailed = messageFailedGET;
			messagesuccess = messageSuccessGET;
			break;
		case "PUT":
			messagefailed = messageFailedPUT;
			messagesuccess = messageSuccessPUT;
			break;
		case "DELETE":
			messagefailed = messageFailedDELETE;
			messagesuccess = messageSuccessDELETE;
			break;
		default:
			break;
		}
		if (ifsuccess) {
			response.setResponse(responseSuccess);
			response.setMessage(messagesuccess);
		} else {
			response.setResponse(responseFailed);
			response.setMessage(messagefailed);
		}

		return response;
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
}
