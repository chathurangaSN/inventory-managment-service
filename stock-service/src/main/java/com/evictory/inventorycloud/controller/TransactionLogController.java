package com.evictory.inventorycloud.controller;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.evictory.inventorycloud.modal.CurrentStock;
import com.evictory.inventorycloud.modal.TransactionLog;
import com.evictory.inventorycloud.service.TransactionLogService;

@RestController
@RequestMapping(value = "/log")
public class TransactionLogController {

	@Autowired
	TransactionLogService transactionLogService;
	
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
	
	@RequestMapping(value = "/viewCurrentStockByDate/{date}", method = RequestMethod.GET) 
    public ResponseEntity<?> currentStockByDate(@PathVariable String date) {  
    	List<CurrentStock> currentStocks = transactionLogService.currentStockByDate(date);
		if(currentStocks == null || currentStocks.size() == 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(oncall(false,"GET"));
		}else {
			return ResponseEntity.ok(currentStocks);			
		}
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST) // create current stock with all its details
    public ResponseEntity<?> save(@RequestBody TransactionLog transactionLog)  {
		transactionLog.setDate(ZonedDateTime.now(ZoneId.of("UTC-4")));
        if(transactionLogService.save(transactionLog)) {
        	return ResponseEntity.status(HttpStatus.ACCEPTED).body(oncall(true,"POST"));				
		}else {				
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(oncall(false,"POST"));
		}
        
    }
	
	 @RequestMapping(value = "/viewTransactionLog", method = RequestMethod.GET) // fetch all current stock records
	    public ResponseEntity<?> fetchAllTransactionLog() {  
	    	List<TransactionLog> transactionLogs = transactionLogService.fetchAll();
			if(transactionLogs == null || transactionLogs.size() == 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(oncall(false,"GET"));
			}else {
				return ResponseEntity.ok(transactionLogs);			
			}
		}
	 
	 
	 @RequestMapping(value = "/viewTransactionLog/{id}", method = RequestMethod.GET)
		public ResponseEntity<TransactionLog> fetch(@PathVariable Integer id) {
			
		 TransactionLog transactionLog = transactionLogService.fetch(id);
			if(transactionLog == null) {
				return ResponseEntity.notFound().build();
			}else {
				return ResponseEntity.ok(transactionLog);
			}
		}
	
	 @RequestMapping(value = "/updateTransactionLog/{id}", method = RequestMethod.PUT)
	    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody TransactionLog transactionLog){
	     
		 if(transactionLogService.update(id, transactionLog)){
			 return ResponseEntity.status(HttpStatus.ACCEPTED).body(oncall(true,"PUT"));	
		 }
		 else {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(oncall(false,"PUT"));
		 }
	    }
	
	
	 @RequestMapping(value = "/deleteTransactionLog/{id}", method = RequestMethod.DELETE)
	    public ResponseEntity<?> delete(@PathVariable Integer id){
	     
		 if(transactionLogService.delete(id)) {
	        	return ResponseEntity.status(HttpStatus.ACCEPTED).body(oncall(true,"DELETE"));				
			}else {				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(oncall(false,"DELETE"));
			}
		 
		 
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
		if(ifsuccess) {
			response.setResponse(responseSuccess);
			response.setMessage(messagesuccess);
		}else {
			response.setResponse(responseFailed);
			response.setMessage(messagefailed);
		}
		
		return response;
	}

	class Response{
	   	 
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