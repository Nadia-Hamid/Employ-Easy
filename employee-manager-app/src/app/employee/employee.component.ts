import { Component, OnInit } from '@angular/core'
import { HttpErrorResponse } from '@angular/common/http'
import { NgForm } from '@angular/forms'
import { EmployeeService } from '../services/employee.service'
import { Employee } from './employee'
import { Router, ActivatedRoute } from '@angular/router'

@Component({ templateUrl: 'employee.component.html' })
export class EmployeeComponent implements OnInit {
  public employees?: Employee[]
  public deleteEmployee: Employee
  public editEmployee: Employee

  public userStatusList: string[] = ['ACTIVE', 'INACTIVE', 'VACATION', 'MATERNITY_LEAVE', 'OFF_DUTY', 'ARCHIVE']
  public systemStatusList: string[] = ['SYSTEM_ADMIN', 'USER']

  constructor(private employeeService: EmployeeService, private route: ActivatedRoute, private router: Router) {}

  ngOnInit() {
    this.getEmployees()
  }

  getEmployees(): void {
    this.employeeService.getEmployees().subscribe(
      (response: Employee[]) => {
        this.employees = response
      },
      (error: HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }

  public onAddEmployee(addForm: NgForm): void {
    document.getElementById('add-employee-form')?.click()
    
    this.employeeService.addEmployee(addForm.value).subscribe(
      (response: Employee) => {
        console.log(response)
        this.getEmployees()
        addForm.reset()
      },
      (error: HttpErrorResponse) => {
        alert(error.message)
        addForm.reset()
      }
    )
  }

  public onDeleteEmployee(userId: String) {
    this.employeeService.deleteEmployee(userId).subscribe(
      (response: void) => {
        this.getEmployees()
      },
      (error: HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }

  public onUpdateEmployee(employee: Employee): void {
    document.getElementById('add-employee-form')?.click()
    this.employeeService.updateEmployee(employee).subscribe(
      (response: Employee) => {
        this.getEmployees()
      },
      (error: HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }

  public onOpenModal(employee: Employee, mode: string): void {
    const container = document.getElementById('main-container')
    const button = document.createElement('button')
    button.type = 'button'
    button.style.display = 'none'
    button.setAttribute('data-toggle', 'modal')
    if (mode === 'add') {
      button.setAttribute('data-target', '#addEmployeeModal')
    }
    if (mode === 'delete') {
      this.deleteEmployee = employee
      button.setAttribute('data-target', '#deleteEmployeeModal')
    }
    if (mode == 'edit') {
      this.editEmployee = employee
      button.setAttribute('data-target', '#updateEmployeeModal')
    }

    //TODO add parameter employee: Employee before mode above
    //TODO if mode for edit and delete
    container?.appendChild(button)
    button.click()
  }
}
