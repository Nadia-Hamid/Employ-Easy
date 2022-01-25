import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { EmployeeComponent } from './components/employee/employee.component';
import { LoginComponent } from './components/login/login.component';
import { UserComponent } from './components/user-employee/user.component';
import { NavbarComponent } from './components/header/header-navbar.component'
import { InfoModalComponent } from './components/employee/info-employee/info-modal.component';
import { UpdateModalComponent } from './components/employee/update-employee/update-modal.component';
import { AddModalComponent } from './components/employee/add-employee/add-modal.component';
import { CardComponent } from './components/employee/card-employee/card-employee.component';
import { DeleteModalComponent } from './components/employee/delete-employee/delete-modal.component';
import { VacationComponent } from './components/user-employee/book-vacation.component';


@NgModule({
  declarations: [
    AppComponent,
    EmployeeComponent,
    LoginComponent,
    UserComponent,
    NavbarComponent,
    InfoModalComponent,
    UpdateModalComponent,
    AddModalComponent,
    CardComponent,
    DeleteModalComponent,
    VacationComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
