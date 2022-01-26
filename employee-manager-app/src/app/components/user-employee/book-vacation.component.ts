import { Component, Input, OnInit } from '@angular/core'
import { NgForm } from '@angular/forms'
import { VacationService } from '../../services/vacation.service'
import { HttpErrorResponse } from '@angular/common/http'
import { Vacation } from './vacation'
import { Employee } from '../employee/employee'
import { MyAnnualAllowance } from './my-annual-allowance'

@Component({ templateUrl: 'book-vacation.component.html', selector: 'book-vacation' })
export class VacationComponent implements OnInit {
  public vacationDates: Vacation[]
  @Input() public employee: Employee

  constructor(private vacationService: VacationService) {}

  ngOnInit() {
    this.getVacationDatesForUser(this.employee?.jobTitle, this.employee?.userId)
  }

  getVacationDatesForUser(jobTitle: String, userId: String) {
    this.vacationService.getVacationDatesForUser(jobTitle, userId).subscribe(
      (response: MyAnnualAllowance) => { 
        this.vacationDates = new Array
        var futureUnbooked = response.futureUnbooked
        console.log(futureUnbooked)
        for(var i in futureUnbooked) {
          let vacationData = {} as Vacation 
          let json = JSON.stringify(futureUnbooked[i])
          let parts = json.split("\"");
          vacationData.date = parts[3]
          vacationData.userId = userId
          this.vacationDates.push(vacationData)
        }
      },
      (error: HttpErrorResponse) => {
        if (error.error instanceof Error) {
            alert('An error occurred:'+ error.error.message);
        } else {
          alert(`${JSON.stringify(error.error)}`);
        }
      }
    )
  }

  onAddVacation(addForm: NgForm, userId: String, jobTitle: String): void {
    let vacation = addForm.value
    vacation['userId'] = userId

    this.vacationService.reserveVacationDate(jobTitle, vacation).subscribe(
      () => {
        this.getVacationDatesForUser(this.employee?.jobTitle, this.employee?.userId)
      },
      (error: HttpErrorResponse) => {
        if (error.error instanceof Error) {
            alert('An error occurred:'+ error.error.message);
        } else {
          alert(`${JSON.stringify(error.error)}`);
        }
      }
    )
  }
}
