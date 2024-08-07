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
import javax.persistence.Transient;

import com.bizmobiz.bizmoweb.value.OfferWorkDistanceValue;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.vividsolutions.jts.geom.Point;

@Entity
@Table(name="offer_work", schema="bizmodb")
@NamedNativeQueries(value = { 
		@NamedNativeQuery(
				name = "OfferWork.findAllInProposal", 
				query = "SELECT sel1.idoffer_work, sel1.title, sel1.description, ST_X(sel1.position) latitud, ST_Y(sel1.position) longitud, sel1.client_name, sel2.service_name,  ( 6371 * \n" + 
						"    acos( \n" + 
						"    cos( radians( :latitud ) ) * \n" + 
						"      cos( radians( ST_X(sel1.position) ) ) * \n" + 
						"      cos( radians( ST_Y(sel1.position) ) - \n" + 
						"      radians( :longitud ) ) + \n" + 
						"        sin( radians( :latitud ) ) * \n" + 
						"          sin( radians( ST_X(sel1.position) ) ) ) ) \n" + 
						"          distance_at \n" + 
						"FROM \n" + 
						"	(SELECT oj1.idoffer_work, oj1.title, oj1.description, oj1.position, CONCAT(u.name, ' ', u.lastname) client_name FROM bizmodb.offer_work oj1 INNER JOIN bizmodb.user u ON oj1.offer_work_state_id = :state_id AND oj1.client_id = u.iduser WHERE u.country_id = :country_id ) sel1\n" + 
						"INNER JOIN \n" + 
						"	(SELECT oj2.idoffer_work, s.name service_name FROM bizmodb.service s INNER JOIN bizmodb.offer_work oj2 ON s.idservice = oj2.service_id WHERE s.idservice = :service_id ) sel2\n" + 
						"ON sel1.idoffer_work = sel2.idoffer_work \n" + 
						"HAVING distance_at <= :radius ;",
				resultSetMapping="OfferWorkDistanceValue")
		})
@SqlResultSetMappings(value = { 
		@SqlResultSetMapping(
			    name = "OfferWorkDistanceValue",
				classes = @ConstructorResult(
		                targetClass = OfferWorkDistanceValue.class,
		                columns = {
			                    @ColumnResult(name = "idoffer_work", type = Integer.class),
			                    @ColumnResult(name = "title", type = String.class),
			                    @ColumnResult(name = "description", type = String.class),
			                    @ColumnResult(name = "latitud", type = Double.class),
			                    @ColumnResult(name = "longitud", type = Double.class),
			                    @ColumnResult(name = "client_name", type = String.class),
			                    @ColumnResult(name = "service_name", type = String.class),
			                    @ColumnResult(name = "distance_at", type = Double.class)
		                    }
		                )
			    )
		})
public class OfferWork implements Serializable {

	private static final long serialVersionUID = -8444724329757179128L;

	private Integer id;
	private String title;
	private String description;
	private Date createDate;
	private Date workDate;
	private Point position;
	private User client;
	private Service service;
	private OfferWorkState state;
	
	@Id
	@Column(name = "idoffer_work", nullable = false, unique = true)
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "title", nullable = false)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name = "description", nullable = false)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name = "create_date", nullable = false)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column(name = "work_date", nullable = false)
	public Date getWorkDate() {
		return workDate;
	}
	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}
	@Column(name = "position", nullable = false, columnDefinition = "geometry")
	public Point getPosition() {
		return position;
	}
	public void setPosition(Point position) {
		this.position = position;
	}
	@ManyToOne(optional=false)
	@JoinColumn(name = "client_id")
	public User getClient() {
		return client;
	}
	public void setClient(User client) {
		this.client = client;
	}
	@ManyToOne(optional=false)
	@JoinColumn(name = "service_id")
	public Service getService() {
		return service;
	}
	public void setService(Service service) {
		this.service = service;
	}
	@ManyToOne(optional=false)
	@JoinColumn(name = "offer_work_state_id")
	public OfferWorkState getState() {
		return state;
	}
	public void setState(OfferWorkState state) {
		this.state = state;
	}
	@JsonGetter("serviceName")
	@Transient
	public String getServiceName() {
		return this.service.getName();
	}
	
	@Override
	public String toString() {
		return "OfferWork [id=" + id + ", title=" + title + ", description=" + description + ", createDate="
				+ createDate + ", workDate=" + workDate + ", position=" + position + ", client="
				+ client + ", service=" + service + ", state=" + state + "]";
	}
}
