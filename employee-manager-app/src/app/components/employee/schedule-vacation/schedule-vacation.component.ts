import { Component, Input, OnInit } from '@angular/core'
import { NgForm } from '@angular/forms'
import { HttpErrorResponse } from '@angular/common/http'
import { VacationService } from '../../../services/vacation.service'
import { VacationBookedPeriod } from './vacationBookedPeriod'

@Component({ templateUrl: 'schedule-vacation.component.html', selector: 'schedule-vacation' })
export class ScheduleVacationComponent implements OnInit {

    public confirmedDates: VacationBookedPeriod
    constructor(private vacationService: VacationService) {}

  ngOnInit() {}

  onScheduleVacation(vacationForm: NgForm) {
    let vacationTerm = vacationForm.value
    let jobTitle: String = vacationTerm.jobTitle
    delete vacationTerm.jobTitle
    this.vacationService.scheduleVacationPeriods(jobTitle, vacationTerm).subscribe(
      (response: VacationBookedPeriod) => {
        this.confirmedDates = response
        alert("Confirmed vacation period " + this.confirmedDates.startDate + " " 
        + this.confirmedDates.endDate + " .Multiple: " + this.confirmedDates.multiple)
      },
      (error: HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }
}
