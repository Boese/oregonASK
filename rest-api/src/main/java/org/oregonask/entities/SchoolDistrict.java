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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "school_district", catalog = "OREGONASK", uniqueConstraints = {
		@UniqueConstraint(columnNames = "SCHOOL_DISTRICT_ID")})

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class SchoolDistrict implements IEntity, Serializable {

	private static final long serialVersionUID = 5233980480820592685L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SCHOOL_DISTRICT_ID")
	private long id;
	
	@Column(name = "SCHOOL_DISTRICT_NUMBER", nullable = false, length = 6)
	private String  number;
	
	@Column(name = "SCHOOL_DISTRICT_NAME", nullable = false, length = 45)
	private String  name;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "EDUCATION_SERVICE_DISTRICT_ID", nullable = false)
	private EducationServiceDistrict educationServiceDistrict;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "locationSchoolDistrict")
	private Set<Location> schoolLocations;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinTable(name = "school_district_grade_count", catalog = "OREGONASK", joinColumns = {
			@JoinColumn(name = "SCHOOL_DISTRICT_ID", nullable = false, updatable = false) },
				inverseJoinColumns = { @JoinColumn(name = "GRADE_COUNT_ID",
					nullable = false, updatable = false) })
	private Set<GradeCount> schoolDistrictGradeCount;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinTable(name = "school_district_ethnicity", catalog = "OREGONASK", joinColumns = {
			@JoinColumn(name = "SCHOOL_DISTRICT_ID", nullable = false, updatable = false) },
				inverseJoinColumns = { @JoinColumn(name = "ETHNICITY_COUNT_ID",
					nullable = false, updatable = false) })
	private Set<EthnicityCount> schoolDistrictEthnicityCount;
	
	
	/**
	 * Getters & Setters
	 */
	
	
	// Needed for Hibernate
	public SchoolDistrict() {
	}

	@JsonProperty("id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonProperty("number")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("educationServiceDistrict")
	public EducationServiceDistrict getEducationServiceDistrict() {
		return educationServiceDistrict;
	}

	public void setEducationServiceDistrict(
			EducationServiceDistrict educationServiceDistrict) {
		this.educationServiceDistrict = educationServiceDistrict;
	}

	@JsonProperty("locations")
	public Set<Location> getSchoolLocations() {
		return schoolLocations;
	}

	public void setSchoolLocations(Set<Location> schoolLocations) {
		this.schoolLocations = schoolLocations;
	}
	
	@JsonProperty("gradeCount")
	public Set<GradeCount> getGradeCount() {
		return schoolDistrictGradeCount;
	}

	public void setGradeCount(Set<GradeCount> schoolDistrictGradeCount) {
		this.schoolDistrictGradeCount = schoolDistrictGradeCount;
	}

	@JsonProperty("ethnicityCount")
	public Set<EthnicityCount> getEthnicityCount() {
		return schoolDistrictEthnicityCount;
	}

	public void setEthnicityCount(
			Set<EthnicityCount> schoolDistrictEthnicityCount) {
		this.schoolDistrictEthnicityCount = schoolDistrictEthnicityCount;
	}

	@Override
	public void deepCopy(Object obj) {
		setName(((SchoolDistrict) obj).getName());
		setNumber(((SchoolDistrict) obj).getNumber());
		setEducationServiceDistrict(((SchoolDistrict) obj).getEducationServiceDistrict());
		setSchoolLocations(((SchoolDistrict) obj).getSchoolLocations());
		setGradeCount(((SchoolDistrict) obj).getGradeCount());
		setEthnicityCount(((SchoolDistrict) obj).getEthnicityCount());
	}
	
}
