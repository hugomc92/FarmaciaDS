package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ReservationPK implements Serializable {
	@Column(name="PHARMACYID")
	protected String cif;
	
	@Column(name="PRODUCTID")
	protected Integer productId;
	
	@Column(name="USERID")
	protected String email;
	
	public ReservationPK(){}
	public ReservationPK(String cif, Integer productId, String email){
		this.cif = cif;
		this.productId = productId;
		this.email = email;
	}
	
	public String getCif(){
		return this.cif;
	}
	public void setCif(String cif){
		this.cif = cif;
	}
	
	public Integer getProductId(){
		return this.productId;
	}
	public void setProductId(Integer id){
		this.productId = id;
	}
	
	public String getEmail(){
		return this.email;
	}
	public void setEmail(String email){
		this.email = email;
	}
	
	public boolean equals( Object obj ) {
		boolean result = false;
		ReservationPK i = (ReservationPK) obj;
		if( i.cif == this.cif && i.productId == this.productId && i.email == this.email)
			result = true;
		return result;
	}
}
