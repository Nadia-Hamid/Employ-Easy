import { Injectable } from '@angular/core'
import { Observable } from 'rxjs'
import { HttpClient } from '@angular/common/http'
import { Employee } from '../components/employee/employee'
import { environment } from 'src/environments/environment'

@Injectable({ providedIn: 'root' })
export class EmployeeService {
  private apiServerUrl = environment.apiBaseUrlEmployee

  constructor(private http: HttpClient) {}

  public getEmployees(): Observable<Employee[]> {
    const arr = this.http.get<Employee[]>(`${this.apiServerUrl}/v1/employees`, { withCredentials: true })
    return arr
  }

  public addEmployee(employee: Employee): Observable<Employee> {
    return this.http.post<Employee>(`${this.apiServerUrl}/v1/employees`, employee, { withCredentials: true })
  }

  public updateEmployee(employee: Employee): Observable<Employee> {
    return this.http.put<Employee>(`${this.apiServerUrl}/v1/employees`, employee, { withCredentials: true })
  }

  public deleteEmployee(userId: String): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/v1/employees/${userId}`, { withCredentials: true })
  }

  public getOneEmployee(userId: String): Observable<Employee> {
    return this.http.get<Employee>(`${this.apiServerUrl}/v1/employees/${userId}`, { withCredentials: true })
  }
}
