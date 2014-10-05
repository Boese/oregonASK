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
@Table(name = "active_date", catalog = "OREGONASK", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ACTIVE_DATE_ID") })

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class ActiveDate implements IEntity, Serializable {
	
	private static final long serialVersionUID = 8415796799977861128L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ACTIVE_DATE_ID")
	private long id;
	
	@Column(name = "ACTIVE_JANUARY", nullable = false)
	private boolean january;
	
	@Column(name = "ACTIVE_FEBRUARY", nullable = false)
	private boolean february;
	
	@Column(name = "ACTIVE_MARCH", nullable = false)
	private boolean march;
	
	@Column(name = "ACTIVE_APRIL", nullable = false)
	private boolean april;
	
	@Column(name = "ACTIVE_MAY", nullable = false)
	private boolean may;
	
	@Column(name = "ACTIVE_JUNE", nullable = false)
	private boolean june;
	
	@Column(name = "ACTIVE_JULY", nullable = false)
	private boolean july;
	
	@Column(name = "ACTIVE_AUGUST", nullable = false)
	private boolean august;
	
	@Column(name = "ACTIVE_SEPTEMBER", nullable = false)
	private boolean september;
	
	@Column(name = "ACTIVE_OCTOBER", nullable = false)
	private boolean october;
	
	@Column(name = "ACTIVE_NOVEMBER", nullable = false)
	private boolean november;
	
	@Column(name = "ACTIVE_DECEMBER", nullable = false)
	private boolean december;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "programActiveDate")
	private Set<Program> programs;
	
	
	/**
	 * Getters & Setters
	 */
	
	
	// Needed for Hibernate
	public ActiveDate() {
	}

	@JsonProperty("id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonProperty("january")
	public boolean isJanuary() {
		return january;
	}

	public void setJanuary(boolean january) {
		this.january = january;
	}

	@JsonProperty("february")
	public boolean isFebruary() {
		return february;
	}

	public void setFebruary(boolean february) {
		this.february = february;
	}

	@JsonProperty("march")
	public boolean isMarch() {
		return march;
	}

	public void setMarch(boolean march) {
		this.march = march;
	}

	@JsonProperty("april")
	public boolean isApril() {
		return april;
	}

	public void setApril(boolean april) {
		this.april = april;
	}

	@JsonProperty("may")
	public boolean isMay() {
		return may;
	}

	public void setMay(boolean may) {
		this.may = may;
	}

	@JsonProperty("june")
	public boolean isJune() {
		return june;
	}

	public void setJune(boolean june) {
		this.june = june;
	}

	@JsonProperty("july")
	public boolean isJuly() {
		return july;
	}

	public void setJuly(boolean july) {
		this.july = july;
	}

	@JsonProperty("august")
	public boolean isAugust() {
		return august;
	}

	public void setAugust(boolean august) {
		this.august = august;
	}

	@JsonProperty("september")
	public boolean isSeptember() {
		return september;
	}

	public void setSeptember(boolean september) {
		this.september = september;
	}

	@JsonProperty("october")
	public boolean isOctober() {
		return october;
	}

	public void setOctober(boolean october) {
		this.october = october;
	}

	@JsonProperty("november")
	public boolean isNovember() {
		return november;
	}

	public void setNovember(boolean november) {
		this.november = november;
	}

	@JsonProperty("december")
	public boolean isDecember() {
		return december;
	}

	public void setDecember(boolean december) {
		this.december = december;
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
		setJanuary(((ActiveDate) obj).isJanuary());
		setFebruary(((ActiveDate) obj).isFebruary());
		setMarch(((ActiveDate) obj).isMarch());
		setApril(((ActiveDate) obj).isApril());
		setMay(((ActiveDate) obj).isMay());
		setJune(((ActiveDate) obj).isJune());
		setJuly(((ActiveDate) obj).isJuly());
		setAugust(((ActiveDate) obj).isAugust());
		setSeptember(((ActiveDate) obj).isSeptember());
		setOctober(((ActiveDate) obj).isOctober());
		setNovember(((ActiveDate) obj).isNovember());
		setDecember(((ActiveDate) obj).isDecember());
		setPrograms(((ActiveDate) obj).getPrograms());
	}

}
