import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

import { environment } from 'src/environments/environment';


@Injectable({ providedIn: 'root' })
export class LoginService {


    constructor(
        private http: HttpClient
    ) {}

    login(username: String, password: String) {
        const headers = new HttpHeaders({Authorization: 'Basic ' +btoa(username +":"+ password)
            })
        return this.http.get(`${environment.apiBaseUrl}/v1/employees`,  { headers })
    }

    
}