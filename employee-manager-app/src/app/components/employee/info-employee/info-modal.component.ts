import { Component, OnInit, Input } from '@angular/core'
import { HttpErrorResponse } from '@angular/common/http'
import { NgForm } from '@angular/forms'
import { EmployeeService } from '../../../services/employee.service'
import { Employee } from '../employee'
import { ModalService } from 'src/app/services/modal.service'

@Component({ templateUrl: 'info-modal.component.html', selector: 'info-modal' })
export class InfoModalComponent implements OnInit {
    @Input() public infoEmployee: Employee

  ngOnInit(): void {
  }

  constructor(private employeeService: EmployeeService, private modalService: ModalService) {}

  public onOpenModal(employee: Employee, mode: string): void {
    const container = document.getElementById('main-container')
    const button = document.createElement('button')
    button.type = 'button'
    button.style.display = 'none'
    button.setAttribute('data-toggle', 'modal')
  
    if (mode == 'info') {
        this.infoEmployee = employee
        button.setAttribute('data-target', '#infoEmployeeModal')
      }

    container?.appendChild(button)
    button.click()
  }
}
