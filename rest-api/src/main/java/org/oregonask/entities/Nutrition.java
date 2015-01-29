package org.oregonask.entities;
// Generated Nov 11, 2014 2:42:57 PM by Hibernate Tools 4.3.1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.Hibernate;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Nutrition generated by hbm2java
 */
@Entity
@Table(name = "NUTRITION", uniqueConstraints = {
		@UniqueConstraint(columnNames = "CNP_WEB_SCHOOL_NUMBER"),
		@UniqueConstraint(columnNames = "SITE_NUMBER") })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Nutrition implements java.io.Serializable, IEntity, IUpdateLastEditBy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String siteName;
	private String siteNumber;
	private String cnpWebSchoolNumber;
	private String programType;
	private String street;
	private String city;
	private String zip;
	private String state;
	private String county;
	private String timeStamp;
	private String lastEditBy;
	private School school;
	private Sponsor sponsor;
	private Set<NutritionInfo> NutritionInfo;

	public Nutrition() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "SITE_NAME", nullable = false, length = 250)
	@JsonProperty("SITE_NAME")
	public String getSiteName() {
		return this.siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	@Column(name = "SITE_NUMBER", unique = true, length = 45)
	@JsonProperty("SITE_NUMBER")
	public String getSiteNumber() {
		return this.siteNumber;
	}

	public void setSiteNumber(String siteNumber) {
		this.siteNumber = siteNumber;
	}

	@Column(name = "CNP_WEB_SCHOOL_NUMBER", unique = true, length = 45)
	@JsonProperty("CNP_WEB_SCHOOL_NUMBER")
	public String getCnpWebSchoolNumber() {
		return this.cnpWebSchoolNumber;
	}

	public void setCnpWebSchoolNumber(String cnpWebSchoolNumber) {
		this.cnpWebSchoolNumber = cnpWebSchoolNumber;
	}

	@Column(name = "PROGRAM_TYPE", length = 100)
	@JsonProperty("PROGRAM_TYPE")
	public String getProgramType() {
		return this.programType;
	}

	public void setProgramType(String programType) {
		this.programType = programType;
	}

	@Column(name = "STREET", length = 250)
	@JsonProperty("STREET")
	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Column(name = "CITY", length = 45)
	@JsonProperty("CITY")
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "ZIP", length = 45)
	@JsonProperty("ZIP")
	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@Column(name = "STATE", length = 45)
	@JsonProperty("STATE")
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "COUNTY", length = 45)
	@JsonProperty("COUNTY")
	public String getCounty() {
		return this.county;
	}

	public void setCounty(String county) {
		this.county = county;
	}
	
	@Column(name = "TIME_STAMP")
	@JsonProperty("TIME_STAMP")
	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Column(name = "LAST_EDIT_BY", length = 100)
	@JsonProperty("LAST_EDIT_BY")
	public String getLastEditBy() {
		return lastEditBy;
	}

	public void setLastEditBy(String lastEditBy) {
		this.lastEditBy = lastEditBy;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "SCHOOL_ID")
	@JsonProperty("School")
	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "SPONSOR_ID")
	@JsonProperty("Sponsor")
	public Sponsor getSponsor() {
		return sponsor;
	}

	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}

	@OneToMany(cascade = {CascadeType.ALL})
	@JoinColumn(name="nutrition_id")
	@JsonProperty("NUTRITION_INFO")
	public Set<NutritionInfo> getNutritioninfos() {
		return NutritionInfo;
	}

	public void setNutritioninfos(Set<NutritionInfo> nutritioninfos) {
		this.NutritionInfo = nutritioninfos;
	}

	@Override
	public void initialize() {
		Hibernate.initialize(this.getSchool());
		Hibernate.initialize(this.getSponsor());
		Hibernate.initialize(this.getNutritioninfos());
	}
}
