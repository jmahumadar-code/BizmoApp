package com.bizmobiz.bizmoweb.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="level_service", schema="bizmodb")
public class LevelService implements Serializable {

	private static final long serialVersionUID = -5461963484072381671L;

	private Integer id;
	private String name;
	private Integer level;
	private String description;
	
	@Id
	@Column(name = "idlevel_service", nullable = false, unique = true)
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
	@Column(name = "level", nullable = false, unique = true)
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
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
		return "LevelService [id=" + id + ", name=" + name + ", level=" + level + ", description=" + description + "]";
	}
}
