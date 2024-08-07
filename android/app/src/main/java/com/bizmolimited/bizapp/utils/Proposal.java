package com.bizmolimited.bizapp.utils;

/**
 * Created by guillermofuentesquijada on 19-11-17.
 */

public class Proposal {

    private String idProposal;
    private String cost;
    private String cost_min;
    private String cost_max;
    private boolean typeCost;
    private String commnets;
    private String fecha;
    private String offerWorkId;
    private String offerWorkLatitud;
    private String offerWorkLongitud;
    private String offerJobId;
    private String offerJobName;
    private String offerName;
    private String offerJobLatitud;
    private String offerJobLongitud;
    private String distanceAt;

    public Proposal(String idProposal, boolean type, String cost, String commnets, String fecha, String offerWorkId,
                    String offerWorkLatitud, String offerWorkLongitud, String offerJobId, String offerJobName,
                    String offerName, String offerJobLatitud, String offerJobLongitud, String distanceAt) {
        this.idProposal = idProposal;
        this.cost = cost;
        this.cost_max = null;
        this.cost_min = null;
        this.typeCost = type;
        this.commnets = commnets;
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

    public Proposal(String idProposal, boolean type, String cost_min, String cost_max, String commnets, String fecha, String offerWorkId,
                    String offerWorkLatitud, String offerWorkLongitud, String offerJobId, String offerJobName,
                    String offerName, String offerJobLatitud, String offerJobLongitud, String distanceAt) {
        this.idProposal = idProposal;
        this.cost = null;
        this.cost_max = cost_max;
        this.cost_min = cost_min;
        this.typeCost = type;
        this.commnets = commnets;
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

    public String getIdProposal() {
        return idProposal;
    }

    public void setIdProposal(String idProposal) {
        this.idProposal = idProposal;
    }

    public Double getCost() {
        return Double.parseDouble(cost);
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCommnets() {
        return commnets;
    }

    public void setCommnets(String commnets) {
        this.commnets = commnets;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getOfferWorkId() {
        return offerWorkId;
    }

    public void setOfferWorkId(String offerWorkId) {
        this.offerWorkId = offerWorkId;
    }

    public String getOfferWorkLatitud() {
        return offerWorkLatitud;
    }

    public void setOfferWorkLatitud(String offerWorkLatitud) {
        this.offerWorkLatitud = offerWorkLatitud;
    }

    public String getOfferWorkLongitud() {
        return offerWorkLongitud;
    }

    public void setOfferWorkLongitud(String offerWorkLongitud) {
        this.offerWorkLongitud = offerWorkLongitud;
    }

    public String getOfferJobId() {
        return offerJobId;
    }

    public void setOfferJobId(String offerJobId) {
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

    public String getOfferJobLatitud() {
        return offerJobLatitud;
    }

    public void setOfferJobLatitud(String offerJobLatitud) {
        this.offerJobLatitud = offerJobLatitud;
    }

    public String getOfferJobLongitud() {
        return offerJobLongitud;
    }

    public void setOfferJobLongitud(String offerJobLongitud) {
        this.offerJobLongitud = offerJobLongitud;
    }

    public String getDistanceAt() {
        return distanceAt;
    }

    public void setDistanceAt(String distanceAt) {
        this.distanceAt = distanceAt;
    }

    public Double getCost_max() {
        return Double.parseDouble(cost_max);
    }

    public Double getCost_min() {
        return Double.parseDouble(cost_min);
    }

    public void setCost_max(String cost_max) {
        this.cost_max = cost_max;
    }

    public void setCost_min(String cost_min) {
        this.cost_min = cost_min;
    }

    public void setTypeCost(boolean typeCost) {
        this.typeCost = typeCost;
    }

    public boolean getTypeCost(){
        return typeCost;
    }
}
