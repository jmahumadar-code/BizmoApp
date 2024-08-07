package com.bizmobiz.bizmoweb.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="proposal_state", schema="bizmodb")
public class ProposalState implements Serializable {

	private static final long serialVersionUID = 5228585036854363653L;

	private Integer id;
	private String name;
	private String description;
	
	@Id
	@Column(name = "idproposal_state", nullable = false, unique = true)
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "name", nullable = false)
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
		return "ProposalState [id=" + id + ", name=" + name + ", description=" + description + "]";
	}
}
