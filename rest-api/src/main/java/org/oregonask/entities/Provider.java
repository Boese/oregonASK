package org.oregonask.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "provider", catalog = "OREGONASK", uniqueConstraints = {
		@UniqueConstraint(columnNames = "PROVIDER_ID"),
		@UniqueConstraint(columnNames = "PROVIDER_NAME") })

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Provider implements IEntity, Serializable {
	
	private static final long serialVersionUID = -7807126263580962750L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PROVIDER_ID")
	private long id;
	
	@Column(name = "PROVIDER_NAME", unique = true, nullable = false, length = 40)
	private String 	name;
	
	@Column(name = "PROVIDER_STREET", nullable = false, length = 60)
	private String  street;
	
	@Column(name = "PROVIDER_CITY", nullable = false, length = 30)
	private String  city;
	
	@Column(name = "PROVIDER_STATE", nullable = false, length = 2)
	private String	state;
	
	@Column(name = "PROVIDER_ZIP", nullable = false, length = 5)
	private Integer	zip;
	
	@Column(name = "PROVIDER_PHONE", nullable = false, length = 10)
	private String	phone;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "programProvider")
	private Set<Program> programs;
	
	
	/**
	 * Getters & Setters
	 */
	
	
	public Provider() {
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

	@JsonProperty("street")
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@JsonProperty("city")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@JsonProperty("state")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@JsonProperty("zip")
	public Integer getZip() {
		return zip;
	}

	public void setZip(Integer zip) {
		this.zip = zip;
	}

	@JsonProperty("phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@JsonProperty("programs")
	public Set<Program> getPrograms() {
		return programs;
	}
	
	public void setPrograms(Set<Program> programs) {
		this.programs = programs;
	}
	
	@Override
	public void deepCopy(Object obj) {
		setName(((Provider) obj).getName());
		setStreet(((Provider) obj).getStreet());
		setCity(((Provider) obj).getCity());
		setState(((Provider) obj).getState());
		setZip(((Provider) obj).getZip());
		setPhone(((Provider) obj).getPhone());
		setPrograms(((Provider) obj).getPrograms());
	}

}
