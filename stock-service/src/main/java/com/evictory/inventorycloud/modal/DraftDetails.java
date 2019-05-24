package com.evictory.inventorycloud.modal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class DraftDetails {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    
    @NotNull(message = "valid item information")
    @Min(value = 1 ,message="valid item information")
    Integer itemId;
    
    @NotNull(message = "valid UOM information")
    @Min(value = 1 ,message="valid UOM information")
    Integer uomId;
    
    @NotNull(message = "valid brand information")
    @Min(value = 1 ,message="valid brand information")
    Integer brandId;

    @NotNull(message = "quantity")
    @Min(value = 0 ,message="valid quantity")
    Double quantity;

    @ManyToOne
    @JoinColumn(name = "OSid")
    @JsonIgnore
    DraftLog draftLog;

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

	public Integer getUomId() {
		return uomId;
	}

	public void setUomId(Integer uomId) {
		this.uomId = uomId;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getQuantity() {
		return quantity;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public DraftLog getDraftLog() {
		return draftLog;
	}

	public void setDraftLog(DraftLog draftLog) {
		this.draftLog = draftLog;
	}

	
	
    
}
