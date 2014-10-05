package org.oregonask.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "grade_count", catalog = "OREGONASK", uniqueConstraints = {
		@UniqueConstraint(columnNames = "GRADE_COUNT_ID") })

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class GradeCount implements IEntity, Serializable {
	
	private static final long serialVersionUID = 5208089557661670757L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "GRADE_COUNT_ID")
	private long id;
	
	@Column(name = "GRADE_COUNT_COUNT", nullable = false, length = 6)
	private Integer count;
	
	@Column(name = "GRADE_COUNT_GRADE", nullable = false, length = 25)
	private String grade;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "schoolDistrictGradeCount")
	private Set<SchoolDistrict> gradeCountSchoolDistrict;
	
	
	/**
	 * Getters & Setters
	 */
	
	
	// Needed for Hibernate
	public GradeCount() {		
	}

	@JsonProperty("id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonProperty("gradeCount")
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@JsonProperty("grade")
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	@JsonProperty("schoolDistricts")
	public Set<SchoolDistrict> getSchoolDistricts() {
		return gradeCountSchoolDistrict;
	}

	public void setSchoolDistricts(Set<SchoolDistrict> gradeCountSchoolDistrict) {
		this.gradeCountSchoolDistrict = gradeCountSchoolDistrict;
	}

	@Override
	public void deepCopy(Object obj) {
		setCount(((GradeCount) obj).getCount());
		setGrade(((GradeCount) obj).getGrade());
		setSchoolDistricts(((GradeCount) obj).getSchoolDistricts());
	}

}
