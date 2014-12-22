package org.oregonask.entities;
// Generated Nov 11, 2014 2:42:57 PM by Hibernate Tools 4.3.1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.Hibernate;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Sponsor generated by hbm2java
 */
@Entity
@Table(name = "SPONSOR", catalog = "OREGONASKDB", uniqueConstraints = {
		@UniqueConstraint(columnNames = "NAME"),
		@UniqueConstraint(columnNames = "AGR_NUMBER") })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Sponsor implements java.io.Serializable, IEntity,IUpdateLastEditBy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String agrNumber;
	private String sponsorType;
	private String timeStamp;
	private String lastEditBy;
	private Set<Summerfood> SummerFood;
	private Set<Nutrition> Nutrition;
	private Set<Program> Program;

	public Sponsor() {
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

	@Column(name = "NAME", unique = true, nullable = false, length = 250)
	@JsonProperty("NAME")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "AGR_NUMBER", unique = true, length = 45)
	@JsonProperty("AGR_NUMBER")
	public String getAgrNumber() {
		return this.agrNumber;
	}

	public void setAgrNumber(String agrNumber) {
		this.agrNumber = agrNumber;
	}

	@Column(name = "SPONSOR_TYPE", length = 45)
	@JsonProperty("SPONSOR_TYPE")
	public String getSponsorType() {
		return this.sponsorType;
	}

	public void setSponsorType(String sponsorType) {
		this.sponsorType = sponsorType;
	}
	
	@Column(name = "TIME_STAMP")
	@JsonProperty("TIME_STAMP")
	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Column(name = "LAST_EDIT_BY", length = 100)
	@JsonProperty("LAST_EDIT_BY")
	public String getLastEditBy() {
		return lastEditBy;
	}

	public void setLastEditBy(String lastEditBy) {
		this.lastEditBy = lastEditBy;
	}

	@OneToMany(mappedBy="sponsor", fetch=FetchType.LAZY)
	@JsonProperty("SUMMERFOOD")
	public Set<Summerfood> getSummerfoods() {
		return SummerFood;
	}

	public void setSummerfoods(Set<Summerfood> summerfoods) {
		this.SummerFood = summerfoods;
	}

	@OneToMany(mappedBy="sponsor", fetch=FetchType.LAZY)
	@JsonProperty("NUTRITION")
	public Set<Nutrition> getNutritions() {
		return Nutrition;
	}

	public void setNutritions(Set<Nutrition> nutritions) {
		this.Nutrition = nutritions;
	}

	@OneToMany(mappedBy="sponsor", fetch=FetchType.LAZY)
	@JsonProperty("PROGRAM")
	public Set<Program> getPrograms() {
		return Program;
	}

	public void setPrograms(Set<Program> programs) {
		this.Program = programs;
	}
	
	@Override
	public void initialize() {
		Hibernate.initialize(this.getSummerfoods());
		Hibernate.initialize(this.getNutritions());
		Hibernate.initialize(this.getPrograms());
	}

}
