package com.bizmobiz.bizmoweb.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;

import com.bizmobiz.bizmoweb.value.ProposalBasicValue;

@Entity
@Table(name="proposal", schema="bizmodb")
@NamedNativeQueries(value = { 
		@NamedNativeQuery(
				name = "Proposal.findByOfferWorkNative", 
				query = "SELECT sel2.idproposal, sel2.cost, sel2.cost_min, sel2.cost_max, sel2.cost_type, sel2.comments, sel2.date, sel2.offer_work_id, ST_X(sel2.offer_work_position) as offer_work_latitud, ST_Y(sel2.offer_work_position) as offer_work_longitud, sel1.offer_job_id, sel1.offer_job_name, sel1.offer_name, ST_X(sel1.offer_job_position) as offer_job_latitud, ST_Y(sel1.offer_job_position) as offer_job_longitud, ( 6371 * \n" + 
						"    acos( \n" + 
						"    cos( radians( ST_X(sel2.offer_work_position) ) ) * \n" + 
						"      cos( radians( ST_X(sel1.offer_job_position) ) ) * \n" + 
						"      cos( radians( ST_Y(sel1.offer_job_position) ) - \n" + 
						"      radians( ST_Y(sel2.offer_work_position) ) ) + \n" + 
						"        sin( radians( ST_X(sel2.offer_work_position) ) ) * \n" + 
						"          sin( radians( ST_X(sel1.offer_job_position) ) ) ) ) \n" + 
						"          AS distance_at\n" + 
						"	FROM \n" + 
						"	(SELECT p1.idproposal, p1.cost, p1.cost_min, p1.cost_max, p1.cost_type, p1.comments, p1.date, p1.offer_job_id, p1.offer_work_id, ow.position as offer_work_position FROM bizmodb.proposal p1 INNER JOIN bizmodb.offer_work ow ON p1.offer_work_id = ow.idoffer_work WHERE p1.offer_work_id = :offer_work_id ) sel2\n" + 
						"    INNER JOIN \n" + 
						"    (SELECT oj1.idoffer_job as offer_job_id, oj1.title as offer_job_name, oj1.last_position as offer_job_position, CONCAT(u.name, ' ', u.lastname) offer_name FROM bizmodb.offer_job oj1 INNER JOIN bizmodb.user u ON oj1.offer_id = u.iduser) sel1 \n" + 
						"    ON sel2.offer_job_id = sel1.offer_job_id ;", 
				resultSetMapping="ProposalBasicValue")
		})
@SqlResultSetMappings(value = { 
		@SqlResultSetMapping(
			    name = "ProposalBasicValue",
				classes = @ConstructorResult(
		                targetClass = ProposalBasicValue.class,
		                columns = {
			                    @ColumnResult(name = "idproposal", type = Integer.class),
			                    @ColumnResult(name = "cost", type = Double.class),
			                    @ColumnResult(name = "cost_min", type = Double.class),
			                    @ColumnResult(name = "cost_max", type = Double.class),
			                    @ColumnResult(name = "cost_type", type = Boolean.class),
			                    @ColumnResult(name = "comments", type = String.class),
			                    @ColumnResult(name = "date", type = Date.class),
			                    @ColumnResult(name = "offer_work_id", type = Integer.class),
			                    @ColumnResult(name = "offer_work_latitud", type = Double.class),
			                    @ColumnResult(name = "offer_work_longitud", type = Double.class),
			                    @ColumnResult(name = "offer_job_id", type = Integer.class),
			                    @ColumnResult(name = "offer_job_name", type = String.class),
			                    @ColumnResult(name = "offer_name", type = String.class),
			                    @ColumnResult(name = "offer_job_latitud", type = Double.class),
			                    @ColumnResult(name = "offer_job_longitud", type = Double.class),
			                    @ColumnResult(name = "distance_at", type = Double.class)
		                    }
		                )
			    )
		})
public class Proposal implements Serializable {

	private static final long serialVersionUID = -7318251711536621034L;

	private Integer id;
	private Double cost;
	private Double costMin;
	private Double costMax;
	private Boolean costType;
	private String comments;
	private Date date;
	private OfferWork offerWork;
	private OfferJob offerJob;
	private ProposalState state;
	
	@Id
	@Column(name = "idproposal", nullable = false, unique = true)
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "cost", nullable = true)
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	@Column(name = "cost_min", nullable = true)
	public Double getCostMin() {
		return costMin;
	}
	public void setCostMin(Double costMin) {
		this.costMin = costMin;
	}
	@Column(name = "cost_max", nullable = true)
	public Double getCostMax() {
		return costMax;
	}
	public void setCostMax(Double costMax) {
		this.costMax = costMax;
	}
	@Column(name = "cost_type", nullable = false)
	public Boolean getCostType() {
		return costType;
	}
	public void setCostType(Boolean costType) {
		this.costType = costType;
	}
	@Column(name = "comments", nullable = false)
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	@Column(name = "date", nullable = false)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@ManyToOne(optional=false)
	@JoinColumn(name = "offer_work_id")
	public OfferWork getOfferWork() {
		return offerWork;
	}
	public void setOfferWork(OfferWork offerWork) {
		this.offerWork = offerWork;
	}
	@ManyToOne(optional=false)
	@JoinColumn(name = "offer_job_id")
	public OfferJob getOfferJob() {
		return offerJob;
	}
	public void setOfferJob(OfferJob offerJob) {
		this.offerJob = offerJob;
	}
	@ManyToOne(optional=false)
	@JoinColumn(name = "proposal_state_id")
	public ProposalState getState() {
		return state;
	}
	public void setState(ProposalState state) {
		this.state = state;
	}
}
