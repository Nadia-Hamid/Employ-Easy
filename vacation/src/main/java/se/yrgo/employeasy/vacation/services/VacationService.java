package se.yrgo.employeasy.vacation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yrgo.employeasy.vacation.dto.OpenDateDTO;
import se.yrgo.employeasy.vacation.dto.ReservedDateDTO;
import se.yrgo.employeasy.vacation.dto.UserAnnualDatesDTO;
import se.yrgo.employeasy.vacation.entities.VacationDate;
import se.yrgo.employeasy.vacation.exceptions.DoubleBookedException;
import se.yrgo.employeasy.vacation.exceptions.ObjectNotFoundException;
import se.yrgo.employeasy.vacation.exceptions.TimeException;
import se.yrgo.employeasy.vacation.repositories.DateRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class VacationService {

    private final DateRepository dateRepository;

    @Autowired
    public VacationService(DateRepository dateRepository) {
        this.dateRepository = dateRepository;
    }

    public Set<OpenDateDTO> getAllFromJobTitle(String jobTitle) {
        return dateRepository
                .findBookableByJobTitle(jobTitle)
                .stream()
                .map(OpenDateDTO::new)
                .collect(Collectors.collectingAndThen(Collectors.toCollection(TreeSet::new), Optional::of))
                .filter(l -> !l.isEmpty())
                .orElseThrow(() -> new ObjectNotFoundException(
                        "No open dates with job title " + jobTitle + " was found."));
    }

    public ReservedDateDTO requestReservationUsingJobTitle(LocalDate date, String userId, String jobTitle) {
        if(date.isBefore(LocalDate.now())) {
            throw new TimeException("Vacation date " + date + " needs to be in the future.");
        }
        var openDates = dateRepository.findDateSlots(jobTitle, date);
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

    @Transactional
    public void resetFutureVacationChoices(String userId) {
        dateRepository.resetFutureChoices(userId);
    }

    public UserAnnualDatesDTO getMyAvailableDates(String jobTitle, String userId) {
        final List<VacationDate> mutableUnbooked = dateRepository.findBookableByJobTitle(jobTitle);
        final List<VacationDate> booked = Collections.unmodifiableList(dateRepository.findAnnualByUserId(userId));
        mutableUnbooked.removeAll(booked);
        var futureBookable = mutableUnbooked.stream()
                .map(OpenDateDTO::new)
                .collect(Collectors.toCollection(TreeSet::new));
        final LocalDate tomorrow = LocalDate.now().plusDays(1);
        final int pastBooked = (int) booked.stream().filter(b -> b.getDate().isBefore(tomorrow)).count();
        final int futureBooked = booked.size() - pastBooked;
        return new UserAnnualDatesDTO(pastBooked, futureBooked, futureBookable);
    }
}
