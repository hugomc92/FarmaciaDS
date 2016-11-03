package model;

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
@Table(name="CATEGORY")
@XmlRootElement(name="category")
@XmlType(propOrder={
		"id",
		"name",
		"urlImg"
})
public class Category {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="IMG")
	private String urlImg;
	
	@XmlElement(required=true)
	public int getId(){
		return this.id;		
	}
	public void setId(int id){
		this.id = id;
	}
	
	@XmlElement(required=true)
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	@XmlElement(required=true)
	public String getUrlImg(){
		return this.urlImg;
	}
	public void setUrlImg(String url){
		this.urlImg = url;
	}
}
