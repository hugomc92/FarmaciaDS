package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name="RESERVATION")
@XmlRootElement(name="reservation")
@XmlType(propOrder={
		"cif",
		"productId",
		"email",
		"quantity"
})
public class Reservation implements Serializable {
	
	@EmbeddedId
	private ReservationPK reservationPK;
	
	@Column(name="QUANTITY")
	private int quantity;
	
	public Reservation(){
		reservationPK = new ReservationPK();
	}
	
	@XmlElement(required=true)
	public String getCif(){
		return this.reservationPK.getCif();
	}
	public void setCif(String cif){
		this.reservationPK.setCif(cif);
	}
	
	@XmlElement(required=true)
	public Integer getProductId(){
		return this.reservationPK.getProductId();
	}
	public void setProductId(Integer id){
		this.reservationPK.setProductId(id);
	}
	
	@XmlElement(required=true)
	public String getEmail(){
		return this.reservationPK.getEmail();
	}
	public void setEmail(String email){
		this.reservationPK.setEmail(email);
	}
	
	@XmlElement(required=true)
	public int getQuantity(){
		return this.quantity;
	}
	public void setQuantity(int q){
		this.quantity = q;
	}
}
