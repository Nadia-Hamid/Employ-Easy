package se.yrgo.employeasy.vacation.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.yrgo.employeasy.vacation.dto.OpenDateDTO;
import se.yrgo.employeasy.vacation.dto.ReservedDateDTO;
import se.yrgo.employeasy.vacation.dto.TableScheduleDTO;
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
    
//	public OpenDateDTO addVacation(OpenDateDTO booking) {
//		
//		VacationDate vd = new VacationDate(booking.getUserId(), booking.getDate(), booking.getJobTitle());
//        List<VacationDate> openDates = dateRepository.findByJobTitleOpenDate(vd.getJobTitle(), vd.getDate());
//        
//        if(openDates.isEmpty()) { throw new ObjectNotFoundException("No open dates"); }
//        
//        long id = openDates.stream().filter(p -> p.getUserId().equals("")).findFirst().orElse(null).getId();
//        vd = dateRepository.getById(id);
//        vd.setUserId(booking.getUserId());
//		dateRepository.save(vd);
//		return new OpenDateDTO(vd);
//	}

    /**
     * This method add new available dates per jobtitle to DB.
     * 
     * @author Nadia Hamid
     * @param jobTitle
     * @param schedule
     * @return TableScheduleDTO
     */
	public TableScheduleDTO addSchedule(String jobTitle, TableScheduleDTO schedule) {

		List<LocalDate> dates = dateList(schedule.getStartDate(), schedule.getEndDate());
		List<VacationDate> vd = new ArrayList<>();

		for (LocalDate localDate : dates) {
			vd.add((VacationDate) Collections.nCopies(schedule.getMultiple(), new VacationDate(jobTitle, localDate)));
		}

		vd.stream().forEach(e -> dateRepository.save(e));
		return null;
	}

	/**
	 * Utility method to retrieve a List with all LocalDate to be inserted in DB.
	 * 
	 * @author Nadia Hamid
	 * @param dateFrom
	 * @param dateTo
	 * @return List of LocalDate
	 */
	private List<LocalDate> dateList(LocalDate dateFrom, LocalDate dateTo) {

		List<LocalDate> dates = dateFrom.datesUntil(dateTo).collect(Collectors.toList());
		return dates;
	}
}
