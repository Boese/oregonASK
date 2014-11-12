package org.oregonask.entities;
// Generated Nov 11, 2014 2:42:57 PM by Hibernate Tools 4.3.1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * SummerfoodInfo generated by hbm2java
 */
@Entity
@Table(name = "SUMMERFOOD_INFO", catalog = "OREGONASKDB", uniqueConstraints = @UniqueConstraint(columnNames = {
		"YEAR", "SUMMERFOOD_ID" }))
public class SummerfoodInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer year;
	private Integer summerfoodId;
	private String breakfast;
	private String lunch;
	private String supper;
	private String amSnack;
	private String pmSnack;

	public SummerfoodInfo() {
	}

	public SummerfoodInfo(Integer year, Integer summerfoodId, String breakfast,
			String lunch, String supper, String amSnack, String pmSnack) {
		this.year = year;
		this.summerfoodId = summerfoodId;
		this.breakfast = breakfast;
		this.lunch = lunch;
		this.supper = supper;
		this.amSnack = amSnack;
		this.pmSnack = pmSnack;
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

	@Column(name = "SUMMERFOOD_ID")
	public Integer getSummerfoodId() {
		return this.summerfoodId;
	}

	public void setSummerfoodId(Integer summerfoodId) {
		this.summerfoodId = summerfoodId;
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

}
