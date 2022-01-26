import { Component, Input, OnInit } from '@angular/core'
import { NgForm } from '@angular/forms'
import { HttpErrorResponse } from '@angular/common/http'
import { VacationService } from '../../../services/vacation.service'
import { VacationBookedPeriod } from './vacationBookedPeriod'
import { CustomErrorMessage } from 'src/app/alerts/custom-error-message'

@Component({ templateUrl: 'schedule-vacation.component.html', selector: 'schedule-vacation' })
export class ScheduleVacationComponent implements OnInit {

  constructor(private vacationService: VacationService) {}
  public confirmedDates: VacationBookedPeriod

  //Error display
  isValidDate: any

  ngOnInit() {}

 
  onScheduleVacation(vacationForm: NgForm) {

    let vacationTerm = vacationForm.value
    let startDate: Date = vacationTerm.startDate
    let endDate: Date = vacationTerm.endDate
    let jobTitle: String = vacationTerm.jobTitle

    delete vacationTerm.jobTitle
    
    this.isValidDate = this.dateValidation(startDate, endDate);

    if (this.isValidDate) {
      this.vacationService.scheduleVacationPeriods(jobTitle, vacationTerm).subscribe(
        (response: VacationBookedPeriod) => {
          this.confirmedDates = response
          alert("Confirmed vacation period " + this.confirmedDates.startDate + " / "
            + this.confirmedDates.endDate + ". Multiple: " + this.confirmedDates.multiple + ".")
        },
        (httpError: HttpErrorResponse) => {
          CustomErrorMessage(httpError)
        }
      )
    }
  }

  dateValidation(startDate: Date, endDate: Date) {

    this.isValidDate = true;
    if ((startDate != null && endDate != null) && (endDate) < (startDate)) {
      this.isValidDate = false;
      alert('End date should be greater that start date.')
    }
    return this.isValidDate;
  }
}
