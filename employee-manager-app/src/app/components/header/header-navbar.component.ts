import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core'
import { ModalService } from 'src/app/services/modal.service'
import { Employee } from '../employee/employee'

@Component({
  templateUrl: 'header-navbar.component.html',
  selector: 'header-navbar',
  styleUrls: ['./header-navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  public userRole: String = localStorage.getItem('userRole')
  @Input() public employee: Employee
  @Output() reload = new EventEmitter()

  constructor(private modalService: ModalService) {}

  ngOnInit() {}

  reloadPage() {
    this.reload.emit()
  }

  onOpenModal(mode: string) {
    this.modalService.onOpenModal(mode)
  }
}
