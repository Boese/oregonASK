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
@Table(name = "ethnicity_count", catalog = "OREGONASK", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ETHNICITY_COUNT_ID") })

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class EthnicityCount implements IEntity, Serializable {
	
	private static final long serialVersionUID = 2113114433206519862L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ETHNICITY_COUNT_ID")
	private long id;
	
	@Column(name = "ETHNICITY_COUNT_COUNT", nullable = false, length = 6)
	private Integer count;
	
	@Column(name = "ETHNICITY_COUNT_SCHOOL_YEAR", nullable = false, length = 45)
	private String  schoolYear;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "ETHNICITY_ID", nullable = false)
	private Ethnicity ethnicityCountEthnicity;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "schoolDistrictEthnicityCount")
	private Set<SchoolDistrict> ethnicityCountSchoolDistricts;
	
	
	/**
	 * Getters & Setters
	 */

	
	// Needed for Hibernate
	public EthnicityCount() {
	}

	@JsonProperty("id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonProperty("count")
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@JsonProperty("schoolYear")
	public String getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}

	@JsonProperty("ethnicity")
	public Ethnicity getEthnicity() {
		return ethnicityCountEthnicity;
	}

	public void setEthnicity(Ethnicity ethnicity) {
		this.ethnicityCountEthnicity = ethnicity;
	}

	@JsonProperty("schools")
	public Set<SchoolDistrict> getSchoolDistricts() {
		return ethnicityCountSchoolDistricts;
	}

	public void setSchoolDistricts(Set<SchoolDistrict> schoolDistricts) {
		this.ethnicityCountSchoolDistricts = schoolDistricts;
	}
	
	@Override
	public void deepCopy(Object obj) {
		setCount(((EthnicityCount) obj).getCount());
		setSchoolYear(((EthnicityCount) obj).getSchoolYear());
		setEthnicity(((EthnicityCount) obj).getEthnicity());
		setSchoolDistricts(((EthnicityCount) obj).getSchoolDistricts());
	}
	
	@Override
	public String toString() {
		return "EthnicityCount Count: " + count + 
				", EhtnicityCountEthnicity" + ethnicityCountEthnicity;
	}

}
