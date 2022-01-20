import { Component, Input, OnInit } from '@angular/core'
import { ModalService } from 'src/app/services/modal.service'

@Component({ templateUrl: 'header-navbar.component.html', selector: 'header-navbar', styleUrls: ['./header-navbar.component.css'] })

export class NavbarComponent implements OnInit {
    public userRole: String = localStorage.getItem('userRole')
    @Input()public jobTitle: String

    constructor(private modalService: ModalService) {}
    
    ngOnInit() {
    }
      onOpenModal(mode: string) {
      this.modalService.onOpenModal(mode)
    }
}