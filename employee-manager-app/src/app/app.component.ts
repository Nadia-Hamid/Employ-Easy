import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Employee } from './employee';
import { EmployeeService } from './employee.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'employee-manager-app';
  employees?: Employee[];
  constructor(private employeeService: EmployeeService){}

  ngOnInit() {
    //this.getEmployees();
  }

  /*getEmployees(): void {
      this.employeeService.getEmployees().subscribe(
          (response: Employee[]) => {
            this.employees = response;
          },
          (error: HttpErrorResponse) => {
            alert(error.message);
          }
      )
  }*/

  public onOpenModal(mode: string):void {
    const container = document.getElementById('main-container')
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if(mode === 'add'){
      button.setAttribute('data-target', '#addEmployeeModal');
    }
    //TODO add parameter employee: Employee before mode above
    //TODO if mode for edit and delete
    container?.appendChild(button);
    button.click();
  }
}
