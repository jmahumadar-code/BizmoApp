package com.bizmobiz.bizmoweb.value;

public class OfferJobOfferDistanceValue {

	private Integer id;
	private String title;
	private String description;
	private Double latitud;
	private Double longitud;
	private String offerName;
	private String serviceName;
	private Integer offerId;
	private Double distanceAt;

	

	public OfferJobOfferDistanceValue(Integer id, String title, String description, Double latitud, Double longitud,
			String offerName, String serviceName, Integer offerId, Double distanceAt) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.latitud = latitud;
		this.longitud = longitud;
		this.offerName = offerName;
		this.serviceName = serviceName;
		this.offerId = offerId;
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

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Integer getOfferId() {
		return offerId;
	}

	public void setOfferId(Integer offerId) {
		this.offerId = offerId;
	}

	public Double getDistanceAt() {
		return distanceAt;
	}

	public void setDistanceAt(Double distanceAt) {
		this.distanceAt = distanceAt;
	}
}
