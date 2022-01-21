package se.yrgo.employeasy.vacation.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class TableScheduleDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private LocalDate startDate;
	private LocalDate endDate;
	private Integer multiple;

	public TableScheduleDTO() {
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public Integer getMultiple() {
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
