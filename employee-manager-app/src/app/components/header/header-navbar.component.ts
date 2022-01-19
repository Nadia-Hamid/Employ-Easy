import { Component, OnInit } from '@angular/core'
import { ModalService } from 'src/app/services/modal.service'
import { Employee } from '../employee/employee'

@Component({ templateUrl: 'header-navbar.component.html', selector: 'header-navbar', styleUrls: ['./header-navbar.component.css'] })

export class NavbarComponent implements OnInit {

    ngOnInit() {
    }
      constructor(private modalService: ModalService) {}

      onOpenModal(mode: string) {
      this.modalService.onOpenModal(mode)
    }
}