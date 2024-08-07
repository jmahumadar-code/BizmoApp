package com.bizmobiz.bizmoweb.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="offer_work_state", schema="bizmodb")
public class OfferWorkState implements Serializable {

	private static final long serialVersionUID = 8186186769690605135L;
	
	private Integer id;
	private String name;
	private String description;
	
	@Id
	@Column(name = "idoffer_work_state", nullable = false, unique = true)
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
	@Column(name = "description", nullable = true)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "OfferWorkState [id=" + id + ", name=" + name + ", description=" + description + "]";
	}
}
