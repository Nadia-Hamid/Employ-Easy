package se.yrgo.employeasy.vacation.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.yrgo.employeasy.vacation.entities.OpenDate;
import se.yrgo.employeasy.vacation.exceptions.JobTitleNotFoundException;
import se.yrgo.employeasy.vacation.repositories.VacationRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VacationServiceTest {

    @Mock
    private VacationRepository mockedVacationRepository;

    @InjectMocks
    private VacationService vacationServiceTest;

    @Test
    void getAllFromExistentJobTitle() {
        final List<OpenDate> openDates = new ArrayList<>();
        openDates.add(new OpenDate("developer"));
        when(mockedVacationRepository.findByJobTitle("developer")).thenReturn(openDates);
        assertEquals(1, vacationServiceTest.getAllFromJobTitle("DEVELOPER").size());
    }

    @Test
    void getAllFromNonExistentJobTitleThrowsNotFound() {
        final List<OpenDate> openDates = new ArrayList<>();
        when(mockedVacationRepository.findByJobTitle(any(String.class))).thenReturn(openDates);
        final String jobTitle = "nonexistent";
        var exception = assertThrows(JobTitleNotFoundException.class,
                () -> vacationServiceTest.getAllFromJobTitle(jobTitle));
        assertEquals("No open dates with job title " + jobTitle + " was found." , exception.getMessage());
    }
}