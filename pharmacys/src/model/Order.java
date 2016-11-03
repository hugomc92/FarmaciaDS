package model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name="USER_ORDER")
@XmlRootElement(name="order")
@XmlType(propOrder={
		"id",
		"email",
		"cif",
		"date",
		"price"
})
public class Order {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;
	
	@Column(name="USERID")
	private String email;
	
	@Column(name="PHARMACYID")
	private String cif;
	
	@Column(name="DATE")
	private Date date;
	
	@Column(name="PRICE")
	private float price;
	
	@XmlElement(required=true)
	public int getId(){
		return this.id;
	}
	public void setId(int id){
		this.id = id;
	}
	
	@XmlElement(required=true)
	public String getEmail(){
		return this.email;
	}
	public void setEmail(String email){
		this.email = email;
	}
	
	@XmlElement(required=true)
	public String getCif(){
		return this.cif;
	}
	public void setCif(String cif){
		this.cif = cif;
	}
	
	@XmlElement(required=true)
	public String getDate(){
		return this.date.toString();
	}
	public void setDate(Date d){
		this.date = d;
	}
	
	@XmlElement(required=true)
	public float getPrice(){
		return this.price;
	}
	public void setPrice(float p){
		this.price = p;
	}
}
