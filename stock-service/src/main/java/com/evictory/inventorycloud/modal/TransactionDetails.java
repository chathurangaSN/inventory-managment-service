package com.evictory.inventorycloud.modal;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class TransactionDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer itemId;
	private Double quantity;
	private Integer uomid;
	private Integer brandid;
	
	@ManyToOne
    @JoinColumn(name = "transactionLogId")
    @JsonIgnore
    TransactionLog transactionlog;

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

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Integer getUomid() {
		return uomid;
	}

	public void setUomid(Integer uomid) {
		this.uomid = uomid;
	}

	public Integer getBrandid() {
		return brandid;
	}

	public void setBrandid(Integer brandid) {
		this.brandid = brandid;
	}

	public TransactionLog getTransactionlog() {
		return transactionlog;
	}

	public void setTransactionlog(TransactionLog transactionlog) {
		this.transactionlog = transactionlog;
	}
	
	
	

	

}