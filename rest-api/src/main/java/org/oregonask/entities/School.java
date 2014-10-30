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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * School generated by hbm2java
 */
@Entity
@Table(name = "SCHOOL", catalog = "OREGONASKDB", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "NAME", "COUNTY" }),
		@UniqueConstraint(columnNames = "INSTITUTION_ID") })
public class School implements java.io.Serializable,IEntity {

	private static final long serialVersionUID = 5054829261229547595L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SCHOOL_ID", unique = true, nullable = false)
	private Integer schoolId;
	
	@Column(name = "INSTITUTION_ID", unique = true)
	private Integer institutionId;
	
	@Column(name = "NAME", nullable = false, length = 250)
	private String name;
	
	@Column(name = "ELEMENTARY")
	private Boolean elementary;
	
	@Column(name = "MIDDLE")
	private Boolean middle;
	
	@Column(name = "HIGH")
	private Boolean high;
	
	@Column(name = "CITY", length = 45)
	private String city;
	
	@Column(name = "STATE", length = 45)
	private String state;
	
	@Column(name = "ZIP", length = 45)
	private String zip;
	
	@Column(name = "STREET", length = 45)
	private String street;
	
	@Column(name = "CONGRESSIONAL_DISTRICT", length = 45)
	private String congressionalDistrict;
	
	@Column(name = "COUNTY", length = 45)
	private String county;
	
	@Column(name = "SCHOOL_DISTRICT", length = 45)
	private String schoolDistrict;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)  
    	@JoinTable(name="NUTRITION_LINK",  
    		joinColumns=@JoinColumn(name="SCHOOL_ID"),  
    		inverseJoinColumns=@JoinColumn(name="NUTRITION_ID")) 
	Set<Nutrition> nutritionBySchool;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)  
	@JoinTable(name="SUMMERFOOD_LINK",  
		joinColumns=@JoinColumn(name="SCHOOL_ID"),  
		inverseJoinColumns=@JoinColumn(name="SUMMERFOOD_ID")) 
	Set<Summerfood> summerfoodBySchool;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)  
	@JoinTable(name="YMCACW_LINK",  
		joinColumns=@JoinColumn(name="SCHOOL_ID"),  
		inverseJoinColumns=@JoinColumn(name="program_ID")) 
	Set<Summerfood> programBySchool;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(name="SCHOOL_INFO_BY_YEAR", 
			joinColumns=@JoinColumn(name="SCHOOL_ID"),
			inverseJoinColumns=@JoinColumn(name="SCHOOL_INFO_ID"))
	private Set<SchoolInfo> schoolInfo;
	
	public School() {
	}

	public Integer getSchoolId() {
		return this.schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	public Integer getInstitutionId() {
		return this.institutionId;
	}

	public void setInstitutionId(Integer institutionId) {
		this.institutionId = institutionId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getElementary() {
		return this.elementary;
	}

	public void setElementary(Boolean elementary) {
		this.elementary = elementary;
	}

	public Boolean getMiddle() {
		return this.middle;
	}

	public void setMiddle(Boolean middle) {
		this.middle = middle;
	}

	public Boolean getHigh() {
		return this.high;
	}

	public void setHigh(Boolean high) {
		this.high = high;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCongressionalDistrict() {
		return this.congressionalDistrict;
	}

	public void setCongressionalDistrict(String congressionalDistrict) {
		this.congressionalDistrict = congressionalDistrict;
	}

	public String getCounty() {
		return this.county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getSchoolDistrict() {
		return this.schoolDistrict;
	}

	public void setSchoolDistrict(String schoolDistrict) {
		this.schoolDistrict = schoolDistrict;
	}

	public Set<Nutrition> getNutritionBySchool() {
		return nutritionBySchool;
	}


	public void setNutritionBySchool(Set<Nutrition> nutritionBySchool) {
		this.nutritionBySchool = nutritionBySchool;
	}

	public Set<Summerfood> getSummerfoodBySchool() {
		return summerfoodBySchool;
	}


	public void setSummerfoodBySchool(Set<Summerfood> summerfoodBySchool) {
		this.summerfoodBySchool = summerfoodBySchool;
	}

	public Set<Summerfood> getProgramBySchool() {
		return programBySchool;
	}


	public void setProgramBySchool(Set<Summerfood> programBySchool) {
		this.programBySchool = programBySchool;
	}

	public Set<SchoolInfo> getSchoolInfo() {
		return schoolInfo;
	}


	public void setSchoolInfo(Set<SchoolInfo> schoolInfo) {
		this.schoolInfo = schoolInfo;
	}


	@Override
	public void deepCopy(Object obj) {
		setSchoolId(((School) obj).getSchoolId());
		setInstitutionId(((School) obj).getInstitutionId());
		setName(((School) obj).getName());
		setElementary(((School) obj).getElementary());
		setMiddle(((School) obj).getElementary());
		setHigh(((School) obj).getHigh());
		setCity(((School) obj).getCity());
		setState(((School) obj).getState());
		setZip(((School) obj).getZip());
		setStreet(((School) obj).getStreet());
		setCongressionalDistrict(((School) obj).getCongressionalDistrict());
		setCounty(((School) obj).getCounty());
		setSchoolDistrict(((School) obj).getSchoolDistrict());
		setNutritionBySchool(((School) obj).getNutritionBySchool());
		setSummerfoodBySchool(((School) obj).getSummerfoodBySchool());
		setProgramBySchool(((School) obj).getProgramBySchool());
		setSchoolInfo(((School) obj).getSchoolInfo());
	}

}
