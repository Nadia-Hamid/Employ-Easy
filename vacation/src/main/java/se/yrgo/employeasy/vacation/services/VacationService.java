package se.yrgo.employeasy.vacation.services;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.yrgo.employeasy.vacation.dto.OpenDateDTO;
import se.yrgo.employeasy.vacation.entities.VacationDate;
import se.yrgo.employeasy.vacation.exceptions.JobTitleNotFoundException;
import se.yrgo.employeasy.vacation.repositories.VacationRepository;

@Service
public class VacationService {

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
    private final VacationRepository vacationRepository;

    @Autowired
    public VacationService(VacationRepository vacationRepository) {
        this.vacationRepository = vacationRepository;
    }

    public List<OpenDateDTO> getAllFromJobTitle(String jobTitle) {
        return vacationRepository
                .findByJobTitle(jobTitle.toLowerCase())
                .stream()
                .map(OpenDateDTO::new)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Optional::of))
                .filter(l -> !l.isEmpty())
                .orElseThrow(() -> new JobTitleNotFoundException("No open dates with job title " + jobTitle + " was found."));
    }
    
    public OpenDateDTO addVacation(OpenDateDTO booking) {
		
    	VacationDate newBooking = new VacationDate(booking);
		newBooking = vacationRepository.addVacationDate(newBooking.getUserId(),
				newBooking.getDate(), newBooking.getJobTitle());
		return new OpenDateDTO(newBooking);
	}
}
