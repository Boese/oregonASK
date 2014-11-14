package org.oregonask.entities;
// Generated Nov 11, 2014 2:42:57 PM by Hibernate Tools 4.3.1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.Hibernate;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * SummerfoodInfo generated by hbm2java
 */
@Entity
@Table(name = "SUMMERFOOD_INFO", catalog = "OREGONASKDB", uniqueConstraints = @UniqueConstraint(columnNames = {
		"YEAR", "SUMMERFOOD_ID" }))
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class SummerfoodInfo implements java.io.Serializable, IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer year;
	private String breakfast;
	private String lunch;
	private String supper;
	private String amSnack;
	private String pmSnack;
	private Summerfood summerfood;

	public SummerfoodInfo() {
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
	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Column(name = "BREAKFAST", length = 45)
	public String getBreakfast() {
		return this.breakfast;
	}

	public void setBreakfast(String breakfast) {
		this.breakfast = breakfast;
	}

	@Column(name = "LUNCH", length = 45)
	public String getLunch() {
		return this.lunch;
	}

	public void setLunch(String lunch) {
		this.lunch = lunch;
	}

	@Column(name = "SUPPER", length = 45)
	public String getSupper() {
		return this.supper;
	}

	public void setSupper(String supper) {
		this.supper = supper;
	}

	@Column(name = "AM_SNACK", length = 45)
	public String getAmSnack() {
		return this.amSnack;
	}

	public void setAmSnack(String amSnack) {
		this.amSnack = amSnack;
	}

	@Column(name = "PM_SNACK", length = 45)
	public String getPmSnack() {
		return this.pmSnack;
	}

	public void setPmSnack(String pmSnack) {
		this.pmSnack = pmSnack;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "SUMMERFOOD_ID")
	public Summerfood getSummerfood() {
		return summerfood;
	}

	public void setSummerfood(Summerfood summerfood) {
		this.summerfood = summerfood;
	}

	@Override
	public void initialize() {
		Hibernate.initialize(this.getSummerfood());
	}

}
