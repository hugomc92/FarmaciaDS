package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class InventoryPK implements Serializable {
	
	@Column(name="PHARMACYID")
	protected String pharmacyId;
	
	@Column(name="PRODUCTID")
	protected Integer productId;
	
	public InventoryPK(){}
	public InventoryPK(String pharmacyId, Integer productId){
		this.pharmacyId = pharmacyId;
		this.productId = productId;
	}
	
	public String getPharmacyId(){
		return this.pharmacyId;
	}
	public void setPharmacyId(String cif){
		this.pharmacyId = cif;
	}
	
	public Integer getProductId(){
		return this.productId;
	}
	public void setProductId(Integer id){
		this.productId = id;
	}
	
	public boolean equals( Object obj ) {
		boolean result = false;
		InventoryPK i = (InventoryPK) obj;
		if( i.pharmacyId == this.pharmacyId && i.productId == this.productId)
			result = true;
		return result;
	}
}
