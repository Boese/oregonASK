package org.oregonask.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "school_info", catalog = "OREGONASKDB")

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class School_Info implements IEntity, Serializable {

	private static final long serialVersionUID = -181231742896297521L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SCHOOL_INFO_ID", unique=true)
	private long id;
	
	@Column(name = "TOTAL_STUDENT_COUNT", nullable = true, length = 45)
	private String total_student_count;
	
	@Column(name = "FREE_LUNCH_COUNT", nullable = true, length = 45)
	private String free_lunch_count;
	
	@Column(name = "REDUCED_LUNCH_COUNT", nullable = true, length = 45)
	private String reduced_lunch_count;
	
	@Column(name = "PERCENT_MEET_READING", nullable = true, length = 45)
	private String percent_meet_reading;
	
	@Column(name = "PERCENT_MINORITY", nullable = true, length = 45)
	private String percent_minority;
	
	@Column(name = "PERCENT_WHITE", nullable = true, length = 45)
	private String percent_white;
	
	@Column(name = "PERCENT_FR", nullable = true, length = 45)
	private String percent_fr;
	
	@Column(name = "LO_GRADE_OFFERED", nullable = true, length = 45)
	private String lo_grade_offered;
	
	@Column(name = "HI_GRADE_OFFERED", nullable = true, length = 45)
	private String hi_grade_offered;
	
	/**
	 * Getters & Setters
	 */
	
	//Needed for Hibernate
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTotal_student_count() {
		return total_student_count;
	}

	public void setTotal_student_count(String total_student_count) {
		this.total_student_count = total_student_count;
	}

	public String getFree_lunch_count() {
		return free_lunch_count;
	}

	public void setFree_lunch_count(String free_lunch_count) {
		this.free_lunch_count = free_lunch_count;
	}

	public String getReduced_lunch_count() {
		return reduced_lunch_count;
	}

	public void setReduced_lunch_count(String reduced_lunch_count) {
		this.reduced_lunch_count = reduced_lunch_count;
	}

	public String getPercent_meet_reading() {
		return percent_meet_reading;
	}

	public void setPercent_meet_reading(String percent_meet_reading) {
		this.percent_meet_reading = percent_meet_reading;
	}

	public String getPercent_minority() {
		return percent_minority;
	}

	public void setPercent_minority(String percent_minority) {
		this.percent_minority = percent_minority;
	}

	public String getPercent_white() {
		return percent_white;
	}

	public void setPercent_white(String percent_white) {
		this.percent_white = percent_white;
	}

	public String getPercent_fr() {
		return percent_fr;
	}

	public void setPercent_fr(String percent_fr) {
		this.percent_fr = percent_fr;
	}

	public String getLo_grade_offered() {
		return lo_grade_offered;
	}

	public void setLo_grade_offered(String lo_grade_offered) {
		this.lo_grade_offered = lo_grade_offered;
	}

	public String getHi_grade_offered() {
		return hi_grade_offered;
	}

	public void setHi_grade_offered(String hi_grade_offered) {
		this.hi_grade_offered = hi_grade_offered;
	}
	
	@Override
	public void deepCopy(Object obj) {
		setTotal_student_count(((School_Info) obj).getTotal_student_count());
		setFree_lunch_count(((School_Info) obj).getFree_lunch_count());
		setReduced_lunch_count(((School_Info) obj).getReduced_lunch_count());
		setPercent_meet_reading(((School_Info) obj).getPercent_meet_reading());
		setPercent_minority(((School_Info) obj).getPercent_minority());
		setPercent_white(((School_Info) obj).getPercent_white());
		setPercent_fr(((School_Info) obj).getPercent_fr());
		setLo_grade_offered(((School_Info) obj).getLo_grade_offered());
		setHi_grade_offered(((School_Info) obj).getHi_grade_offered());
	}

}
