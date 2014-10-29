package org.oregonask.entities;
// Generated Oct 28, 2014 11:43:30 AM by Hibernate Tools 4.3.1

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Nutrition generated by hbm2java
 */
@Entity
@Table(name = "NUTRITION", catalog = "OREGONASKDB", uniqueConstraints = @UniqueConstraint(columnNames = "CNP_WEB_SCHOOL_NUMBER"))
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Nutrition implements java.io.Serializable,IEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NUTRITION_ID", unique = true, nullable = false)
	private Integer nutritionId;
	
	@Column(name = "SITE_NAME", nullable = false, length = 250)
	private String siteName;
	
	@Column(name = "CNP_WEB_SCHOOL_NUMBER", unique = true, nullable = false, length = 45)
	private String cnpWebSchoolNumber;
	
	@Column(name = "STREET", length = 250)
	private String street;
	
	@Column(name = "CITY", length = 45)
	private String city;
	
	@Column(name = "ZIP", length = 45)
	private String zip;
	
	@Column(name = "STATE", length = 45)
	private String state;
	
	@Column(name = "COUNTY", length = 45)
	private String county;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)  
	@JoinTable(name="NUTRITION_LINK", 
		joinColumns=@JoinColumn(name="NUTRITION_ID"),  
		inverseJoinColumns=@JoinColumn(name="SCHOOL_ID")) 
	private School school;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)  
	@JoinTable(name="NUTRITION_LINK", 
		joinColumns=@JoinColumn(name="NUTRITION_ID"),  
		inverseJoinColumns=@JoinColumn(name="SPONSOR_ID")) 
	private Sponsor sponsor;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(name="NUTRITION_INFO_BY_YEAR", 
			joinColumns=@JoinColumn(name="NUTRITION_ID"),
			inverseJoinColumns=@JoinColumn(name="NUTRITION_INFO_ID"))
	private Set<NutritionInfo> nutritionInfo;
	
	public Nutrition() {
	}

	@JsonProperty("id")
	public Integer getNutritionId() {
		return this.nutritionId;
	}

	public void setNutritionId(Integer nutritionId) {
		this.nutritionId = nutritionId;
	}

	
	@JsonProperty("site_name")
	public String getSiteName() {
		return this.siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	
	@JsonProperty("cnp_web_school_number")
	public String getCnpWebSchoolNumber() {
		return this.cnpWebSchoolNumber;
	}

	public void setCnpWebSchoolNumber(String cnpWebSchoolNumber) {
		this.cnpWebSchoolNumber = cnpWebSchoolNumber;
	}

	
	@JsonProperty("street")
	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	
	@JsonProperty("city")
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	
	@JsonProperty("zip")
	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	
	@JsonProperty("state")
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	
	@JsonProperty("county")
	public String getCounty() {
		return this.county;
	}

	public void setCounty(String county) {
		this.county = county;
	}
	
	//** Get School & Sponsor & Nutrition Info
	
	@JsonProperty("school")
	public School getSchool() {
		return this.school;
	}
	
	public void setSchool(School school) {
		this.school = school;
	}

	@JsonProperty("sponsor")
	public Sponsor getSponsor() {
		return sponsor;
	}

	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}

	@JsonProperty("nutrition_info")
	public Set<NutritionInfo> getNutritionInfo() {
		return nutritionInfo;
	}

	public void setNutritionInfo(Set<NutritionInfo> nutritionInfo) {
		this.nutritionInfo = nutritionInfo;
	}

	@Override
	public void deepCopy(Object obj) {
		setNutritionId(((Nutrition) obj).getNutritionId());
		setSiteName(((Nutrition) obj).getSiteName());
		setCnpWebSchoolNumber(((Nutrition) obj).getCnpWebSchoolNumber());
		setStreet(((Nutrition) obj).getStreet());
		setCity(((Nutrition) obj).getCity());
		setZip(((Nutrition) obj).getZip());
		setState(((Nutrition) obj).getState());
		setCounty(((Nutrition) obj).getCounty());
		setSchool(((Nutrition) obj).getSchool());
		setSponsor(((Nutrition) obj).getSponsor());
		setNutritionInfo(((Nutrition) obj).getNutritionInfo());
	}

}
