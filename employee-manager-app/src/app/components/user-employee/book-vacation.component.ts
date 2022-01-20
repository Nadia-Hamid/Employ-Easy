import { Component, Input, OnInit } from "@angular/core"
import { NgForm } from "@angular/forms";
import { VacationService } from "../../services/vacation.service"
import { HttpErrorResponse } from '@angular/common/http'
import { Vacation } from "./vacation";

@Component({ templateUrl: 'book-vacation.component.html', selector: 'book-vacation' })
export class VacationComponent implements OnInit {
    public vacationDates: Vacation[]
    @Input() public jobTitle: String
    
    constructor(private vacationService: VacationService) {}
    
    ngOnInit() {
        this.getVacationaDates(this.jobTitle)
    }

  getVacationaDates(jobTitle: String) {
    this.vacationService.getVacationDates(jobTitle).subscribe(
      (response: Vacation[]) => {
        this.vacationDates= response
      },
      (error: HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }

  onAddVacation(addForm: NgForm) {
      // console.log("Test")
  }

}