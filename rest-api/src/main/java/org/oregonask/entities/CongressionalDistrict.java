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
@Table(name = "congressional_district", catalog = "OREGONASK", uniqueConstraints = {
		@UniqueConstraint(columnNames = "CONGRESSIONAL_DISTRICT_ID") })

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class CongressionalDistrict implements IEntity, Serializable {
	
	private static final long serialVersionUID = 1870604751762635546L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CONGRESSIONAL_DISTRICT_ID")
	private long id;
	
	@Column(name = "CONGRESSIONAL_DISTRICT_NAME", nullable = false, length = 45)
	private String name;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "locationCongressionalDistrict")
	private Set<Location> congressionalDistrictLocations;

	
	/**
	 * Getters & Setters
	 */
	
	
	// Needed for Hibernate
	public CongressionalDistrict() {
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
		return congressionalDistrictLocations;
	}

	public void setLocations(
			Set<Location> congressionalDistrictLocations) {
		this.congressionalDistrictLocations = congressionalDistrictLocations;
	}
	
	@Override
	public void deepCopy(Object obj) {
		setName(((CongressionalDistrict) obj).getName());
		setLocations(((CongressionalDistrict) obj).getLocations());
	}

}
