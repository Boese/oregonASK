package org.oregonask.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "kids_served", catalog = "OREGONASK", uniqueConstraints = {
		@UniqueConstraint(columnNames = "KIDS_SERVED_ID") })

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class KidsServed implements IEntity, Serializable {
	
	private static final long serialVersionUID = 1356545726137690137L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "KIDS_SERVED_ID")
	private long id;
	
	@Column(name = "KIDS_SERVED_COUNT", nullable = false, length = 6)
	private Integer count;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "programKidsCount")
	private Set<Program> programs;
	
	
	/**
	 * Getters & Setters
	 */
	
	
	// Needed for Hibernate
	public KidsServed() {
	}
	
	@JsonProperty("id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonProperty("count")
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
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
		setCount(((KidsServed) obj).getCount());
		setPrograms(((KidsServed) obj).getPrograms());
	}

}
