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
@Table(name = "program_type", catalog = "OREGONASK", uniqueConstraints = {
		@UniqueConstraint(columnNames = "PROGRAM_TYPE_ID"),
		@UniqueConstraint(columnNames = "PROGRAM_TYPE_NAME") })

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class ProgramType implements IEntity, Serializable {
	
	private static final long serialVersionUID = -4148945586705587168L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PROGRAM_TYPE_ID")
	private long id;
	
	@Column(name = "PROGRAM_TYPE_NAME", unique = true, nullable = false, length = 45)
	private String name;
	
	@Column(name = "PROGRAM_TYPE_DESCRIPTION", length = 200)
	private String description;
	
	@Column(name = "PROGRAM_TYPE_FOOD_NUMBER", length = 25)
	private String foodNumber;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "programType")
	private Set<Program> programs;
	
	
	/**
	 * Getters & Setters
	 */
	

	// Needed for Hibernate
	public ProgramType() {
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
	
	@JsonProperty("foodNumber")
	public String getFoodNumber() {
		return foodNumber;
	}

	public void setFoodNumber(String foodNumber) {
		this.foodNumber = foodNumber;
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
		setName(((ProgramType) obj).getName());
		setDescription(((ProgramType) obj).getDescription());
		setFoodNumber(((ProgramType) obj).getFoodNumber());
		setPrograms(((ProgramType) obj).getPrograms());
	}

}
