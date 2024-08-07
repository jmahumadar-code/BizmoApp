package com.bizmobiz.bizmoweb.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="country", schema="bizmodb")
public class Country implements Serializable {

	private static final long serialVersionUID = -1075356672876463491L;
	
	private Integer id;
	private String name;
	private String code;
	private String setupVersion;
	private Boolean isEnabled;
	
	@Id
	@Column(name = "idcountry", nullable = false, unique = true)
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "name", nullable = false, unique = true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "code", nullable = false, unique = true)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(name = "setup_version", nullable = true)
	public String getSetupVersion() {
		return setupVersion;
	}
	public void setSetupVersion(String setupVersion) {
		this.setupVersion = setupVersion;
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
		return "Country [id=" + id + ", name=" + name + ", code=" + code + ", setupVersion=" + setupVersion
				+ ", isEnabled=" + isEnabled + "]";
	}
}
