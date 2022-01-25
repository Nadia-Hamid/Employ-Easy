package se.yrgo.employeasy.vacation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yrgo.employeasy.vacation.dto.*;
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

    
    
    public ReservedDateDTO requestReservationUsingJobTitle(ReservedDateDTO request, String jobTitle) {
        final LocalDate dateRequested = request.getDate();
        if(dateRequested.isBefore(LocalDate.now())) {
            throw new TimeException("Vacation date " + dateRequested + " needs to be in the future.");
        }
        var openDates = dateRepository.findDateSlots(jobTitle, dateRequested);

        final String userIdRequested = request.getUserId();
        if(openDates.isEmpty()) {
            throw new ObjectNotFoundException("No open dates with user " + userIdRequested + " was found.");
        } else {
            if(dateRepository.hasAlreadyBooked(dateRequested, userIdRequested)) {
                throw new DoubleBookedException("A single user can only book a date once");
            }
            int randomElementIndex = ThreadLocalRandom.current().nextInt(openDates.size());
            var update = openDates.get(randomElementIndex);
            update.setUserId(userIdRequested);
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

    @Transactional
    public void addSchedule(TableScheduleDTO schedule, String jobTitle) {
        var startDate = schedule.getStartDate();
        var endDate = schedule.getEndDate();
        if(startDate.isAfter(endDate)) {
            throw new TimeException("End date must be after start date");
        }

        List<LocalDate> dates = startDate
                .datesUntil(endDate.plusDays(1))
                .collect(Collectors.toList());

        List<VacationDate> vd = new ArrayList<>();
        final int multiple = schedule.getMultiple();
        for (LocalDate localDate : dates) {
            for (int i = 0; i < multiple; i++) {
                vd.add(new VacationDate(jobTitle, localDate));
            }
        }
        dateRepository.saveAll(vd);
    }

    public TableBookableDTO getBookableByYearAndJobTitle(String jobTitle, String year) {
        List<VacationDate> allMatching = dateRepository.findAllByYearAndJobTitle(Integer.parseInt(year), jobTitle);
        Map<LocalDate, Long> allMatchingAsMap = allMatching
                .stream()
                .collect(Collectors.groupingBy(VacationDate::getDate, Collectors.counting()));
        return new TableBookableDTO(allMatchingAsMap);
    }

    public TableBookableDTO getAllBookable() {
        List<VacationDate> allMatching = dateRepository.findAllAnnual();
        Map<LocalDate, Long> allMatchingAsMap = allMatching
                .stream()
                .collect(Collectors.groupingBy(VacationDate::getDate, Collectors.counting()));
        return new TableBookableDTO(allMatchingAsMap);
    }
}
