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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "school", catalog = "OREGONASK", uniqueConstraints = {
		@UniqueConstraint(columnNames = "SCHOOL_ID") })

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class School implements IEntity, Serializable {
	
	private static final long serialVersionUID = -5356561816348393503L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SCHOOL_ID")
	private long id;
	
	@Column(name = "SCHOOL_CODE", nullable = false, length = 6)
	private Integer schoolCode;
	
	@Column(name = "GRADE_LEVEL", nullable = false, length = 25)
	private String gradeLevel;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "LOCATION_ID", nullable = false)
	private Location schoolLocation;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "programSchools")
	private Set<Program> schoolPrograms;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinTable(name = "school_student", catalog = "OREGONASK", joinColumns = {
			@JoinColumn(name = "SCHOOL_ID", nullable = false, updatable = false) },
				inverseJoinColumns = { @JoinColumn(name = "STUDENT_COUNT_ID",
					nullable = false, updatable = false) })
	private Set<StudentCount> schoolStudentCount;
	
	
	/**
	 * Getters & Setters
	 */
	

	//Needed for Hibernate
	public School() {
	}

	@JsonProperty("id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonProperty("schoolCode")
	public Integer getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(Integer schoolCode) {
		this.schoolCode = schoolCode;
	}
	
	@JsonProperty("gradeLevel")
	public String getGradeLevel() {
		return this.gradeLevel;
	}
	
	public void setGradeLevel(String gradeLevel) {
		this.gradeLevel = gradeLevel;
	}

	@JsonProperty("location")
	public Location getLocation() {
		return schoolLocation;
	}

	public void setLocation(Location schoolLocation) {
		this.schoolLocation = schoolLocation;
	}

	@JsonProperty("programs")
	public Set<Program> getPrograms() {
		return schoolPrograms;
	}

	public void setPrograms(Set<Program> programs) {
		this.schoolPrograms = programs;
	}

	@JsonProperty("studentCount")
	public Set<StudentCount> getStudentCount() {
		return schoolStudentCount;
	}

	public void setStudentCount(Set<StudentCount> studentCount) {
		this.schoolStudentCount = studentCount;
	}
	
	@Override
	public void deepCopy(Object obj) {
		setSchoolCode(((School) obj).getSchoolCode());
		setGradeLevel(((School) obj).getGradeLevel());
		setLocation(((School) obj).getLocation());
		setPrograms(((School) obj).getPrograms());
		setStudentCount(((School) obj).getStudentCount());
	}
	
}
