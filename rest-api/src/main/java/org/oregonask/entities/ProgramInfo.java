package org.oregonask.entities;
// Generated Nov 11, 2014 2:42:57 PM by Hibernate Tools 4.3.1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * ProgramInfo generated by hbm2java
 */
@Entity
@Table(name = "PROGRAM_INFO", catalog = "OREGONASKDB", uniqueConstraints = @UniqueConstraint(columnNames = {
		"YEAR", "PROGRAM_ID" }))
public class ProgramInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer year;
	private Integer programId;
	private String averageDailyAttendance;
	private String maxCapacity;
	private Boolean offeredBeforeSchool;
	private Boolean offeredAfterSchool;
	private Boolean offeredMonday;
	private Boolean offeredTuesday;
	private Boolean offeredWednesday;
	private Boolean offeredThursday;
	private Boolean offeredFriday;
	private Boolean offeredSaturday;
	private Boolean offeredSunday;
	private Boolean offeredWeekends;
	private Boolean offeredEvenings;
	private Boolean offeredBreaks;
	private String averageHoursPerWeek;
	private Boolean foodProvidedBeforeSchool;
	private Boolean foodProvidedAfterSchool;
	private Boolean foodProvidedDuringBreaks;
	private Boolean stemOffered;

	public ProgramInfo() {
	}

	public ProgramInfo(Integer year, Integer programId,
			String averageDailyAttendance, String maxCapacity,
			Boolean offeredBeforeSchool, Boolean offeredAfterSchool,
			Boolean offeredMonday, Boolean offeredTuesday,
			Boolean offeredWednesday, Boolean offeredThursday,
			Boolean offeredFriday, Boolean offeredSaturday,
			Boolean offeredSunday, Boolean offeredWeekends,
			Boolean offeredEvenings, Boolean offeredBreaks,
			String averageHoursPerWeek, Boolean foodProvidedBeforeSchool,
			Boolean foodProvidedAfterSchool, Boolean foodProvidedDuringBreaks,
			Boolean stemOffered) {
		this.year = year;
		this.programId = programId;
		this.averageDailyAttendance = averageDailyAttendance;
		this.maxCapacity = maxCapacity;
		this.offeredBeforeSchool = offeredBeforeSchool;
		this.offeredAfterSchool = offeredAfterSchool;
		this.offeredMonday = offeredMonday;
		this.offeredTuesday = offeredTuesday;
		this.offeredWednesday = offeredWednesday;
		this.offeredThursday = offeredThursday;
		this.offeredFriday = offeredFriday;
		this.offeredSaturday = offeredSaturday;
		this.offeredSunday = offeredSunday;
		this.offeredWeekends = offeredWeekends;
		this.offeredEvenings = offeredEvenings;
		this.offeredBreaks = offeredBreaks;
		this.averageHoursPerWeek = averageHoursPerWeek;
		this.foodProvidedBeforeSchool = foodProvidedBeforeSchool;
		this.foodProvidedAfterSchool = foodProvidedAfterSchool;
		this.foodProvidedDuringBreaks = foodProvidedDuringBreaks;
		this.stemOffered = stemOffered;
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

	@Column(name = "YEAR")
	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Column(name = "PROGRAM_ID")
	public Integer getProgramId() {
		return this.programId;
	}

	public void setProgramId(Integer programId) {
		this.programId = programId;
	}

	@Column(name = "AVERAGE_DAILY_ATTENDANCE", length = 45)
	public String getAverageDailyAttendance() {
		return this.averageDailyAttendance;
	}

	public void setAverageDailyAttendance(String averageDailyAttendance) {
		this.averageDailyAttendance = averageDailyAttendance;
	}

	@Column(name = "MAX_CAPACITY", length = 45)
	public String getMaxCapacity() {
		return this.maxCapacity;
	}

	public void setMaxCapacity(String maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	@Column(name = "OFFERED_BEFORE_SCHOOL")
	public Boolean getOfferedBeforeSchool() {
		return this.offeredBeforeSchool;
	}

	public void setOfferedBeforeSchool(Boolean offeredBeforeSchool) {
		this.offeredBeforeSchool = offeredBeforeSchool;
	}

	@Column(name = "OFFERED_AFTER_SCHOOL")
	public Boolean getOfferedAfterSchool() {
		return this.offeredAfterSchool;
	}

	public void setOfferedAfterSchool(Boolean offeredAfterSchool) {
		this.offeredAfterSchool = offeredAfterSchool;
	}

	@Column(name = "OFFERED_MONDAY")
	public Boolean getOfferedMonday() {
		return this.offeredMonday;
	}

	public void setOfferedMonday(Boolean offeredMonday) {
		this.offeredMonday = offeredMonday;
	}

	@Column(name = "OFFERED_TUESDAY")
	public Boolean getOfferedTuesday() {
		return this.offeredTuesday;
	}

	public void setOfferedTuesday(Boolean offeredTuesday) {
		this.offeredTuesday = offeredTuesday;
	}

	@Column(name = "OFFERED_WEDNESDAY")
	public Boolean getOfferedWednesday() {
		return this.offeredWednesday;
	}

	public void setOfferedWednesday(Boolean offeredWednesday) {
		this.offeredWednesday = offeredWednesday;
	}

	@Column(name = "OFFERED_THURSDAY")
	public Boolean getOfferedThursday() {
		return this.offeredThursday;
	}

	public void setOfferedThursday(Boolean offeredThursday) {
		this.offeredThursday = offeredThursday;
	}

	@Column(name = "OFFERED_FRIDAY")
	public Boolean getOfferedFriday() {
		return this.offeredFriday;
	}

	public void setOfferedFriday(Boolean offeredFriday) {
		this.offeredFriday = offeredFriday;
	}

	@Column(name = "OFFERED_SATURDAY")
	public Boolean getOfferedSaturday() {
		return this.offeredSaturday;
	}

	public void setOfferedSaturday(Boolean offeredSaturday) {
		this.offeredSaturday = offeredSaturday;
	}

	@Column(name = "OFFERED_SUNDAY")
	public Boolean getOfferedSunday() {
		return this.offeredSunday;
	}

	public void setOfferedSunday(Boolean offeredSunday) {
		this.offeredSunday = offeredSunday;
	}

	@Column(name = "OFFERED_WEEKENDS")
	public Boolean getOfferedWeekends() {
		return this.offeredWeekends;
	}

	public void setOfferedWeekends(Boolean offeredWeekends) {
		this.offeredWeekends = offeredWeekends;
	}

	@Column(name = "OFFERED_EVENINGS")
	public Boolean getOfferedEvenings() {
		return this.offeredEvenings;
	}

	public void setOfferedEvenings(Boolean offeredEvenings) {
		this.offeredEvenings = offeredEvenings;
	}

	@Column(name = "OFFERED_BREAKS")
	public Boolean getOfferedBreaks() {
		return this.offeredBreaks;
	}

	public void setOfferedBreaks(Boolean offeredBreaks) {
		this.offeredBreaks = offeredBreaks;
	}

	@Column(name = "AVERAGE_HOURS_PER_WEEK", length = 45)
	public String getAverageHoursPerWeek() {
		return this.averageHoursPerWeek;
	}

	public void setAverageHoursPerWeek(String averageHoursPerWeek) {
		this.averageHoursPerWeek = averageHoursPerWeek;
	}

	@Column(name = "FOOD_PROVIDED_BEFORE_SCHOOL")
	public Boolean getFoodProvidedBeforeSchool() {
		return this.foodProvidedBeforeSchool;
	}

	public void setFoodProvidedBeforeSchool(Boolean foodProvidedBeforeSchool) {
		this.foodProvidedBeforeSchool = foodProvidedBeforeSchool;
	}

	@Column(name = "FOOD_PROVIDED_AFTER_SCHOOL")
	public Boolean getFoodProvidedAfterSchool() {
		return this.foodProvidedAfterSchool;
	}

	public void setFoodProvidedAfterSchool(Boolean foodProvidedAfterSchool) {
		this.foodProvidedAfterSchool = foodProvidedAfterSchool;
	}

	@Column(name = "FOOD_PROVIDED_DURING_BREAKS")
	public Boolean getFoodProvidedDuringBreaks() {
		return this.foodProvidedDuringBreaks;
	}

	public void setFoodProvidedDuringBreaks(Boolean foodProvidedDuringBreaks) {
		this.foodProvidedDuringBreaks = foodProvidedDuringBreaks;
	}

	@Column(name = "STEM_OFFERED")
	public Boolean getStemOffered() {
		return this.stemOffered;
	}

	public void setStemOffered(Boolean stemOffered) {
		this.stemOffered = stemOffered;
	}

}
