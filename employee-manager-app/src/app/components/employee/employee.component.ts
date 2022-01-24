import { Component, Input, OnInit, EventEmitter } from '@angular/core'
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
      console.log(this.reload)
  }

  reloadPage() {
    this.reload = true
    console.log("hej")
  }
}

