import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core'
import { EmployeeService } from '../../../services/employee.service'
import { HttpErrorResponse } from '@angular/common/http'
import { Employee } from '../employee'
import { ModalService } from 'src/app/services/modal.service'
import { CustomErrorMessage } from 'src/app/alerts/custom-error-message'

@Component({ templateUrl: 'delete-modal.component.html', selector: 'delete-modal' })
export class DeleteModalComponent implements OnInit {
  @Input() public deleteEmployee: Employee
  @Output() reload = new EventEmitter()

  constructor(private employeeService: EmployeeService) {}

  ngOnInit() {}

  reloadPage() {
    this.reload.emit()
  }

  public onDeleteEmployee(userId: String) {
    this.employeeService.deleteEmployee(userId).subscribe(
      (response: void) => {},
      (httpError: HttpErrorResponse) => {
        CustomErrorMessage(httpError)}
    )
  }
}
