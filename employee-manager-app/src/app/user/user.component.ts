import { Component, OnInit } from '@angular/core'
import { Router, ActivatedRoute } from '@angular/router'
import { CommonModule } from '@angular/common'
import { EmployeeService } from '../services/employee.service'
import { Employee } from '../employee/employee'
import { HttpErrorResponse } from '@angular/common/http'

@Component({ templateUrl: 'user.component.html', styleUrls: ['./user.component.css'] })
export class UserComponent implements OnInit {
  public employee?: Employee

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
      },
      (error: HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }
}
