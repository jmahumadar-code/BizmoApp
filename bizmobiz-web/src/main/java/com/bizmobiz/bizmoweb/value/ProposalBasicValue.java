package com.bizmobiz.bizmoweb.value;

import java.util.Date;

public class ProposalBasicValue {

	private Integer id;
	private Double cost;
	private Double costMin;
	private Double costMax;
	private Boolean costType;
	private String comments;
	private Date fecha;
	private Integer offerWorkId;
	private Double offerWorkLatitud;
	private Double offerWorkLongitud;
	private Integer offerJobId;
	private String offerJobName;
	private String offerName;
	private Double offerJobLatitud;
	private Double offerJobLongitud;
	private Double distanceAt;

	public ProposalBasicValue(Integer id, Double cost, Double costMin, Double costMax, Boolean costType,
			String comments, Date fecha, Integer offerWorkId, Double offerWorkLatitud, Double offerWorkLongitud,
			Integer offerJobId, String offerJobName, String offerName, Double offerJobLatitud, Double offerJobLongitud,
			Double distanceAt) {
		super();
		this.id = id;
		this.cost = cost;
		this.costMin = costMin;
		this.costMax = costMax;
		this.costType = costType;
		this.comments = comments;
		this.fecha = fecha;
		this.offerWorkId = offerWorkId;
		this.offerWorkLatitud = offerWorkLatitud;
		this.offerWorkLongitud = offerWorkLongitud;
		this.offerJobId = offerJobId;
		this.offerJobName = offerJobName;
		this.offerName = offerName;
		this.offerJobLatitud = offerJobLatitud;
		this.offerJobLongitud = offerJobLongitud;
		this.distanceAt = distanceAt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getCostMin() {
		return costMin;
	}

	public void setCostMin(Double costMin) {
		this.costMin = costMin;
	}

	public Double getCostMax() {
		return costMax;
	}

	public void setCostMax(Double costMax) {
		this.costMax = costMax;
	}

	public Boolean getCostType() {
		return costType;
	}

	public void setCostType(Boolean costType) {
		this.costType = costType;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getOfferWorkId() {
		return offerWorkId;
	}

	public void setOfferWorkId(Integer offerWorkId) {
		this.offerWorkId = offerWorkId;
	}

	public Double getOfferWorkLatitud() {
		return offerWorkLatitud;
	}

	public void setOfferWorkLatitud(Double offerWorkLatitud) {
		this.offerWorkLatitud = offerWorkLatitud;
	}

	public Double getOfferWorkLongitud() {
		return offerWorkLongitud;
	}

	public void setOfferWorkLongitud(Double offerWorkLongitud) {
		this.offerWorkLongitud = offerWorkLongitud;
	}

	public Integer getOfferJobId() {
		return offerJobId;
	}

	public void setOfferJobId(Integer offerJobId) {
		this.offerJobId = offerJobId;
	}

	public String getOfferJobName() {
		return offerJobName;
	}

	public void setOfferJobName(String offerJobName) {
		this.offerJobName = offerJobName;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public Double getOfferJobLatitud() {
		return offerJobLatitud;
	}

	public void setOfferJobLatitud(Double offerJobLatitud) {
		this.offerJobLatitud = offerJobLatitud;
	}

	public Double getOfferJobLongitud() {
		return offerJobLongitud;
	}

	public void setOfferJobLongitud(Double offerJobLongitud) {
		this.offerJobLongitud = offerJobLongitud;
	}

	public Double getDistanceAt() {
		return distanceAt;
	}

	public void setDistanceAt(Double distanceAt) {
		this.distanceAt = distanceAt;
	}

	@Override
	public String toString() {
		return "ProposalBasicValue [id=" + id + ", cost=" + cost + ", costMin=" + costMin + ", costMax=" + costMax
				+ ", costType=" + costType + ", comments=" + comments + ", fecha=" + fecha + ", offerWorkId="
				+ offerWorkId + ", offerWorkLatitud=" + offerWorkLatitud + ", offerWorkLongitud=" + offerWorkLongitud
				+ ", offerJobId=" + offerJobId + ", offerJobName=" + offerJobName + ", offerName=" + offerName
				+ ", offerJobLatitud=" + offerJobLatitud + ", offerJobLongitud=" + offerJobLongitud + ", distanceAt="
				+ distanceAt + "]";
	}
}
