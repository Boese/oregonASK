package org.oregonask.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "location", catalog = "OREGONASK", uniqueConstraints = {
		@UniqueConstraint(columnNames = "LOCATION_ID") })

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Location implements IEntity, Serializable {
	
	private static final long serialVersionUID = 819805565489220728L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LOCATION_ID")
	private long id;
	
	@Column(name = "LOCATION_STREET", nullable = false, length = 45)
	private String  street;
	
	@Column(name = "LOCATION_CITY", nullable = false, length = 45)
	private String	city;
	
	@Column(name = "LOCATION_STATE", nullable = false, length = 2)
	private String	state;
	
	@Column(name = "LOCATION_ZIP", nullable = false, length = 5)
	private String  zip;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "COUNTY_ID", nullable = false)
	private County locationCounty;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "SCHOOL_DISTRICT_ID", nullable = false)
	private SchoolDistrict locationSchoolDistrict;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "programLocations")
	private Set<Program> locationPrograms;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "CONGRESSIONAL_DISTRICT_ID", nullable = false)
	private CongressionalDistrict locationCongressionalDistrict;
	
	
	/**
	 * Getters & Setters
	 */
	
	
	// Required for Hibernate
	public Location() {
		
	}

	@JsonProperty("id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonProperty("street")
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@JsonProperty("city")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@JsonProperty("state")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@JsonProperty("zip")
	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@JsonProperty("programs")
	public Set<Program> getPrograms() {
		return locationPrograms;
	}

	public void setPrograms(Set<Program> locationPrograms) {
		this.locationPrograms = locationPrograms;
	}

	@JsonProperty("county")
	public County getCounty() {
		return locationCounty;
	}

	public void setCounty(County county) {
		this.locationCounty = county;
	}

	@JsonProperty("schoolDistrict")
	public SchoolDistrict getSchoolDistrict() {
		return locationSchoolDistrict;
	}

	public void setSchoolDistrict(SchoolDistrict schoolDistrict) {
		this.locationSchoolDistrict = schoolDistrict;
	}
	
	public CongressionalDistrict getCongressionalDistrict() {
		return locationCongressionalDistrict;
	}

	public void setCongressionalDistrict(
			CongressionalDistrict locationCongressionalDistrict) {
		this.locationCongressionalDistrict = locationCongressionalDistrict;
	}

	public void deepCopy(Object obj) {
		setStreet(((Location) obj).getStreet());
		setCity(((Location) obj).getCity());
		setState(((Location) obj).getState());
		setZip(((Location) obj).getZip());
		setSchoolDistrict(((Location) obj).getSchoolDistrict());
		setPrograms(((Location) obj).getPrograms());
		setCounty(((Location) obj).getCounty());
		setCongressionalDistrict(((Location) obj).getCongressionalDistrict());
	}
	
}
