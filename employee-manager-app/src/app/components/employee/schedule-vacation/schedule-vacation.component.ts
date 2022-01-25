import { Component, Input, OnInit } from '@angular/core'
import { NgForm } from '@angular/forms'
import { HttpErrorResponse } from '@angular/common/http'
import { VacationService } from '../../../services/vacation.service'

@Component({ templateUrl: 'schedule-vacation.component.html', selector: 'schedule-vacation' })
export class ScheduleVacationComponent implements OnInit {
    constructor(private vacationService: VacationService) {}

  ngOnInit() {}

  onScheduleVacation(vacationForm: NgForm) {
    let vacationTerm = vacationForm.value
    let jobTitle: String = vacationTerm.jobTitle
    delete vacationTerm.jobTitle
    this.vacationService.scheduleVacationPeriods(jobTitle, vacationTerm).subscribe(
      (response: void) => {},
      (error: HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }
}
