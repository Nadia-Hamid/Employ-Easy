import { Component, OnInit } from '@angular/core'
import { Employee } from './employee'

@Component({ templateUrl: 'employee.component.html' })
export class EmployeeComponent implements OnInit {
  public employees?: Employee[]
  public deleteEmployee: Employee
  public editEmployee: Employee
  public infoEmployee: Employee
  public userStatusList: string[] = ['ACTIVE', 'INACTIVE', 'VACATION', 'MATERNITY_LEAVE', 'OFF_DUTY', 'ARCHIVE']
  public systemStatusList: string[] = ['SYSTEM_ADMIN', 'USER']
  public reload: boolean = false
  

  constructor() {}

  ngOnInit() {
  }

  reloadPage() {
    this.reload = true
  }
}

