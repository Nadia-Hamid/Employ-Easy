import { HttpErrorResponse } from '@angular/common/http'
import { Component, Input, OnInit, Output, SimpleChanges } from '@angular/core'
import { CustomErrorMessage } from 'src/app/alerts/custom-error-message'
import { VacationService } from 'src/app/services/vacation.service'
import { Employee } from '../../employee/employee'
import { MyAnnualAllowance } from '../my-annual-allowance'

@Component({
  templateUrl: 'reserved-vacation-user.component.html',
  selector: 'reserved-vacation-user',
  styleUrls: ['./reserved-vacation-user.component.css'],
})
export class ReservedVacationUser implements OnInit {
  public userRole: String = localStorage.getItem('userRole')
  public annualAllowance: MyAnnualAllowance
  public futureUnbookedDates: String[]
  @Input() employee: Employee
  @Input() update: boolean

  constructor(private vacationService: VacationService) {}

  ngOnInit() {
  }

  ngOnChanges() {
    this.getVacationDates;
  }
  
  
    getVacationDates() {
        this.futureUnbookedDates = new Array()
        this.vacationService.getVacationDatesForUser(this.employee.jobTitle, this.employee.userId).subscribe(
          (response: MyAnnualAllowance) => {
            this.annualAllowance = response
            for (let item in response.futureUnbooked) {
              this.futureUnbookedDates.push(response.futureUnbooked[item].date.toString())
            }
          },
          (httpError: HttpErrorResponse) => {
            CustomErrorMessage(httpError)
          }
        )
     this.getVacationDates()
    }
}
