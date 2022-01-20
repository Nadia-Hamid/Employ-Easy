import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';


@Injectable({ providedIn: 'root' })
export class LoginService {

    private apiServerUrl = environment.apiBaseUrlEmployee;

    constructor(
        private http: HttpClient
    ) {}

    login(username: String, password: String) {
        const headers = new HttpHeaders({Authorization: 'Basic ' +btoa(username +":"+ password)
            })
        return this.http.get(`${this.apiServerUrl}/v1/auth`,  { headers })
    }

    
}