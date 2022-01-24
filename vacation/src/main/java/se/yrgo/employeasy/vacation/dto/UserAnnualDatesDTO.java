package se.yrgo.employeasy.vacation.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

public class UserAnnualDatesDTO implements Serializable {

    private final int pastBooked;
    private final int futureBooked;
    private final Set<OpenDateDTO> futureUnbooked;

    public UserAnnualDatesDTO(int pastBooked, int futureBooked, Set<OpenDateDTO> futureBookable) {
        this.pastBooked = pastBooked;
        this.futureBooked = futureBooked;
        this.futureUnbooked = futureBookable;
    }

    public int getPastBooked() {
        return pastBooked;
    }

    public int getFutureBooked() {
        return futureBooked;
    }

    public Set<OpenDateDTO> getFutureUnbooked() {
        return Collections.unmodifiableSet(futureUnbooked);
    }
}
