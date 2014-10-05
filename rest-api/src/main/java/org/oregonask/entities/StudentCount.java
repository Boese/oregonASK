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
@Table(name = "student_count", catalog = "OREGONASK", uniqueConstraints = {
		@UniqueConstraint(columnNames = "STUDENT_COUNT_ID") })

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class StudentCount implements IEntity, Serializable {
	
	private static final long serialVersionUID = -623068512158313133L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "STUDENT_COUNT_ID")
	private long id;
	
	@Column(name = "STUDENT_COUNT_COUNT", nullable = false, length = 6)
	private Integer count;
	
	@Column(name = "STUDENT_COUNT_SCHOOL_YEAR", nullable = false, length = 25)
	private String schoolYear;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "schoolStudentCount")
	private Set<School> studentCountSchools;
	
	
	/**
	 * Getters & Setters
	 */
	
	
	// Needed for Hibernate
	public StudentCount() {		
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

	@JsonProperty("schools")
	public Set<School> getSchools() {
		return studentCountSchools;
	}

	public void setSchools(Set<School> studentCountSchools) {
		this.studentCountSchools = studentCountSchools;
	}
	
	@Override
	public void deepCopy(Object obj) {
		setCount(((StudentCount) obj).getCount());
		setSchoolYear(((StudentCount) obj).getSchoolYear());
		setSchools(((StudentCount) obj).getSchools());
	}

}
