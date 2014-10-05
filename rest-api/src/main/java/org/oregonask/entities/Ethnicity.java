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
@Table(name = "ethnicity", catalog = "OREGONASK", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ETHNICITY_ID") })

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Ethnicity implements IEntity, Serializable {
	
	private static final long serialVersionUID = -5135174340698101878L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ETHNICITY_ID")
	private long id;
	
	@Column(name = "ETHNICITY_CATEGORY", nullable = false, length = 45)
	private String category;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ethnicityCountEthnicity")
	private Set<EthnicityCount> ethnicityCount;
	
	
	/**
	 * Getters & Setters
	 */
	
	
	// Needed for Hibernate
	public Ethnicity() {	
	}

	@JsonProperty("id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonProperty("category")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@JsonProperty("count")
	public Set<EthnicityCount> getEthnicityCount() {
		return ethnicityCount;
	}

	public void setEthnicityCount(Set<EthnicityCount> ethnicityCount) {
		this.ethnicityCount = ethnicityCount;
	}
	
	@Override
	public void deepCopy(Object obj) {
		setCategory(((Ethnicity) obj).getCategory());
		setEthnicityCount(((Ethnicity) obj).getEthnicityCount());
	}
	
	@Override
	public String toString() {
		String result = "";
		for (EthnicityCount ec : ethnicityCount) {
			result += ec.getEthnicity() + " ";
		}
		return "Ethnicity Category: " + category +
				"Ethnicities: " + result;
	}

}
