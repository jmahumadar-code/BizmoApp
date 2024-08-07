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
@Table(name = "image_work", schema = "bizmodb")
public class ImageWork implements Serializable {

	private static final long serialVersionUID = 6284884448699664217L;

	private Integer id;
	private String path;
	private OfferWork offerWork;
	
	@Id
	@Column(name = "idimage_work", nullable = false, unique = true)
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "path", nullable = false)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@ManyToOne(optional=false)
	@JoinColumn(name = "offer_work_id")
	public OfferWork getOfferWork() {
		return offerWork;
	}

	public void setOfferWork(OfferWork offerWork) {
		this.offerWork = offerWork;
	}

	@Override
	public String toString() {
		return "ImageWork [id=" + id + ", path=" + path + ", offerWork=" + offerWork + "]";
	}
}
