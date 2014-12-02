package org.oregonask.entities;
// Generated Nov 11, 2014 2:42:57 PM by Hibernate Tools 4.3.1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.Hibernate;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Program generated by hbm2java
 */
@Entity
@Table(name = "PROGRAM", catalog = "OREGONASKDB", uniqueConstraints = @UniqueConstraint(columnNames = {
		"NAME", "CITY", "SCHOOL_SERVED" }))
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Program implements java.io.Serializable,IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String licenseNumber;
	private String notes;
	private String contactName;
	private String email;
	private String phone;
	private String city;
	private String state;
	private String zip;
	private String street;
	private String county;
	private String schoolServed;
	private School school;
	private Sponsor sponsor;
	private Set<ProgramInfo> programinfos;

	public Program() {
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

	@Column(name = "NAME", nullable = false, length = 250)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "LICENSE_NUMBER", length = 45)
	public String getLicenseNumber() {
		return this.licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	@Column(name = "NOTES", length = 1000)
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Column(name = "CONTACT_NAME", length = 1000)
	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	@Column(name = "EMAIL", length = 250)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "PHONE", length = 45)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "CITY", length = 45)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "STATE", length = 45)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "ZIP", length = 45)
	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@Column(name = "STREET", length = 45)
	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Column(name = "COUNTY", length = 45)
	public String getCounty() {
		return this.county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	@Column(name = "SCHOOL_SERVED", length = 250)
	public String getSchoolServed() {
		return this.schoolServed;
	}

	public void setSchoolServed(String schoolServed) {
		this.schoolServed = schoolServed;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "SCHOOL_ID")
	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "SPONSOR_ID")
	public Sponsor getSponsor() {
		return sponsor;
	}

	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}

	@OneToMany(cascade = {CascadeType.ALL})
	@JoinColumn(name="program_id")
	public Set<ProgramInfo> getPrograminfos() {
		return programinfos;
	}

	public void setPrograminfos(Set<ProgramInfo> programinfos) {
		this.programinfos = programinfos;
	}

	@Override
	public void initialize() {
		Hibernate.initialize(this.getSchool());
		Hibernate.initialize(this.getSponsor());
		Hibernate.initialize(this.getPrograminfos());
	}

}
