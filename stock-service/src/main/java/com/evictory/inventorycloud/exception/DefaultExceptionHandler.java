package com.evictory.inventorycloud.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler{

	public final String responseFailed = "Failed";
	
	 @Override
	  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	      HttpHeaders headers, HttpStatus status, WebRequest request) {

		 String message = "Please provide ";
		
			for (int i = 0; i < ex.getBindingResult().getAllErrors().size(); i++) {
				
				if(i == 0) {
					message = message + ex.getBindingResult().getAllErrors().get(i).getDefaultMessage();
				}else {
					message = message +" & " + ex.getBindingResult().getAllErrors().get(i).getDefaultMessage() ;
				}
			}
			ErrorMessage errorMessage = new ErrorMessage(message+".", responseFailed);
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	  } 
	 
	 @ExceptionHandler(MessageBodyConstraintViolationException.class)
	  public final ResponseEntity<?> handleUserNotFoundException(MessageBodyConstraintViolationException ex) {
		 ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), responseFailed);
		 
	    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	  }
	 
	 public class ErrorMessage {
			
			private String response;
			private String message; 
	    	
	    	public ErrorMessage() {
	    		super();
			}
	    	public ErrorMessage(String message, String response) {
	    		super();
	    		this.message = message;
	    		this.response = response;
			}

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
