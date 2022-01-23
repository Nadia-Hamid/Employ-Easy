import { Component, Input, OnInit } from '@angular/core'
import { EmployeeService } from '../../../services/employee.service'
import { HttpErrorResponse } from '@angular/common/http'
import { Employee } from '../employee'
import { ModalService } from 'src/app/services/modal.service'

@Component({ templateUrl: 'delete-modal.component.html', selector: 'delete-modal' })
export class DeleteModalComponent implements OnInit { 
    @Input() public deleteEmployee: Employee

  constructor(private employeeService: EmployeeService, private modalService: ModalService) {}

  ngOnInit() {
  }

  getEmployees() {
    this.modalService.getEmployees();
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
}