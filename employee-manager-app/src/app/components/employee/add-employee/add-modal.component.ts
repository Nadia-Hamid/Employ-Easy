import { Component, OnInit, Output, EventEmitter } from '@angular/core'
import { HttpErrorResponse } from '@angular/common/http'
import { NgForm } from '@angular/forms'
import { EmployeeService } from '../../../services/employee.service'

@Component({ templateUrl: 'add-modal.component.html', selector: 'add-modal' })
export class AddModalComponent implements OnInit {
  public userStatusList: string[] = ['ACTIVE', 'INACTIVE', 'VACATION', 'MATERNITY_LEAVE', 'OFF_DUTY', 'ARCHIVE']
  public systemStatusList: string[] = ['SYSTEM_ADMIN', 'USER']

  @Output() reload = new EventEmitter()

  constructor(private employeeService: EmployeeService) {}

  ngOnInit(): void {}

  reloadPage() {
    this.reload.emit()
  }

  public onAddEmployee(addForm: NgForm): void {
    document.getElementById('add-employee-form')?.click()
    this.employeeService.addEmployee(addForm.value).subscribe(
      () => {
        addForm.reset()
      },
      (error: HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }
}
