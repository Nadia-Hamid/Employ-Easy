import { Component, OnInit } from '@angular/core'
import { Router, ActivatedRoute } from '@angular/router'
import { FormBuilder, FormGroup } from '@angular/forms'

import { LoginService } from '../services/login.service'

@Component({ templateUrl: 'login.component.html' })
export class LoginComponent implements OnInit {
  form: FormGroup
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
      (data) => {
        this.router.navigate(['v1/employees'])
      },
      (error) => {
        this.loading = false
      }
    )
  }
}
