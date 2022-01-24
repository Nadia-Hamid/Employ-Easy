package se.yrgo.employeasy.vacation.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.yrgo.employeasy.vacation.dto.OpenDateDTO;
import se.yrgo.employeasy.vacation.dto.ReservedDateDTO;
import se.yrgo.employeasy.vacation.dto.TableScheduleDTO;
import se.yrgo.employeasy.vacation.dto.UserAnnualDatesDTO;
import se.yrgo.employeasy.vacation.entities.VacationDate;
import se.yrgo.employeasy.vacation.exceptions.DoubleBookedException;
import se.yrgo.employeasy.vacation.exceptions.ObjectNotFoundException;
import se.yrgo.employeasy.vacation.exceptions.TimeException;
import se.yrgo.employeasy.vacation.repositories.DateRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VacationServiceTest {

    @Mock
    private DateRepository mockedDateRepository;

    @InjectMocks
    private VacationService vacationServiceTest;

    private static final String JOB_TITLE = "developer";
    private static final String USER_ID = "marmar1234";
    private static final int CURRENT = LocalDate.now().getYear();
    private static final LocalDate MID_SUMMER = LocalDate.of(CURRENT, 6, 20);

    @Test
    void getAllFromExistentJobTitle() {
        final List<VacationDate> vacationDates = List.of(
                new VacationDate(JOB_TITLE, MID_SUMMER),
                new VacationDate(JOB_TITLE, MID_SUMMER.plusDays(1)),
                new VacationDate(JOB_TITLE, MID_SUMMER.plusDays(2))
        );

        when(mockedDateRepository.findBookableByJobTitle(JOB_TITLE)).thenReturn(vacationDates);
        assertEquals(vacationDates.size(), vacationServiceTest.getAllFromJobTitle(JOB_TITLE).size());
    }

    @Test
    void getAllFromNonExistentJobTitleThrowsNotFound() {
        final String nonExistent = "nonexistent";

        when(mockedDateRepository.findBookableByJobTitle(any(String.class))).thenReturn(new ArrayList<>());
        var exception = assertThrows(ObjectNotFoundException.class,
                () -> vacationServiceTest.getAllFromJobTitle(nonExistent));
        assertEquals("No open dates with job title " + nonExistent + " was found." , exception.getMessage());
    }

    @Test
    void requestVacationDateAsUser() {
        VacationDate vd = new VacationDate(JOB_TITLE, MID_SUMMER);
        vd.setUserId(USER_ID);

        when(mockedDateRepository.findDateSlots(JOB_TITLE, MID_SUMMER)).thenReturn(List.of(vd));
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
        final LocalDate futureWorkDate = LocalDate.of(CURRENT,3,28);
        VacationDate vd = new VacationDate(JOB_TITLE, futureWorkDate);
        vd.setUserId(USER_ID);

        when(mockedDateRepository.findDateSlots(JOB_TITLE, futureWorkDate)).thenReturn(new ArrayList<>());
        assertThrows(ObjectNotFoundException.class,
                () -> vacationServiceTest.requestReservationUsingJobTitle(futureWorkDate, USER_ID, JOB_TITLE));
    }

    @Test
    void requestAlreadyBookedVacationDate() {
        VacationDate vd = new VacationDate(JOB_TITLE, MID_SUMMER);
        vd.setUserId(USER_ID);

        when(mockedDateRepository.findDateSlots(JOB_TITLE, MID_SUMMER)).thenReturn(List.of(vd));
        when(mockedDateRepository.hasAlreadyBooked(MID_SUMMER, USER_ID)).thenReturn(true);
        assertThrows(DoubleBookedException.class,
                () -> vacationServiceTest.requestReservationUsingJobTitle(MID_SUMMER, USER_ID, JOB_TITLE));
    }

    @Test
    void getUserAnnualBookingData() {
        final List<VacationDate> mutableUnbooked = new ArrayList<>();
        mutableUnbooked.add(new VacationDate(JOB_TITLE, MID_SUMMER));
        mutableUnbooked.add(new VacationDate(JOB_TITLE, MID_SUMMER.plusDays(1)));
        mutableUnbooked.add(new VacationDate(JOB_TITLE, MID_SUMMER.plusDays(2)));
        final List<VacationDate> booked = List.of(
                new VacationDate(JOB_TITLE, LocalDate.of(2022,1,3)),
                new VacationDate(JOB_TITLE, MID_SUMMER)
        );
        when(mockedDateRepository.findBookableByJobTitle(JOB_TITLE)).thenReturn(mutableUnbooked);
        when(mockedDateRepository.findAnnualByUserId(USER_ID)).thenReturn(booked);
        UserAnnualDatesDTO annualData = vacationServiceTest.getMyAvailableDates(JOB_TITLE, USER_ID);
        assertEquals(1, annualData.getPastBooked());
        assertEquals(1, annualData.getFutureBooked());
        mutableUnbooked.removeAll(booked);
        var dtos = mutableUnbooked.stream().map(OpenDateDTO::new).collect(Collectors.toSet());
        assertEquals(dtos, annualData.getFutureUnbooked());
    }

    @Test
    void resetWasCalled() {
        vacationServiceTest.resetFutureVacationChoices(USER_ID);
        verify(mockedDateRepository, times(1)).resetFutureChoices(any(String.class));
    }

    @Test
    void addSummerVacationsAsAdmin() {
        final int multiple = 1, wantedNumberOfInvocations = 1, two = 2;
        final var schedule = new TableScheduleDTO(MID_SUMMER, MID_SUMMER.plusDays(two), multiple);
        var result = vacationServiceTest.addSchedule(schedule, JOB_TITLE);
        verify(mockedDateRepository, times(wantedNumberOfInvocations)).saveAll(any(Iterable.class));
        assertEquals(schedule, result);
    }
}
