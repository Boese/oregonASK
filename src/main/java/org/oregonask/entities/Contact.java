package org.oregonask.entities;

// Generated Jan 2, 2015 10:07:55 AM by Hibernate Tools 4.3.1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.Hibernate;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Contact generated by hbm2java
 */
@Entity
@Table(name = "CONTACT", uniqueConstraints = @UniqueConstraint(columnNames = {
		"FIRSTNAME", "LASTNAME", "EMAIL" }))
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Contact implements java.io.Serializable,IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String district;
	private String schoolDistrict;
	private String notes;
	private Set<Category> categorys = new HashSet<Category>();
	private Set<EmailAlternate> emailalternates = new HashSet<EmailAlternate>();
	private Set<PhoneAlternate> phonealternates = new HashSet<PhoneAlternate>();
	private Set<Training> trainings = new HashSet<Training>();
	private Set<Association> associations = new HashSet<Association>();

	public Contact() {}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "FIRSTNAME", nullable = false, length = 100)
	@JsonProperty("FIRSTNAME")
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "LASTNAME", nullable = false, length = 100)
	@JsonProperty("LASTNAME")
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "EMAIL", length = 100)
	@JsonProperty("EMAIL")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "PHONE", length = 45)
	@JsonProperty("PHONE")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "DISTRICT", length = 45)
	@JsonProperty("DISTRICT")
	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	@Column(name = "SCHOOLDISTRICT", length = 100)
	@JsonProperty("SCHOOLDISTRICT")
	public String getSchoolDistrict() {
		return this.schoolDistrict;
	}

	public void setSchoolDistrict(String schoolDistrict) {
		this.schoolDistrict = schoolDistrict;
	}

	@Column(name = "NOTES", length = 1000)
	@JsonProperty("NOTES")
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "CATEGORY_JOIN", joinColumns = { 
			@JoinColumn(name = "CONTACT_FK", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "CATEGORY_FK", 
					nullable = false, updatable = false) })
	@JsonProperty("CATEGORYS")
	public Set<Category> getCategorys() {
		return categorys;
	}

	public void setCategorys(Set<Category> categorys) {
		this.categorys = categorys;
	}

	@OneToMany(cascade = {CascadeType.ALL},  orphanRemoval=true)
	@JoinColumn(name="CONTACT_FK", nullable=false)
	@JsonProperty("EMAILALTERNATES")
	public Set<EmailAlternate> getEmail_alternates() {
		return emailalternates;
	}

	public void setEmail_alternates(Set<EmailAlternate> email_alternates) {
		this.emailalternates = email_alternates;
	}

	@OneToMany(cascade = {CascadeType.ALL},  orphanRemoval=true)
	@JoinColumn(name="CONTACT_FK", nullable=false)
	@JsonProperty("PHONEALTERNATES")
	public Set<PhoneAlternate> getPhone_alternates() {
		return phonealternates;
	}

	public void setPhone_alternates(Set<PhoneAlternate> phone_alternates) {
		this.phonealternates = phone_alternates;
	}

	@OneToMany(cascade = {CascadeType.ALL},  orphanRemoval=true)
	@JoinColumn(name="CONTACT_FK", nullable=false)
	@JsonProperty("TRAININGS")
	public Set<Training> getTrainings() {
		return trainings;
	}

	public void setTrainings(Set<Training> trainings) {
		this.trainings = trainings;
	}

	@OneToMany(cascade = {CascadeType.ALL},  orphanRemoval=true)
	@JoinColumn(name="CONTACT_FK", nullable=false)
	@JsonProperty("ASSOCIATIONS")
	public Set<Association> getAssociations() {
		return associations;
	}

	public void setAssociations(Set<Association> associations) {
		this.associations = associations;
	}

	@Override
	public void initialize() {
		Hibernate.initialize(this.getCategorys());
		Hibernate.initialize(this.getEmail_alternates());
		Hibernate.initialize(this.getPhone_alternates());
		Hibernate.initialize(this.getTrainings());
		Hibernate.initialize(this.getAssociations());
	}

}