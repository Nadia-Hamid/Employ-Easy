package se.yrgo.employeasy.vacation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yrgo.employeasy.vacation.dto.OpenDateDTO;
import se.yrgo.employeasy.vacation.entities.VacationDate;
import se.yrgo.employeasy.vacation.exceptions.JobTitleNotFoundException;
import se.yrgo.employeasy.vacation.repositories.UserDateRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VacationService {

    private final UserDateRepository userDateRepository;

    @Autowired
    public VacationService(UserDateRepository userDateRepository) {
        this.userDateRepository = userDateRepository;
    }

    public Set<OpenDateDTO> getAllFromJobTitle(String jobTitle) {
        return userDateRepository
                .findByJobTitle(jobTitle.toLowerCase())
                .stream()
                .map(OpenDateDTO::new)
                .collect(Collectors.collectingAndThen(Collectors.toSet(), Optional::of))
                .filter(l -> !l.isEmpty())
                .orElseThrow(() -> new JobTitleNotFoundException("No open dates with job title " + jobTitle + " was found."));
    }

    public void deleteDateFromUser(String userId, LocalDate date) {
        VacationDate entity = userDateRepository.findUserDate(userId, date);
        userDateRepository.delete(entity);
    }

    public Object requestReservation(LocalDate requestedDate, String userId) {
        //TODO
        return null;
    }
}
