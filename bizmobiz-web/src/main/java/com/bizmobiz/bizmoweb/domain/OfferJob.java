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

import com.bizmobiz.bizmoweb.value.OfferJobDistanceValue;
import com.bizmobiz.bizmoweb.value.OfferJobOfferDistanceValue;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vividsolutions.jts.geom.Point;

@Entity
@Table(name="offer_job", schema="bizmodb")
@NamedNativeQueries(value = { 
		@NamedNativeQuery(
				name = "OfferJob.find10ByPositionRadiusCountry", 
				query = "SELECT sel1.idoffer_job, sel1.title, sel1.description, ST_X(sel1.last_position) latitud, ST_Y(sel1.last_position) longitud, sel1.offer_name, sel2.service_name,  ( 6371 * \n" + 
						"    acos( \n" + 
						"    cos( radians( :latitud ) ) * \n" + 
						"      cos( radians( ST_X(sel1.last_position) ) ) * \n" + 
						"      cos( radians( ST_Y(sel1.last_position) ) - \n" + 
						"      radians( :longitud ) ) + \n" + 
						"        sin( radians( :latitud ) ) * \n" + 
						"          sin( radians( ST_X(sel1.last_position) ) ) ) ) \n" + 
						"          distance_at \n" + 
						"FROM \n" + 
						"	(SELECT oj1.idoffer_job, oj1.title, oj1.description, oj1.last_position, CONCAT(u.name, ' ', u.lastname) offer_name FROM bizmodb.offer_job oj1 INNER JOIN bizmodb.user u ON oj1.offer_id = u.iduser WHERE u.country_id = :country_id) sel1 \n" + 
						"INNER JOIN \n" + 
						"	(SELECT oj2.idoffer_job, s.name service_name FROM bizmodb.service s INNER JOIN bizmodb.offer_job oj2 ON s.idservice = oj2.service_id) sel2\n" + 
						"ON sel1.idoffer_job = sel2.idoffer_job \n" + 
						"HAVING distance_at <= :radius LIMIT 10;", 
				resultSetMapping="OfferJobWithDistance"),
		@NamedNativeQuery(
				name = "OfferJob.findAllByServicePositionRadiusCountry", 
				query = "SELECT sel1.idoffer_job, sel1.title, sel1.description, ST_X(sel1.last_position) latitud, ST_Y(sel1.last_position) longitud, sel1.offer_name, sel2.service_name, sel1.offer_id, ( 6371 * \n" + 
						"    acos( \n" + 
						"    cos( radians( :latitud ) ) * \n" + 
						"      cos( radians( ST_X(sel1.last_position) ) ) * \n" + 
						"      cos( radians( ST_Y(sel1.last_position) ) - \n" + 
						"      radians( :longitud ) ) + \n" + 
						"        sin( radians( :latitud ) ) * \n" + 
						"          sin( radians( ST_X(sel1.last_position) ) ) ) ) \n" + 
						"          distance_at \n" + 
						"FROM \n" + 
						"	(SELECT oj1.idoffer_job, oj1.title, oj1.description, oj1.last_position, CONCAT(u.name, ' ', u.lastname) offer_name, oj1.offer_id FROM bizmodb.offer_job oj1 INNER JOIN bizmodb.user u ON oj1.offer_id = u.iduser WHERE u.country_id = :country_id AND oj1.service_id = :service_id) sel1 \n" + 
						"INNER JOIN \n" + 
						"	(SELECT oj2.idoffer_job, s.name service_name FROM bizmodb.service s INNER JOIN bizmodb.offer_job oj2 ON s.idservice = oj2.service_id) sel2\n" + 
						"ON sel1.idoffer_job = sel2.idoffer_job \n" + 
						"HAVING distance_at <= :radius ;", 
				resultSetMapping="OfferJobWithOfferDistance")
		})
@SqlResultSetMappings(value = { 
		@SqlResultSetMapping(
			    name = "OfferJobWithDistance",
				classes = @ConstructorResult(
		                targetClass = OfferJobDistanceValue.class,
		                columns = {
			                    @ColumnResult(name = "idoffer_job", type = Integer.class),
			                    @ColumnResult(name = "title", type = String.class),
			                    @ColumnResult(name = "description", type = String.class),
			                    @ColumnResult(name = "latitud", type = Double.class),
			                    @ColumnResult(name = "longitud", type = Double.class),
			                    @ColumnResult(name = "offer_name", type = String.class),
			                    @ColumnResult(name = "service_name", type = String.class),
			                    @ColumnResult(name = "distance_at", type = Double.class)
		                    }
		                )
			    ),
		@SqlResultSetMapping(
			    name = "OfferJobWithOfferDistance",
				classes = @ConstructorResult(
		                targetClass = OfferJobOfferDistanceValue.class,
		                columns = {
			                    @ColumnResult(name = "idoffer_job", type = Integer.class),
			                    @ColumnResult(name = "title", type = String.class),
			                    @ColumnResult(name = "description", type = String.class),
			                    @ColumnResult(name = "latitud", type = Double.class),
			                    @ColumnResult(name = "longitud", type = Double.class),
			                    @ColumnResult(name = "offer_name", type = String.class),
			                    @ColumnResult(name = "service_name", type = String.class),
			                    @ColumnResult(name = "offer_id", type = Integer.class),
			                    @ColumnResult(name = "distance_at", type = Double.class)
		                    }
		                )
			    )
		})
public class OfferJob implements Serializable {

	private static final long serialVersionUID = 277986652276074662L;

	private Integer id;
	private String title;
	private String description;
	private Date createDate;
	private Point lastPosition;
	private User offer;
	private Service service;
	
	public OfferJob() {
		super();
	}
	
	public OfferJob(Integer id, String title, String description, Date createDate, Point lastPosition, User offer,
			Service service) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.createDate = createDate;
		this.lastPosition = lastPosition;
		this.offer = offer;
		this.service = service;
	}
	
	@Id
	@Column(name = "idoffer_job", nullable = false, unique = true)
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
	@Column(name = "last_position", nullable = false, columnDefinition = "geometry")
	public Point getLastPosition() {
		return lastPosition;
	}
	public void setLastPosition(Point lastPosition) {
		this.lastPosition = lastPosition;
	}
	@JsonIgnore
	@ManyToOne(optional=false)
	@JoinColumn(name = "offer_id")
	public User getOffer() {
		return offer;
	}
	public void setOffer(User offer) {
		this.offer = offer;
	}
	@JsonIgnore
	@ManyToOne(optional=false)
	@JoinColumn(name = "service_id")
	public Service getService() {
		return service;
	}
	public void setService(Service service) {
		this.service = service;
	}
	@JsonGetter("offerName")
	@Transient
	public String getOfferName() {
		return this.offer.getName() + " " + this.offer.getLastname();
	}
	@JsonGetter("serviceName")
	@Transient
	public String getServiceName() {
		return this.service.getName();
	}
	
	@Override
	public String toString() {
		return "OfferJob [id=" + id + ", title=" + title + ", description=" + description + ", createDate=" + createDate
				+ ", lastPosition=" + lastPosition + ", offer=" + offer + ", service=" + service + "]";
	}
}
