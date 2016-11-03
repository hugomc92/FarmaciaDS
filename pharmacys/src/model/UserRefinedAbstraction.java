package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name="USER")
@XmlRootElement(name="user")
@XmlType(propOrder={
		"email",
		"name",
		"surname",
		"password",
		"resetHash",
		"role",
		"orders",
		"active",
		"cifPharmacy"
})
public class UserRefinedAbstraction implements UserAbstraction {
	
	// con la anotacion @Transient le decimos a hibernate que ignore este atributo
	@Transient
	private UserImplementator implementator;
	
	@Id
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="SURNAME")
	private String surname;
	
	@Column(name="PASSWORD")
	private String password;
		
	@Column(name="RESETHASH")
	private String resetHash;
	
	@Column(name="ROLE")
	private int role;
	
	@Column(name="ACTIVE")
	private int active;
	
	@Column(name="CIFPHARMACY")
	private String cifPharmacy;
	
	public UserRefinedAbstraction(){
		this.implementator = null;
	}
	
	public UserRefinedAbstraction(UserImplementator uimpl){
		this.implementator = uimpl;
	}
	
	// EMAIL
	@XmlElement(required=true)
	public String getEmail(){
		return this.email;
	}
	public void setEmail(String email){
		this.email = email;
	}
	
	// NAME
	@XmlElement(required=true)
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	// SURNAME
	@XmlElement(required=true)
	public String getSurname(){
		return this.surname;
	}
	public void setSurname(String surname){
		this.surname = surname;
	}
	
	// PASSWORD
	@XmlElement(required=true)
	public String getPassword(){
		return this.password;
	}
	public void setPassword(String passw){
		this.password = passw;
	}
	
	// RESET HASH
	@XmlElement(required=true)
	public String getResetHash(){
		return this.resetHash;
	}
	public void setResetHash(String hash){
		this.resetHash = hash;
	}
	
	// ROLE
	@XmlElement(required=true)
	public int getRole(){
		return this.role;
	}
	public void setRole(int r){
		this.role = r;
	}
	
	// ACTIVE
	@XmlElement(required=true)
	public int getActive(){
		return this.active;
	}
	public void setActive(){
		this.active = 1;
	}
	
	// PHARMACIES
	@XmlElement(required=true)
	public String getCifPharmacy(){
		return this.cifPharmacy;
	}
	public void setCifPharmacy(String cif){
		this.cifPharmacy = cif;
	}
	
	@Transient
	public boolean registration(String email, String name, String surname, String password){
		return false;
	}
	
	@Transient
	public boolean logout(){
		return false;
	}

	@Transient
	@Override
	public boolean sendResetMail() {
		return this.implementator.sendResetMail();
	}

	@Transient
	@Override
	public boolean sendVerificationMail() {
		return this.implementator.sendVerificationMail();
	}
}
