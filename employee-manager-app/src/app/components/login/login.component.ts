import { Component, OnInit } from '@angular/core'
import { Router, ActivatedRoute, Data } from '@angular/router'
import { FormBuilder, FormGroup } from '@angular/forms'

import { LoginService } from '../../services/login.service'
import { HttpHeaderResponse } from '@angular/common/http'

@Component({ templateUrl: 'login.component.html' })
export class LoginComponent implements OnInit {
  form: FormGroup
  private responseData: any
  loading = false
  submitted = false

constructor(private formBuilder: FormBuilder, private router: Router, private loginService: LoginService) {}

  ngOnInit() {
    this.form = this.formBuilder.group({
      username: '',
      password: '',
    })
  }
  
  get f() {
    return this.form.controls
  }

  onSubmit() {
    this.submitted = true
    if (this.form.invalid) {
      return
    }
    this.loginService.login(this.f.username.value, this.f.password.value).subscribe(
      (response) => {
        this.responseData = response
        localStorage.setItem('userName', this.responseData.username.toString())
        let userRole = this.responseData.authorities[0].authority.toString()
        this.redirectUser(userRole)
      },
      (error) => {
        this.loading = false
      }
    )
  }
  redirectUser(userRole: string) {
    if ('ROLE_ADMIN' == userRole) {
      this.router.navigate(['/v1/employees'])
    }
    if ('ROLE_EMPLOYEE' == userRole) {
      this.router.navigate(['/v1/employees/user'])
    }
  }
}