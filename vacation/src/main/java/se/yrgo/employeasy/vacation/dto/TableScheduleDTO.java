package se.yrgo.employeasy.vacation.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO class to receive a List with dates and job title to be added to the DB, with vacant holiday dates
 * @author nadiahamid
 */
public class TableScheduleDTO implements Serializable {
	private static final long serialVersionUID = 7L;

	private LocalDate startDate;
	private LocalDate endDate;
	private int multiple;

	public TableScheduleDTO(LocalDate startDate, LocalDate endDate, int multiple) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.multiple = multiple;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public int getMultiple() {
		return multiple;
	}

	@Override
	public int hashCode() {
		return Objects.hash(endDate, multiple, startDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TableScheduleDTO other = (TableScheduleDTO) obj;
		return Objects.equals(endDate, other.endDate) && Objects.equals(multiple, other.multiple)
				&& Objects.equals(startDate, other.startDate);
	}
}
