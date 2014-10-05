package org.oregonask.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
@Table(name = "county", catalog = "OREGONASK", uniqueConstraints = {
		@UniqueConstraint(columnNames = "COUNTY_ID") })

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class County implements IEntity, Serializable {

	private static final long serialVersionUID = -5349833879206020541L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COUNTY_ID")
	private long id;
	
	@Column(name = "COUNTY_NAME", nullable = false, length = 45)
	private String  name;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "locationCounty")
	private Set<Location> countyLocations;
	
	
	/**
	 * Getters & Setters
	 */
	
	
	// Needed for Hibernate
	public County() {
	}
	
	@JsonProperty("id")
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@JsonProperty("name")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonProperty("locations")
	public Set<Location> getLocations() {
		return countyLocations;
	}

	public void setLocations(Set<Location> locations) {
		this.countyLocations = locations;
	}
	
	@Override
	public void deepCopy(Object obj) {
		setName(((County) obj).getName());
		setLocations(((County) obj).getLocations());
	}
	
}
