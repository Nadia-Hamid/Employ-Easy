package se.yrgo.employeasy.vacation.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.yrgo.employeasy.vacation.dto.OpenDateDTO;
import se.yrgo.employeasy.vacation.dto.ReservedDateDTO;
import se.yrgo.employeasy.vacation.entities.VacationDate;
import se.yrgo.employeasy.vacation.exceptions.DoubleBookedException;
import se.yrgo.employeasy.vacation.exceptions.ObjectNotFoundException;
import se.yrgo.employeasy.vacation.exceptions.TimeException;
import se.yrgo.employeasy.vacation.repositories.DateRepository;


@Service
public class VacationService {

    private final DateRepository dateRepository;

    @Autowired
    public VacationService(DateRepository dateRepository) {
        this.dateRepository = dateRepository;
    }

    public Set<OpenDateDTO> getAllFromJobTitle(String jobTitle) {
        return dateRepository
                .findByJobTitle(jobTitle.toLowerCase())
                .stream()
                .map(OpenDateDTO::new)
                .collect(Collectors.collectingAndThen(Collectors.toSet(), Optional::of))
                .filter(l -> !l.isEmpty())
                .orElseThrow(() -> new ObjectNotFoundException("No open dates with job title " + jobTitle + " was found."));
    }

    
    
    public ReservedDateDTO requestReservationUsingJobTitle(LocalDate date, String userId, String jobTitle) {
        if(date.isBefore(LocalDate.now())) {
            throw new TimeException("Vacation date " + date + " needs to be in the future.");
        }
        var openDates = dateRepository.findByJobTitleOpenDate(jobTitle, date);
        if(openDates.isEmpty()) {
            throw new ObjectNotFoundException("No open dates with user " + userId + " was found.");
        } else {
            if(dateRepository.hasAlreadyBooked(date, userId)) {
                throw new DoubleBookedException("A single user can only book a date once");
            }
            int randomElementIndex = ThreadLocalRandom.current().nextInt(openDates.size());
            var update = openDates.get(randomElementIndex);
            update.setUserId(userId);
            var result = dateRepository.save(update);
            return new ReservedDateDTO(result.getDate(), result.getUserId());
        }
    }

    /**
     * Insert new available dates into DB
     * @param jobTitle
     * @param start
     * @param end
     * @param i
     * @return List of VacatioDate
     */
    public List<VacationDate> addSchedule(String jobTitle, LocalDate start, LocalDate end, int i) {

		List<LocalDate> dates = start.datesUntil(end.plusDays(1)).collect(Collectors.toList());
		List<VacationDate> vd = new ArrayList<>();
		
		for (LocalDate localDate : dates) {
			for (int j = 0; j < i; j++) {
				vd.add(new VacationDate(jobTitle, localDate));
			}
		}
		
		vd.stream().forEach(x -> dateRepository.save(x));
		return vd;
	}

    @Transactional
    public void resetFutureVacationChoices(String userId) {
        dateRepository.resetFutureChoices(userId);
    }
}
