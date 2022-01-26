import { Injectable } from "@angular/core"
import { HttpClient } from '@angular/common/http';
import { environment } from "src/environments/environment";
import { Observable } from "rxjs";
import { Vacation } from "../components/user-employee/vacation";
import { VacationBookedPeriod } from "../components/employee/schedule-vacation/vacationBookedPeriod";
import { MyAnnualAllowance } from "../components/user-employee/my-annual-allowance";


@Injectable({providedIn: 'root'})
export class VacationService {
    private apiServerUrl = environment.apiBaseUrlVacation;
   
    constructor(private http: HttpClient) {}

    public getVacationDates(jobTitle: String): Observable<Vacation[]> {
        const arr = this.http.get<Vacation[]>(`${this.apiServerUrl}/v1/vacations/${jobTitle}`);
        return arr;
    }

    public getVacationDatesForUser(jobTitle: String, userId: String): Observable<MyAnnualAllowance> {
        return this.http.get<MyAnnualAllowance>(`${this.apiServerUrl}/v1/vacations/${jobTitle}/${userId}`);
    }

    public reserveVacationDate(jobTitle: String, vacation: Object): Observable<void> {
        return this.http.put<void>(`${this.apiServerUrl}/v1/vacations/${jobTitle}`, vacation);
    }

    public scheduleVacationPeriods(jobTitle: String, vacationTerm: Object): Observable<VacationBookedPeriod> {
        return this.http.post<VacationBookedPeriod>(`${this.apiServerUrl}/v1/vacations/${jobTitle}`, vacationTerm);
    }

    public getBookableDates(jobTitle: String, year: String): Observable<any> {
        return this.http.get<any>(`${this.apiServerUrl}/v1/vacations/${jobTitle}/year/${year}`);
    }
}