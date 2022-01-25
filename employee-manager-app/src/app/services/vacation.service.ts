import { Injectable } from "@angular/core"
import { Employee } from '../components/employee/employee'
import { HttpClient } from '@angular/common/http';
import { environment } from "src/environments/environment";
import { Observable } from "rxjs";
import { Vacation } from "../components/user-employee/vacation";


@Injectable({providedIn: 'root'})
export class VacationService {
    private apiServerUrl = environment.apiBaseUrlVacation;
   
    constructor(private http: HttpClient) {}

    public getVacationDates(jobTitle: String): Observable<Vacation[]> {
        const arr = this.http.get<Vacation[]>(`${this.apiServerUrl}/v1/vacations/${jobTitle}`);
        return arr;
    }

    public reserveVacationDate(jobTitle: String, vacation: Object): Observable<void> {
        return this.http.put<void>(`${this.apiServerUrl}/v1/vacations/${jobTitle}`, vacation);
    }

    public scheduleVacationPeriods(jobTitle: String, vacationTerm: Object): Observable<void> {
        return this.http.post<void>(`${this.apiServerUrl}/v1/vacations/${jobTitle}`, vacationTerm);
    }
}