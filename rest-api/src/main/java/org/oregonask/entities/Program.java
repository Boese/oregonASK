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
@Table(name = "program", catalog = "OREGONASK", uniqueConstraints = {
		@UniqueConstraint(columnNames = "PROGRAM_ID"),
		@UniqueConstraint(columnNames = "PROGRAM_NAME") })

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Program implements IEntity, Serializable {
	
	private static final long serialVersionUID = 1488892046889547458L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PROGRAM_ID")
	private long id;
	
	@Column(name = "PROGRAM_NAME", unique = true, nullable = false, length = 45)
	private String  name;
	
	@Column(name = "PROGRAM_DESCRIPTION", nullable = false, length = 200)
	private String  description;
	
	@Column(name = "PROGRAM_HOURS", nullable = true, length = 45)
	private String hours;
	
	@Column(name = "PROGRAM_LICENSE_NUMBER", nullable = false, length = 25)
	private String licenseNumber;
	
	@Column(name = "PROGRAM_OCC_NUMBER", nullable = true, length = 25)
	private String occNumber;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "PROVIDER_ID")
	private Provider programProvider;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "PROGRAM_TYPE_ID")
	private ProgramType programType;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "ACTIVE_DATE_ID")
	private ActiveDate programActiveDate;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinTable(name = "program_location", catalog = "OREGONASK", joinColumns = {
			@JoinColumn(name = "PROGRAM_ID", nullable = false, updatable = false) },
				inverseJoinColumns = { @JoinColumn(name = "LOCATION_ID",
					nullable = false, updatable = false) })
	private Set<Location> programLocations;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinTable(name = "school_program", catalog = "OREGONASK", joinColumns = {
			@JoinColumn(name = "PROGRAM_ID", nullable = false, updatable = false) },
				inverseJoinColumns = { @JoinColumn(name = "SCHOOL_ID",
					nullable = false, updatable = false) })
	private Set<School> programSchools;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinTable(name = "kids_program", catalog = "OREGONASK", joinColumns = {
			@JoinColumn(name = "PROGRAM_ID", nullable = false, updatable = false) },
				inverseJoinColumns = { @JoinColumn(name = "KIDS_SERVED_ID",
					nullable = false, updatable = false) })
	private Set<KidsServed> programKidsCount;
	
	
	/**
	 * Getters & Setters
	 */
	
	// Required for Hibernate
	public Program() {
	}
	
	@JsonProperty("id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@JsonProperty("hours")
	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	@JsonProperty("licenseNumber")
	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	@JsonProperty("occNumber")
	public String getOccNumber() {
		return occNumber;
	}

	public void setOccNumber(String occNumber) {
		this.occNumber = occNumber;
	}

	@JsonProperty("provider")
	public Provider getProvider() {
		return programProvider;
	}
	
	public void setProvider(Provider provider) {
		this.programProvider = provider;
	}

	@JsonProperty("programType")
	public ProgramType getProgramType() {
		return programType;
	}

	public void setProgramType(ProgramType programType) {
		this.programType = programType;
	}

	@JsonProperty("activeDate")
	public ActiveDate getActiveDate() {
		return programActiveDate;
	}

	public void setActiveDate(ActiveDate programActiveDate) {
		this.programActiveDate = programActiveDate;
	}

	@JsonProperty("locations")
	public Set<Location> getLocations() {
		return programLocations;
	}

	public void setLocations(Set<Location> locations) {
		this.programLocations = locations;
	}

	@JsonProperty("schools")
	public Set<School> getSchools() {
		return programSchools;
	}

	public void setSchools(Set<School> schools) {
		this.programSchools = schools;
	}
	
	@JsonProperty("kidsCount")
	public Set<KidsServed> getKidsCount() {
		return programKidsCount;
	}

	public void setKidsCount(Set<KidsServed> programKidsCount) {
		this.programKidsCount = programKidsCount;
	}

	@Override
	public void deepCopy(Object obj) {
		setName(((Program) obj).getName());
		setDescription(((Program) obj).getDescription());
		setHours(((Program) obj).getHours());
		setLicenseNumber(((Program) obj).getLicenseNumber());
		setOccNumber(((Program) obj).getOccNumber());
		setProvider(((Program) obj).getProvider());
		setLocations(((Program) obj).getLocations());
		setSchools(((Program) obj).getSchools());
		setProgramType(((Program) obj).getProgramType());
		setActiveDate(((Program) obj).getActiveDate());
		setKidsCount(((Program) obj).getKidsCount());
	}
	
}
