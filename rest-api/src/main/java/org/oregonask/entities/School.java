package org.oregonask.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "school", catalog = "OREGONASKDB", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"NAME", "CITY"}) })

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class School implements IEntity, Serializable {

	private static final long serialVersionUID = -1880436377900536984L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SCHOOL_ID", unique=true)
	private long id;
	
	@Column(name = "NAME", nullable = false, length = 250)
	private String name;
	
	@Column(name = "ELEMENTARY", nullable = true, length = 1)
	private Integer elementary;
	
	@Column(name = "MIDDLE", nullable = true, length = 1)
	private Integer middle;
	
	@Column(name = "HIGH", nullable = true, length = 1)
	private Integer high;
	
	@Column(name = "CITY", nullable = true, length = 45)
	private String city;
	
	@Column(name = "STATE", nullable = true, length = 45)
	private String state;
	
	@Column(name = "ZIP", nullable = true, length = 45)
	private String zip;
	
	@Column(name = "STREET", nullable = true, length = 45)
	private String street;
	
	@Column(name = "CONGRESSIONAL_DISTRICT", nullable = true, length = 45)
	private String congressional_district;
	
	@Column(name = "COUNTY", nullable = true, length = 45)
	private String county;
	
	@Column(name = "SCHOOL_DISTRICT", nullable = true, length = 45)
	private String school_district;
	
	/**
	 * Getters & Setters
	 */
	
	//Needed for Hibernate
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

	@JsonProperty("elementary")
	public Integer getElementary() {
		return elementary;
	}

	public void setElementary(Integer elementary) {
		this.elementary = elementary;
	}

	@JsonProperty("middle")
	public Integer getMiddle() {
		return middle;
	}

	public void setMiddle(Integer middle) {
		this.middle = middle;
	}

	@JsonProperty("high")
	public Integer getHigh() {
		return high;
	}

	public void setHigh(Integer high) {
		this.high = high;
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

	@JsonProperty("street")
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@JsonProperty("congressional_district")
	public String getCongressional_district() {
		return congressional_district;
	}

	public void setCongressional_district(String congressional_district) {
		this.congressional_district = congressional_district;
	}
	
	@JsonProperty("county")
	public String getCounty() {
		return county;
	}
	
	public void setCounty(String county) {
		this.county = county;
	}
	
	@JsonProperty("school_district")
	public String getSchool_district() {
		return school_district;
	}

	public void setSchool_district(String school_district) {
		this.school_district = school_district;
	}

	@Override
	public void deepCopy(Object obj) {
		setName(((School) obj).getName());
		setElementary(((School) obj).getElementary());
		setMiddle(((School) obj).getMiddle());
		setHigh(((School) obj).getHigh());
		setCity(((School) obj).getCity());
		setState(((School) obj).getState());
		setZip(((School) obj).getZip());
		setStreet(((School) obj).getStreet());
		setCongressional_district(((School) obj).getCongressional_district());
		setCounty(((School) obj).getCounty());
		setSchool_district(((School) obj).getSchool_district());
	}
	
}
