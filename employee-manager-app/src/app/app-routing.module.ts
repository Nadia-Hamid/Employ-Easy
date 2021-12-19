import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EmployeeComponent } from './employee/employee.component';
import { LoginComponent } from './login/login.component';
import { UserComponent } from './user/user.component';


const routes: Routes = [
    { path:"", redirectTo:"login", pathMatch:"full" },
    { path: 'login', component: LoginComponent },
    { path: 'v1/employees', component: EmployeeComponent },
    { path: 'v1/employees/user', component: UserComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }