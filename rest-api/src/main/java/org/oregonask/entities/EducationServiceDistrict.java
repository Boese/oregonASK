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
@Table(name = "education_service_district", catalog = "OREGONASK", uniqueConstraints = {
		@UniqueConstraint(columnNames = "EDUCATION_SERVICE_DISTRICT_ID") })

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class EducationServiceDistrict implements IEntity, Serializable {
	
	private static final long serialVersionUID = -3125289037084891255L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EDUCATION_SERVICE_DISTRICT_ID")
	private long id;
	
	@Column(name = "EDUCATION_SERVICE_DISTRICT_NAME", nullable = false, length = 45)
	private String  name;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "educationServiceDistrict")
	private Set<SchoolDistrict> schoolDistricts;
	
	
	/**
	 * Getters & Setters
	 */
	
	
	// Needed for Hibernate
	public EducationServiceDistrict() {
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

	@JsonProperty("schoolDistricts")
	public Set<SchoolDistrict> getSchoolDistricts() {
		return schoolDistricts;
	}

	public void setSchoolDistricts(Set<SchoolDistrict> schoolDistricts) {
		this.schoolDistricts = schoolDistricts;
	}
	
	@Override
	public void deepCopy(Object obj) {
		setName(((EducationServiceDistrict) obj).getName());
		setSchoolDistricts(((EducationServiceDistrict) obj).getSchoolDistricts());
	}

}
