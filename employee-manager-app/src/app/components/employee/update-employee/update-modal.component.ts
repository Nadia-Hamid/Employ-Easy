import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core'
import { HttpErrorResponse } from '@angular/common/http'
import { EmployeeService } from '../../../services/employee.service'
import { Employee } from '../employee'


@Component({ templateUrl: 'update-modal.component.html', selector: 'update-modal' })
export class UpdateModalComponent implements OnInit {
    @Input() public editEmployee: Employee
    @Output() public reload = new EventEmitter();
    
    public userStatusList: string[] = ['ACTIVE', 'INACTIVE', 'VACATION', 'MATERNITY_LEAVE', 'OFF_DUTY', 'ARCHIVE']
    public systemStatusList: string[] = ['SYSTEM_ADMIN', 'USER']
  
  ngOnInit(): void {
  }
  
  constructor(private employeeService: EmployeeService) {}

  reloadPage() {
    this.reload.emit();
  }

  public onUpdateEmployee(employee: Employee): void {
    this.employeeService.updateEmployee(employee).subscribe(
      (response: Employee) => {
      },
      (error: HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }
}