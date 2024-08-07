package com.bizmobiz.bizmoweb.value;

import java.util.Map;

public class OfferWorkDistanceValue {

	private Integer id;
	private String title;
	private String description;
	private Double latitud;
	private Double longitud;
	private String clientName;
	private String serviceName;
	private Double distanceAt;
	private Map<String, Object> images;

	public OfferWorkDistanceValue(Integer id, String title, String description, Double latitud, Double longitud,
			String clientName, String serviceName, Double distanceAt) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.latitud = latitud;
		this.longitud = longitud;
		this.clientName = clientName;
		this.serviceName = serviceName;
		this.distanceAt = distanceAt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Double getDistanceAt() {
		return distanceAt;
	}

	public void setDistanceAt(Double distanceAt) {
		this.distanceAt = distanceAt;
	}

	public Map<String, Object> getImages() {
		return images;
	}

	public void setImages(Map<String, Object> images) {
		this.images = images;
	}
}
