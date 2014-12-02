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
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * NutritionInfo generated by hbm2java
 */
@Entity
@Table(name = "NUTRITION_INFO", catalog = "OREGONASKDB", uniqueConstraints = @UniqueConstraint(columnNames = {
		"YEAR", "NUTRITION_ID" }))
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class NutritionInfo implements java.io.Serializable, IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer year;
	private Boolean amSnackServed;
	private Boolean breakfastServed;
	private Boolean lunchServed;
	private Boolean pmSnackServed;
	private Boolean supperServed;
	private Boolean eveningSnackServed;
	private Boolean oprJan;
	private Boolean oprFeb;
	private Boolean oprMar;
	private Boolean oprApr;
	private Boolean oprMay;
	private Boolean oprJun;
	private Boolean oprJul;
	private Boolean oprAug;
	private Boolean oprSep;
	private Boolean oprOct;
	private Boolean oprNov;
	private Boolean oprDec;
	private Nutrition nutrition;

	public NutritionInfo() {
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

	@Column(name = "AM_SNACK_SERVED")
	public Boolean getAmSnackServed() {
		return this.amSnackServed;
	}

	public void setAmSnackServed(Boolean amSnackServed) {
		this.amSnackServed = amSnackServed;
	}

	@Column(name = "BREAKFAST_SERVED")
	public Boolean getBreakfastServed() {
		return this.breakfastServed;
	}

	public void setBreakfastServed(Boolean breakfastServed) {
		this.breakfastServed = breakfastServed;
	}

	@Column(name = "LUNCH_SERVED")
	public Boolean getLunchServed() {
		return this.lunchServed;
	}

	public void setLunchServed(Boolean lunchServed) {
		this.lunchServed = lunchServed;
	}

	@Column(name = "PM_SNACK_SERVED")
	public Boolean getPmSnackServed() {
		return this.pmSnackServed;
	}

	public void setPmSnackServed(Boolean pmSnackServed) {
		this.pmSnackServed = pmSnackServed;
	}

	@Column(name = "SUPPER_SERVED")
	public Boolean getSupperServed() {
		return this.supperServed;
	}

	public void setSupperServed(Boolean supperServed) {
		this.supperServed = supperServed;
	}

	@Column(name = "EVENING_SNACK_SERVED")
	public Boolean getEveningSnackServed() {
		return this.eveningSnackServed;
	}

	public void setEveningSnackServed(Boolean eveningSnackServed) {
		this.eveningSnackServed = eveningSnackServed;
	}

	@Column(name = "OPR_JAN")
	public Boolean getOprJan() {
		return this.oprJan;
	}

	public void setOprJan(Boolean oprJan) {
		this.oprJan = oprJan;
	}

	@Column(name = "OPR_FEB")
	public Boolean getOprFeb() {
		return this.oprFeb;
	}

	public void setOprFeb(Boolean oprFeb) {
		this.oprFeb = oprFeb;
	}

	@Column(name = "OPR_MAR")
	public Boolean getOprMar() {
		return this.oprMar;
	}

	public void setOprMar(Boolean oprMar) {
		this.oprMar = oprMar;
	}

	@Column(name = "OPR_APR")
	public Boolean getOprApr() {
		return this.oprApr;
	}

	public void setOprApr(Boolean oprApr) {
		this.oprApr = oprApr;
	}

	@Column(name = "OPR_MAY")
	public Boolean getOprMay() {
		return this.oprMay;
	}

	public void setOprMay(Boolean oprMay) {
		this.oprMay = oprMay;
	}

	@Column(name = "OPR_JUN")
	public Boolean getOprJun() {
		return this.oprJun;
	}

	public void setOprJun(Boolean oprJun) {
		this.oprJun = oprJun;
	}

	@Column(name = "OPR_JUL")
	public Boolean getOprJul() {
		return this.oprJul;
	}

	public void setOprJul(Boolean oprJul) {
		this.oprJul = oprJul;
	}

	@Column(name = "OPR_AUG")
	public Boolean getOprAug() {
		return this.oprAug;
	}

	public void setOprAug(Boolean oprAug) {
		this.oprAug = oprAug;
	}

	@Column(name = "OPR_SEP")
	public Boolean getOprSep() {
		return this.oprSep;
	}

	public void setOprSep(Boolean oprSep) {
		this.oprSep = oprSep;
	}

	@Column(name = "OPR_OCT")
	public Boolean getOprOct() {
		return this.oprOct;
	}

	public void setOprOct(Boolean oprOct) {
		this.oprOct = oprOct;
	}

	@Column(name = "OPR_NOV")
	public Boolean getOprNov() {
		return this.oprNov;
	}

	public void setOprNov(Boolean oprNov) {
		this.oprNov = oprNov;
	}

	@Column(name = "OPR_DEC")
	public Boolean getOprDec() {
		return this.oprDec;
	}

	public void setOprDec(Boolean oprDec) {
		this.oprDec = oprDec;
	}

	@ManyToOne
	@JoinColumn(name="nutrition_id", insertable=false, updatable=false)
	public Nutrition getNutrition() {
		return nutrition;
	}

	public void setNutrition(Nutrition nutrition) {
		this.nutrition = nutrition;
	}

	@Override
	public void initialize() {
		Hibernate.initialize(this.getNutrition());
	}

}
