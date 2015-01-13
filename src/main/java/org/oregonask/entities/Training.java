package org.oregonask.entities;

// Generated Jan 2, 2015 10:07:55 AM by Hibernate Tools 4.3.1

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.Hibernate;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Training generated by hbm2java
 */
@Entity
@Table(name = "TRAINING")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Training implements java.io.Serializable,IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String site;
	private String trainingAttended;
	private String hoursAttended;
	private String trainingGroup;
	private String ethnicity;
	private String MF;
	private String level;
	private Contact contact;

	public Training() {
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

	@Column(name = "SITE", length = 100)
	@JsonProperty("SITE")
	public String getSite() {
		return this.site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	@Column(name = "TRAINING_ATTENDED", length = 100)
	@JsonProperty("TRAINING_ATTENDED")
	public String getTrainingAttended() {
		return this.trainingAttended;
	}

	public void setTrainingAttended(String trainingAttended) {
		this.trainingAttended = trainingAttended;
	}

	@Column(name = "HOURS_ATTENDED", length = 45)
	@JsonProperty("HOURS_ATTENDED")
	public String getHoursAttended() {
		return this.hoursAttended;
	}

	public void setHoursAttended(String hoursAttended) {
		this.hoursAttended = hoursAttended;
	}

	@Column(name = "TRAINING_GROUP", length = 100)
	@JsonProperty("TRAINING_GROUP")
	public String getTrainingGroup() {
		return this.trainingGroup;
	}

	public void setTrainingGroup(String trainingGroup) {
		this.trainingGroup = trainingGroup;
	}

	@Column(name = "ETHNICITY", length = 45)
	@JsonProperty("ETHNICITY")
	public String getEthnicity() {
		return this.ethnicity;
	}

	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}

	@Column(name = "MF", length = 45)
	@JsonProperty("MF")
	public String getMF() {
		return this.MF;
	}

	public void setMF(String MF) {
		this.MF = MF;
	}

	@Column(name = "LEVEL", length = 45)
	@JsonProperty("LEVEL")
	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@ManyToOne
	@JoinColumn(name = "CONTACT_FK", nullable=false, insertable=false, updatable=false)
	@JsonProperty("CONTACT")
	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	@Override
	public void initialize() {
		Hibernate.initialize(this.getContact());
	}

}