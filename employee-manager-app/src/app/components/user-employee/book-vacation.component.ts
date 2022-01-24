import { Component, Input, OnInit } from '@angular/core'
import { NgForm } from '@angular/forms'
import { VacationService } from '../../services/vacation.service'
import { HttpErrorResponse } from '@angular/common/http'
import { Vacation } from './vacation'
import { Employee } from '../employee/employee'

@Component({ templateUrl: 'book-vacation.component.html', selector: 'book-vacation' })
export class VacationComponent implements OnInit {
  public vacationDates: Vacation[]
  @Input() public employee: Employee

  constructor(private vacationService: VacationService) {}

  ngOnInit() {
    this.getVacationaDates(this.employee?.jobTitle)
  }

  getVacationaDates(jobTitle: String) {
    this.vacationService.getVacationDates(jobTitle).subscribe(
      (response: Vacation[]) => {
        this.vacationDates = response
      },
      (error: HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }

  onAddVacation(addForm: NgForm, userId: String, jobTitle: String): void {
    console.log(addForm.value, userId, jobTitle)
    let vacation = addForm.value
    vacation['userId'] = userId

    this.vacationService.reserveVacationDate(jobTitle, vacation).subscribe(
      (response: void) => {
        this.getVacationaDates(this.employee?.jobTitle)
      },
      (error: HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }
}
