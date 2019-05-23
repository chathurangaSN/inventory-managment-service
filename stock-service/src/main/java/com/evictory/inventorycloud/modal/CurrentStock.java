package com.evictory.inventorycloud.modal;

import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CurrentStock {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer itemId;
	private ZonedDateTime date;
	private Integer userId;
	private Double quantity;
	private String transferRefference;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public ZonedDateTime getDate() {
		return date;
	}
	public void setDate(ZonedDateTime date) {
		this.date = date;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public String getTransferRefference() {
		return transferRefference;
	}
	public void setTransferRefference(String transferRefference) {
		this.transferRefference = transferRefference;
	}
	

	

}
