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
@Table(name="setup", schema="bizmodb")
public class Setup implements Serializable {

	private static final long serialVersionUID = 4045110545484675158L;
	
	private Integer id;
	private String key;
	private String value;
	private Country country;
	
	@Id
	@Column(name = "idsetup", nullable = false, unique = true)
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "key", nullable = false)
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	@Column(name = "value", nullable = false)
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@ManyToOne(optional=false)
	@JoinColumn(name = "country_id")
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	
	@Override
	public String toString() {
		return "Setup [id=" + id + ", key=" + key + ", value=" + value + ", country=" + country + "]";
	}
}
