package se.yrgo.employeasy.vacation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yrgo.employeasy.vacation.dto.OpenDateDTO;
import se.yrgo.employeasy.vacation.repositories.VacationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VacationService {

    private final VacationRepository vacationRepository;

    @Autowired
    public VacationService(VacationRepository vacationRepository) {
        this.vacationRepository = vacationRepository;
    }

    public List<OpenDateDTO> getAllFromJobTitle(String jobTitle) {
        return vacationRepository.findByJobTitle(jobTitle).stream().map(OpenDateDTO::new).collect(Collectors.toList());
    }
}
