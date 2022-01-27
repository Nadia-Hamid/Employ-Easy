import { Component, Input, OnInit } from '@angular/core'
import { NgForm } from '@angular/forms'
import { VacationService } from 'src/app/services/vacation.service'
import { HttpErrorResponse } from '@angular/common/http'
import { CustomErrorMessage } from 'src/app/alerts/custom-error-message'

@Component({ templateUrl: 'term-vacation.component.html', selector: 'term-vacation' })
export class TermVacationComponent implements OnInit {
  yearSelect = 2022
  public bookableDates: string[]


  constructor(private vacationService: VacationService) {}

  ngOnInit() {}

  getBookableDatesVacation(bookableDatesForm: NgForm) {
    const datesFormValue = bookableDatesForm.value
    const jobTitle: String = datesFormValue.jobTitle
    const year: String = datesFormValue.year
    this.bookableDates = new Array
    this.vacationService.getBookableDates(jobTitle, year).subscribe(
      (response: any) => {
        this.bookableDates = response.vacationAvailable
      },
      (httpError: HttpErrorResponse) => {
        CustomErrorMessage(httpError)}
    )
  }
  }

