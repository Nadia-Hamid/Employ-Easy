import { Injectable } from '@angular/core'
import { HttpErrorResponse } from '@angular/common/http'
import { Employee } from '../components/employee/employee'
import { EmployeeService } from './employee.service'
import { CustomErrorMessage } from '../alerts/custom-error-message'

@Injectable({ providedIn: 'root' })
export class ModalService {
  public employees?: Employee[]

  constructor(private employeeService: EmployeeService) {}

  getEmployees() {
    this.employeeService.getEmployees().subscribe(
      (response: Employee[]) => {
        this.employees = response
      },
      (httpError: HttpErrorResponse) => {
        CustomErrorMessage(httpError)}
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

    if (mode === 'vacationSchedule') {
      button.setAttribute('data-target', '#vacationScheduleEmployeeModal')
    }

    if (mode === 'vacationTermPeriods') {
      button.setAttribute('data-target', '#vacationTermPeriodsEmployeeModal')
    }

    if (mode === 'reservedVacation') {
        button.setAttribute('data-target', '#reservedVacationEmployeeModal')
      }
    container?.appendChild(button)
    button.click()
  }
}
