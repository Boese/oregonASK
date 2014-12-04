package org.oregonask.entities;
// Generated Nov 11, 2014 2:42:57 PM by Hibernate Tools 4.3.1

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.Hibernate;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * SchoolInfo generated by hbm2java
 */
@Entity
@Table(name = "SCHOOL_INFO", catalog = "OREGONASKDB", uniqueConstraints = @UniqueConstraint(columnNames = {
		"YEAR", "SCHOOL_ID" }))
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class SchoolInfo implements java.io.Serializable, IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3497019376174034386L;
	
	
	private Integer id;
	private Integer year;
	private String totalStudentCount;
	private String freeLunchCount;
	private String reducedLunchCount;
	private String percentMeetReading;
	private String percentMinority;
	private String percentWhite;
	private String percentFr;
	private String loGradeOffered;
	private String hiGradeOffered;
	private School School;

	public SchoolInfo() {
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

	@Column(name = "YEAR")
	@JsonProperty("YEAR")
	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Column(name = "TOTAL_STUDENT_COUNT", length = 45)
	@JsonProperty("TOTAL_STUDENT_COUNT")
	public String getTotalStudentCount() {
		return this.totalStudentCount;
	}

	public void setTotalStudentCount(String totalStudentCount) {
		this.totalStudentCount = totalStudentCount;
	}

	@Column(name = "FREE_LUNCH_COUNT", length = 45)
	@JsonProperty("FREE_LUNCH_COUNT")
	public String getFreeLunchCount() {
		return this.freeLunchCount;
	}

	public void setFreeLunchCount(String freeLunchCount) {
		this.freeLunchCount = freeLunchCount;
	}

	@Column(name = "REDUCED_LUNCH_COUNT", length = 45)
	@JsonProperty("REDUCED_LUNCH_COUNT")
	public String getReducedLunchCount() {
		return this.reducedLunchCount;
	}

	public void setReducedLunchCount(String reducedLunchCount) {
		this.reducedLunchCount = reducedLunchCount;
	}

	@Column(name = "PERCENT_MEET_READING", length = 45)
	@JsonProperty("PERCENT_MEET_READING")
	public String getPercentMeetReading() {
		return this.percentMeetReading;
	}

	public void setPercentMeetReading(String percentMeetReading) {
		this.percentMeetReading = percentMeetReading;
	}

	@Column(name = "PERCENT_MINORITY", length = 45)
	@JsonProperty("PERCENT_MINORITY")
	public String getPercentMinority() {
		return this.percentMinority;
	}

	public void setPercentMinority(String percentMinority) {
		this.percentMinority = percentMinority;
	}

	@Column(name = "PERCENT_WHITE", length = 45)
	@JsonProperty("PERCENT_WHITE")
	public String getPercentWhite() {
		return this.percentWhite;
	}

	public void setPercentWhite(String percentWhite) {
		this.percentWhite = percentWhite;
	}

	@Column(name = "PERCENT_FR", length = 45)
	@JsonProperty("PERCENT_FR")
	public String getPercentFr() {
		return this.percentFr;
	}

	public void setPercentFr(String percentFr) {
		this.percentFr = percentFr;
	}

	@Column(name = "LO_GRADE_OFFERED", length = 45)
	@JsonProperty("LO_GRADE_OFFERED")
	public String getLoGradeOffered() {
		return this.loGradeOffered;
	}

	public void setLoGradeOffered(String loGradeOffered) {
		this.loGradeOffered = loGradeOffered;
	}

	@Column(name = "HI_GRADE_OFFERED", length = 45)
	@JsonProperty("HI_GRADE_OFFERED")
	public String getHiGradeOffered() {
		return this.hiGradeOffered;
	}

	public void setHiGradeOffered(String hiGradeOffered) {
		this.hiGradeOffered = hiGradeOffered;
	}
	
	@ManyToOne
	@JoinColumn(name="school_id", insertable=false, updatable=false)
	@JsonProperty("SCHOOL")
	public School getSchool() {
		return School;
	}

	public void setSchool(School school) {
		this.School = school;
	}

	@Override
	public void initialize() {
		Hibernate.initialize(this.getSchool());
	}
}
