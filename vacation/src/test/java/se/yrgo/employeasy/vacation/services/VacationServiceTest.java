package se.yrgo.employeasy.vacation.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.yrgo.employeasy.vacation.entities.UserDate;
import se.yrgo.employeasy.vacation.exceptions.JobTitleNotFoundException;
import se.yrgo.employeasy.vacation.repositories.UserDateRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VacationServiceTest {

    @Mock
    private UserDateRepository mockedDateRepository;

    @InjectMocks
    private VacationService vacationServiceTest;

    @Test
    void getAllFromExistentJobTitle() {
    	
        final List<UserDate> userDates = new ArrayList<>();
        
        userDates.add(new UserDate("marmar1234", "developer", LocalDate.of(2022,6,20)));
        userDates.add(new UserDate("marmar1234", "developer", LocalDate.of(2022,6,21)));
        userDates.add(new UserDate("marmar1234", "developer", LocalDate.of(2022,6,22)));
        
        when(mockedDateRepository.findByJobTitle("developer")).thenReturn(userDates);
        assertEquals(3, vacationServiceTest.getAllFromJobTitle("DEVELOPER").size());
    }

    @Test
    void getAllFromNonExistentJobTitleThrowsNotFound() {
    	
        final List<UserDate> openDates = new ArrayList<>();
        
        when(mockedDateRepository.findByJobTitle(any(String.class))).thenReturn(openDates);
        
        final String jobTitle = "nonexistent";
        var exception = assertThrows(JobTitleNotFoundException.class,
                () -> vacationServiceTest.getAllFromJobTitle(jobTitle));
        assertEquals("No open dates with job title " + jobTitle + " was found." , exception.getMessage());
    }

    @Test
    void deleteDateFromJobTitle() {
        final String userId = "marmar1234";
        final LocalDate date = LocalDate.of(2022,6,20);
        when(mockedDateRepository.findUserDate(userId, date)).thenReturn(new UserDate(userId, "developer", date));
        vacationServiceTest.deleteDateFromUser(userId, date);
        verify(mockedDateRepository, times(1)).delete(any(UserDate.class));
    }
}