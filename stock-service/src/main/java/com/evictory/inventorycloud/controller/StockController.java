package com.evictory.inventorycloud.controller;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.evictory.inventorycloud.modal.Stock;
import com.evictory.inventorycloud.service.StockService;

@RestController
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
	
	
	
	// Sahan Add your stock controller methods
	
		@RequestMapping(value = "", method = RequestMethod.POST) // create  stock log with all its respective details
	    public ResponseEntity<?> saveOpenStock(@Valid @RequestBody Stock stock)  {
			stock.setDate(ZonedDateTime.now(ZoneId.of("UTC-4")));
	        if(stockService.saveAll(stock)) {
	        	return ResponseEntity.status(HttpStatus.ACCEPTED).body(oncall(true,"POST"));				
			}else {				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(oncall(false,"POST"));
			}
	        
	    }
		
		
	    @RequestMapping(value = "", method = RequestMethod.GET) // fetch all stock logs with its respective stock details
	    public ResponseEntity<?> fetchAllOpenStock() {  
	    	List<Stock> openStocks = stockService.fetchAll();
			if(openStocks == null || openStocks.size() == 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(oncall(false,"GET"));
			}else {
				return ResponseEntity.ok(openStocks);			
			}
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
