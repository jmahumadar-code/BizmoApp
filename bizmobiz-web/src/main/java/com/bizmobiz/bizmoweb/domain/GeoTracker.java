package com.bizmobiz.bizmoweb.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.vividsolutions.jts.geom.Point;

@Entity
@Table(name="geo_tracker", schema="bizmodb")
public class GeoTracker implements Serializable {

	private static final long serialVersionUID = -1847287040474319196L;
	
	private Integer id;
	private Point position;
	private Date date;
	private User user;
	private GeoTrackerType geoTrackerType;
	
	@Id
	@Column(name = "idgeo_tracker", nullable = false, unique = true)
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "position", nullable = false, columnDefinition = "geometry")
	public Point getPosition() {
		return position;
	}
	public void setPosition(Point position) {
		this.position = position;
	}
	@Column(name = "date", nullable = false)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@ManyToOne(optional=false)
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@ManyToOne(optional=false)
	@JoinColumn(name = "geo_tracker_type_id")
	public GeoTrackerType getGeoTrackerType() {
		return geoTrackerType;
	}
	public void setGeoTrackerType(GeoTrackerType geoTrackerType) {
		this.geoTrackerType = geoTrackerType;
	}
	
	@Override
	public String toString() {
		return "GeoTracker [id=" + id + ", position=" + position + ", date=" + date + ", user=" + user
				+ ", geoTrackerType=" + geoTrackerType + "]";
	}
}
