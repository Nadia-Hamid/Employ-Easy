import { Injectable } from '@angular/core'
import { HttpErrorResponse } from '@angular/common/http'
import { Employee } from '../components/employee/employee'
import { EmployeeService } from './employee.service'

@Injectable({ providedIn: 'root' })
export class ModalService {
  public employees?: Employee[]

  constructor(private employeeService: EmployeeService) {}

  getEmployees() {
    this.employeeService.getEmployees().subscribe(
      (response: Employee[]) => {
        this.employees = response
      },
      (error: HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }

  public onOpenModal(mode: string): void {
    const container = document.getElementById('main-container')
    const button = document.createElement('button')
    button.type = 'button'
    button.style.display = 'none'
    button.setAttribute('data-toggle', 'modal')

    if (mode === 'add') {
      button.setAttribute('data-target', '#addEmployeeModal')
    }

    if (mode === 'vacation') {
      button.setAttribute('data-target', '#vacationEmployeeModal')
    }
    container?.appendChild(button)
    button.click()
  }
}
