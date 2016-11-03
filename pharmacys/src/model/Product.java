package model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name="PRODUCT")
@XmlRootElement(name="product")
@XmlType(propOrder={
		"id",
		"name",
		"description",
		"laboratory",		
		"units",
		"expirationDate",
		"size",
		"lot",
		"urlImg",
		"category"
})
public class Product {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;

	@Column(name="NAME")
	private String name;

	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="LABORATORY")
	private String laboratory;
	
	@Column(name="UNITS")
	private String units;
	
	@Column(name="EXPIRATION_DATE")
	private Date expirationDate;
	
	@Column(name="SIZE")
	private int size;
	
	@Column(name="LOT")
	private String lot;
	
	@Column(name="URL_IMG")
	private String urlImg;
	
	// foraign key, le indicamos que la referencia de clave está dentro del objeto Category y en la columna ID
	@ManyToOne
	@JoinColumn(name="CATEGORYID")
	private Category category;
	
	// ID
	@XmlElement(required=true)
	public int getId(){
		return this.id;		
	}
	public void setId(int id){
		this.id = id;
	}
	
	// NAME
	@XmlElement(required=true)
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	// DESCRIPTION
	@XmlElement(required=true)
	public String getDescription(){
		return this.description;
	}
	public void setDescription(String descr){
		this.description = descr;
	}
	
	// LABORATORY
	@XmlElement(required=true)
	public String getLaboratory(){
		return this.laboratory;
	}
	public void setLaboratory(String lab){
		this.laboratory = lab;
	}
	
	// UNITS
	@XmlElement(required=true)
	public String getUnits(){
		return this.units;
	}
	public void setUnits(String u){
		this.units = u;
	}
	
	// EXPIRATION_DATE
	@XmlElement(required=true)
	public String getExpirationDate(){
		return this.expirationDate.toString();
	}
	public void setExpirationDate(Date d){
		this.expirationDate = d;
	}
	
	// SIZE
	@XmlElement(required=true)
	public int getSize(){
		return this.size;
	}
	public void setSize(int s){
		this.size = s;
	}
	
	// LOT
	@XmlElement(required=true)
	public String getLot(){
		return this.lot;		
	}
	public void setLot(String l){
		this.lot = l;
	}
	
	// URL_IMG
	@XmlElement(required=true)
	public String getUrlImg(){
		return this.urlImg;		
	}
	public void setUrlImg(String uri){
		this.urlImg = uri;
	}
	
	// CATEGORYID
	@XmlElement(required=true)
	public Category getCategory(){
		return this.category;
	}
	public void setCategory(Category c){
		this.category = c;
	}
}
