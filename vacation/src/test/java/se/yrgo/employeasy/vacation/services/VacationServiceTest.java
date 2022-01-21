package se.yrgo.employeasy.vacation.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;

import se.yrgo.employeasy.vacation.dto.ReservedDateDTO;
import se.yrgo.employeasy.vacation.dto.TableScheduleDTO;
import se.yrgo.employeasy.vacation.entities.VacationDate;
import se.yrgo.employeasy.vacation.exceptions.DoubleBookedException;
import se.yrgo.employeasy.vacation.exceptions.ObjectNotFoundException;
import se.yrgo.employeasy.vacation.exceptions.TimeException;
import se.yrgo.employeasy.vacation.repositories.DateRepository;

@ExtendWith(MockitoExtension.class)
class VacationServiceTest {

    @Mock
    private DateRepository mockedDateRepository;

    @InjectMocks
    private VacationService vacationServiceTest;

    private static final String JOB_TITLE = "developer";
    private static final String USER_ID = "marmar1234";
    private static final int FUTURE = LocalDate.now().getYear() + 1;
    private static final LocalDate MID_SUMMER = LocalDate.of(FUTURE, 6, 20);

    @Test
    void getAllFromExistentJobTitle() {
        final List<VacationDate> vacationDates = List.of(
                new VacationDate(JOB_TITLE, MID_SUMMER),
                new VacationDate(JOB_TITLE, MID_SUMMER.plusDays(1)),
                new VacationDate(JOB_TITLE, MID_SUMMER.plusDays(2))
        );

        when(mockedDateRepository.findByJobTitle(JOB_TITLE)).thenReturn(vacationDates);
        assertEquals(vacationDates.size(), vacationServiceTest.getAllFromJobTitle(JOB_TITLE).size());
    }

    @Test
    void getAllFromNonExistentJobTitleThrowsNotFound() {
        final String nonExistent = "nonexistent";

        when(mockedDateRepository.findByJobTitle(any(String.class))).thenReturn(new ArrayList<>());
        var exception = assertThrows(ObjectNotFoundException.class,
                () -> vacationServiceTest.getAllFromJobTitle(nonExistent));
        assertEquals("No open dates with job title " + nonExistent + " was found." , exception.getMessage());
    }

    @Test
    void requestVacationDateAsUser() {
        VacationDate vd = new VacationDate(JOB_TITLE, MID_SUMMER);
        vd.setUserId(USER_ID);

        when(mockedDateRepository.findByJobTitleOpenDate(JOB_TITLE, MID_SUMMER)).thenReturn(List.of(vd));
        when(mockedDateRepository.save(any(VacationDate.class))).thenReturn(vd);
        ReservedDateDTO result = vacationServiceTest.requestReservationUsingJobTitle(MID_SUMMER, USER_ID, JOB_TITLE);
        assertEquals(MID_SUMMER, result.getDate());
        assertEquals(USER_ID, result.getUserId());
    }

    @Test
    void requestOldVacationDateAsUser() {
        final LocalDate oldDate = LocalDate.of(2021,12,24);
        VacationDate vd = new VacationDate(JOB_TITLE, oldDate);
        vd.setUserId(USER_ID);

        assertThrows(TimeException.class,
                () -> vacationServiceTest.requestReservationUsingJobTitle(oldDate, USER_ID, JOB_TITLE));
    }

    @Test
    void requestNonExistingVacationDate() {
        final LocalDate futureWorkDate = LocalDate.of(FUTURE,3,28);
        VacationDate vd = new VacationDate(JOB_TITLE, futureWorkDate);
        vd.setUserId(USER_ID);

        when(mockedDateRepository.findByJobTitleOpenDate(JOB_TITLE, futureWorkDate)).thenReturn(new ArrayList<>());
        assertThrows(ObjectNotFoundException.class,
                () -> vacationServiceTest.requestReservationUsingJobTitle(futureWorkDate, USER_ID, JOB_TITLE));
    }

    @Test
    void requestAlreadyBookedVacationDate() {
        VacationDate vd = new VacationDate(JOB_TITLE, MID_SUMMER);
        vd.setUserId(USER_ID);

        when(mockedDateRepository.findByJobTitleOpenDate(JOB_TITLE, MID_SUMMER)).thenReturn(List.of(vd));
        when(mockedDateRepository.hasAlreadyBooked(MID_SUMMER, USER_ID)).thenReturn(true);
        assertThrows(DoubleBookedException.class,
                () -> vacationServiceTest.requestReservationUsingJobTitle(MID_SUMMER, USER_ID, JOB_TITLE));
    }

    @Test
    void resetFutureVacationChoicesAsUser() {
        final int wantedNumberOfInvocations = 1;
        mockedDateRepository.resetFutureChoices(USER_ID);
        verify(mockedDateRepository, times(wantedNumberOfInvocations)).resetFutureChoices(USER_ID);
    }
    
	@Test
	void InsertDatesIntoDB() {

		LocalDate ld = LocalDate.of(2022, 07, 01);
		LocalDate ld2 = LocalDate.of(2022, 07, 10);
		
		List<LocalDate> dates = dateList(ld, ld2);
		List<VacationDate> vd = new ArrayList<>();

		VacationDate v = new VacationDate("developer", ld2);

		for (LocalDate localDate : dates) {
			vd.addAll(Collections.nCopies(3, new VacationDate("developer", localDate)));
		}

		mockedDateRepository.save(v);
		
//		vd.stream().forEach(e -> mockedDateRepository.save(e));
		verify(mockedDateRepository, times(1)).save(v);

	}

	private List<LocalDate> dateList(LocalDate dateFrom, LocalDate dateTo) {

		List<LocalDate> dates = dateFrom.datesUntil(dateTo).collect(Collectors.toList());
		return dates;
	}
}
