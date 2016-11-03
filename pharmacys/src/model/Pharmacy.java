package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name="PHARMACY")
@XmlRootElement(name="pharmacy")
@XmlType(propOrder={
		"cif",
		"name",
		"description",
		"phoneNumber",
		"startSchedule",
		"endSchedule"
})
public class Pharmacy {

	@Id
	@Column(name="CIF")
	private String cif;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="PHONE_NUMBER")
	private int phoneNumber;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="START_SCHEDULE")
	private int startShedule; 
	
	@Column(name="END_SCHEDULE")
	private int endtShedule; 
	
	@Column(name="LATITUDE")
	private double latitude;
	
	@Column(name="LONGITUDE")
	private double longitude;
	
	@Column(name="ADDRESS")
	private String address;
	
	@Column(name="URL_IMG")
	private String urlImg;
	
	// CIF
	@XmlElement(required=true)
	public String getCif(){
		return this.cif;
	}	
	public void setCif(String cif){
		this.cif = cif;
	}
	
	// NAME
	@XmlElement(required=true)
	public String getName(){
		return this.name;
	}	
	public void setName(String name){
		this.name = name;
	}
	
	//PHONE_NUMBER
	@XmlElement(required=true)
	public int getPhoneNumber(){
		return this.phoneNumber;
	}
	public void setPhoneNumber(int phoneN){
		this.phoneNumber = phoneN;		
	}
	
	// DESCRIPTION
	@XmlElement(required=true)
	public String getDescription(){
		return this.description;
	}
	public void setDescription(String descr){
		this.description = descr;
	}
	
	// START_SCHEDULE
	@XmlElement(required=true)
	public int getStartSchedule(){
		return this.startShedule;
	}
	public void setStartSchedule(int h){
		this.startShedule = h;
	}
	
	// END_SCHEDULE
	@XmlElement(required=true)
	public int getEndSchedule(){
		return this.endtShedule;
	}
	public void setEndSchedule(int h){
		this.endtShedule = h;
	}
	
	// LATITUDE
	@XmlElement(required=true)
	public double getLatitude(){
		return this.latitude;
	}
	public void setLatitude(double lat){
		this.latitude = lat;
	}
	
	// LONGITUDE
	@XmlElement(required=true)
	public double getLongitude(){
		return this.longitude;
	}
	public void setLongitude(double lng){
		this.longitude = lng;
	}
	 
	// ADDRESS
	@XmlElement(required=true)
	public String getAddress(){
		return this.address;
	}
	public void setAddress(String address){
		this.address = address;
	}
	
	// URL_IMG
	@XmlElement(required=true)
	public String getUrlImg(){
		return this.urlImg;		
	}
	public void setUrlImg(String uri){
		this.urlImg = uri;
	}
}
