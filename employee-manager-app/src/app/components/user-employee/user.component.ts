import { Component, OnInit } from '@angular/core'
import { Router } from '@angular/router'
import { EmployeeService } from '../../services/employee.service'
import { Employee } from '../employee/employee'
import { HttpErrorResponse } from '@angular/common/http'
import { CustomErrorMessage } from 'src/app/alerts/custom-error-message'

@Component({ templateUrl: 'user.component.html', styleUrls: ['./user.component.css'] })
export class UserComponent implements OnInit {
  public employee?: Employee
  public employeeLoaded: boolean = false

  constructor(private router: Router, private employeeService: EmployeeService) {}

  go() {
    this.router.navigate(['v1/employees/user'])
  }

  ngOnInit(): void {
    this.getOneEmployee()
  }

  getOneEmployee(): void {
    this.employeeService.getOneEmployee(localStorage.getItem('userName')).subscribe(
      (response: Employee) => {
        this.employee = response
        this.employeeLoaded = true
      },
      (httpError: HttpErrorResponse) => {
        CustomErrorMessage(httpError)}
    )
  }
}
