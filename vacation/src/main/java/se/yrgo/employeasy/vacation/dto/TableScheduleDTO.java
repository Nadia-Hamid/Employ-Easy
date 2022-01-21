package se.yrgo.employeasy.vacation.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class TableScheduleDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	public LocalDate startDate;
	public LocalDate endDate;
	public int multiple;

	public TableScheduleDTO() {
	}

	public TableScheduleDTO(LocalDate startDate, LocalDate endDate, int multiple) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.multiple = multiple;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public void setMultiple(int multiple) {
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
