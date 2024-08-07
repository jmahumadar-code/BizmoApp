package com.bizmobiz.bizmoweb.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="user", schema="bizmodb")
public class User implements Serializable {

	private static final long serialVersionUID = -5913607313002415076L;
	
	private Integer id;
	private String phoneNumber;
	private String email;
	private String password;
	private String name;
	private String lastname;
	private String photo;
	private String uid;
	private String facebookUid;
	private String googleUid;
	private Boolean isCompany;
	private Country country;
	private Boolean isOffer;
	private Boolean isEnabled;
	
	@Id
	@Column(name = "iduser", nullable = false, unique = true)
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "phone_number", nullable = false)
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@Column(name = "email", nullable = false, unique = true)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name = "password", nullable = false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "lastname", nullable = false)
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	@Column(name = "photo", nullable=true)
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	@Column(name = "uid", nullable=true)
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	@Column(name = "facebook_uid", nullable=true)
	public String getFacebookUid() {
		return facebookUid;
	}
	public void setFacebookUid(String facebookUid) {
		this.facebookUid = facebookUid;
	}
	@Column(name = "google_uid", nullable=true)
	public String getGoogleUid() {
		return googleUid;
	}
	public void setGoogleUid(String googleUid) {
		this.googleUid = googleUid;
	}
	@Column(name = "is_company", nullable = false)
	public Boolean getIsCompany() {
		return isCompany;
	}
	public void setIsCompany(Boolean isCompany) {
		this.isCompany = isCompany;
	}
	@ManyToOne(optional=false)
	@JoinColumn(name = "country_id")
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	@Column(name = "is_offer", nullable = false)
	public Boolean getIsOffer() {
		return isOffer;
	}
	public void setIsOffer(Boolean isOffer) {
		this.isOffer = isOffer;
	}
	@Column(name = "is_enabled", nullable = false)
	public Boolean getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", phoneNumber=" + phoneNumber + ", email=" + email + ", password=" + password
				+ ", name=" + name + ", lastname=" + lastname + ", photo=" + photo + ", uid=" + uid + ", facebookUid="
				+ facebookUid + ", googleUid=" + googleUid + ", isCompany=" + isCompany + ", country=" + country
				+ ", isOffer=" + isOffer + ", isEnabled=" + isEnabled + "]";
	}
}
