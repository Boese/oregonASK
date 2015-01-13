package org.oregonask.entities;

// Generated Jan 2, 2015 10:07:55 AM by Hibernate Tools 4.3.1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.Hibernate;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * PhoneAlternate generated by hbm2java
 */
@Entity
@Table(name = "PHONE_ALTERNATE", uniqueConstraints = @UniqueConstraint(columnNames = "PHONEALT"))
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class PhoneAlternate implements java.io.Serializable,IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String phoneAlt;
	private Contact contact;

	public PhoneAlternate() {
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

	@Column(name = "PHONEALT", unique = true, nullable = false, length = 45)
	@JsonProperty("PHONEALT")
	public String getPhoneAlt() {
		return this.phoneAlt;
	}

	public void setPhoneAlt(String phoneAlt) {
		this.phoneAlt = phoneAlt;
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
