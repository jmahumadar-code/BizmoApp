package com.bizmobiz.bizmoweb.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import com.bizmobiz.bizmoweb.value.ServiceBasic;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "service", schema = "bizmodb")
@NamedNativeQuery(name = "Service.findByTagIds", query = "SELECT DISTINCT s.idservice, s.name, s.description FROM service s INNER JOIN service_tag st ON s.idservice = st.service_id WHERE st.tag_id IN (:tagIds) ORDER BY s.name ASC", resultSetMapping="ServiceBasic")
@SqlResultSetMapping(
	    name = "ServiceBasic",
		classes = @ConstructorResult(
                targetClass = ServiceBasic.class,
                columns = {
                    @ColumnResult(name = "idservice", type = Integer.class),
                    @ColumnResult(name = "name"),
                    @ColumnResult(name = "description")
                    }
                )
	    )
public class Service implements Serializable {

	private static final long serialVersionUID = -7274153848703651032L;

	private Integer id;
	private String name;
	private String description;
	private Integer parentId;
	private LevelService levelService;
	private List<Tag> tags;
	private List<Service> childrens;

	@Id
	@Column(name = "idservice", nullable = false, unique = true)
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", nullable = true)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "parent_service_id", nullable = true)
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@ManyToOne(optional = false)
	@JoinColumn(name = "level_service_id")
	public LevelService getLevelService() {
		return levelService;
	}

	public void setLevelService(LevelService levelService) {
		this.levelService = levelService;
	}

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "service_tag", joinColumns = @JoinColumn(name = "service_id", referencedColumnName = "idservice"), inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "idtag"))
	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	@OneToMany
	@JoinColumn(name = "parent_service_id", referencedColumnName = "idservice")
	public List<Service> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<Service> childrens) {
		this.childrens = childrens;
	}

	@Override
	public String toString() {
		return "Service [id=" + id + ", name=" + name + ", description=" + description + ", parentId=" + parentId
				+ ", levelService=" + levelService + ", tags=" + tags + ", childrens=" + childrens + "]";
	}
}
