import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { HttpClient } from '@angular/common/http';
import { Employee } from "./employee";
import { environment } from "src/environments/environment";

@Injectable({providedIn: 'root'})
export class EmployeeService {
    private apiServerUrl = environment.apiBaseUrl;
    constructor(private http: HttpClient) {}

    public getEmployees(): Observable<Employee[]> {
        const arr = this.http.get<Employee[]>(`${this.apiServerUrl}/v1/employees`);
        return arr;
    }

    public addEmployee(employee: Employee): Observable<Employee> {
        return this.http.post<Employee>(`${this.apiServerUrl}/v1/employees`, employee);
    }

    /*public updateEmployee(employee: Employee): Observable<Employee> {
        return this.http.put<Employee>(`${this.apiServerUrl}/employees/${employee.userId}`, employee);
    }*/

    public deleteEmployee(userId: String): Observable<void> {
    console.log(userId)
       return this.http.delete<void>(`${this.apiServerUrl}/employees/${userId}`);
    }
    
}