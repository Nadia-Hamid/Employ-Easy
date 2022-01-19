package se.yrgo.employeasy.vacation.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import se.yrgo.employeasy.vacation.dto.OpenDateDTO;
import se.yrgo.employeasy.vacation.entities.VacationDate;
import se.yrgo.employeasy.vacation.exceptions.JobTitleNotFoundException;
import se.yrgo.employeasy.vacation.repositories.VacationRepository;

@ExtendWith(MockitoExtension.class)
class VacationServiceTest {

    @Mock
    private VacationRepository mockedVacationRepository;

    @InjectMocks
    private VacationService vacationServiceTest;

    @Test
    void getAllFromExistentJobTitle() {
    	
        final List<VacationDate> openDates = new ArrayList<>();
        
        openDates.add(new VacationDate("developer", LocalDate.of(2022,6,20)));
        openDates.add(new VacationDate("developer", LocalDate.of(2022,6,21)));
        openDates.add(new VacationDate("developer", LocalDate.of(2022,6,22)));
        
        when(mockedVacationRepository.findByJobTitle("developer")).thenReturn(openDates);
        assertEquals(3, vacationServiceTest.getAllFromJobTitle("DEVELOPER").size());
    }

    @Test
    void getAllFromNonExistentJobTitleThrowsNotFound() {
    	
        final List<VacationDate> openDates = new ArrayList<>();
        
        when(mockedVacationRepository.findByJobTitle(any(String.class))).thenReturn(openDates);
        
        final String jobTitle = "nonexistent";
        var exception = assertThrows(JobTitleNotFoundException.class,
                () -> vacationServiceTest.getAllFromJobTitle(jobTitle));
        assertEquals("No open dates with job title " + jobTitle + " was found." , exception.getMessage());
    }
    
    @Test
    void bookVacationTest() {
    	
    	VacationDate od = new VacationDate("nadham31", LocalDate.of(2022, 6, 1), "developer");
    	OpenDateDTO odd = new OpenDateDTO("nadham31", LocalDate.of(2022, 6, 1), "developer");
        when(mockedVacationRepository.addVacationDate("nadham31", LocalDate.of(2022, 6, 1), "developer")).thenReturn(od);
        
        OpenDateDTO odd2 = vacationServiceTest.addVacation(odd);
        
        assertEquals("nadham31", odd2.getUserId());
    }
}