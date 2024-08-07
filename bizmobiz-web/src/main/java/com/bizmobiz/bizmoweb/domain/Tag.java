package com.bizmobiz.bizmoweb.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tag", schema="bizmodb")
public class Tag implements Serializable {

	private static final long serialVersionUID = -8484880985203355078L;

	private Integer id;
	private String value;
	
	@Id
	@Column(name = "idtag", nullable = false, unique = true)
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "value", nullable = false, unique = true)
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "Tag [id=" + id + ", value=" + value + "]";
	}
}
