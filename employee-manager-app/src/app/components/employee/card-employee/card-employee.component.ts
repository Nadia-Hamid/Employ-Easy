import { Component, Input, OnInit } from '@angular/core'
import { HttpErrorResponse } from '@angular/common/http'
import { EmployeeService } from '../../../services/employee.service'
import { Employee } from '../employee'

@Component({ templateUrl: 'card-employee.component.html', selector: 'card' })
export class CardComponent implements OnInit {
  @Input() public employees?: Employee[]
  @Input() public deleteEmployee: Employee
  @Input() public editEmployee: Employee
  @Input() public infoEmployee: Employee
  @Input() public reload: boolean

  public userStatusList: string[] = ['ACTIVE', 'INACTIVE', 'VACATION', 'MATERNITY_LEAVE', 'OFF_DUTY', 'ARCHIVE']
  public systemStatusList: string[] = ['SYSTEM_ADMIN', 'USER']

  constructor(private employeeService: EmployeeService) {}

  ngOnInit(): void {
    this.getEmployees()
  }

  ngOnChanges() {
    if (this.reload === true) {
      window.location.reload()
    }
  }

  reloadPage() {
    window.location.reload()
  }

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
    if (mode == 'info') {
      this.infoEmployee = employee
      button.setAttribute('data-target', '#infoEmployeeModal')
    }

    container?.appendChild(button)
    button.click()
  }
}
