import { Component, OnInit, Input } from '@angular/core'
import { HttpErrorResponse } from '@angular/common/http'
import { EmployeeService } from '../../../services/employee.service'
import { Employee } from '../employee'


@Component({ templateUrl: 'update-modal.component.html', selector: 'update-modal' })
export class UpdateModalComponent implements OnInit {
    @Input() public editEmployee: Employee
    
    public userStatusList: string[] = ['ACTIVE', 'INACTIVE', 'VACATION', 'MATERNITY_LEAVE', 'OFF_DUTY', 'ARCHIVE']
    public systemStatusList: string[] = ['SYSTEM_ADMIN', 'USER']
  
  ngOnInit(): void {
  }

  constructor(private employeeService: EmployeeService) {}

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