import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { catchError } from 'rxjs';
import { UserLogin } from '../types/user';
import { APP_URL } from '../../../services/base-urls';
import { GeneralService } from 'src/app/services/general.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private isLoading: boolean = false;
  get loading(){
    return this.isLoading;
  }

  set setLoading(value: boolean){
    this.isLoading = value;
  }

  constructor(private readonly http: HttpClient, private router: Router,
    private loginState: GeneralService) { }

  signin(user: UserLogin){
    this.setLoading = true;
    this.http.post<any>(`${APP_URL}api/auth/`, user, {
      headers: {'Conten-type': 'application/json'
    }
    })
    .pipe(
      catchError((error) => {
        this.setLoading = false;
        return error;
      })
    )
    .subscribe((response) => {
      localStorage.setItem("token", response.token);
      this.router.navigateByUrl('/');
      this.loginState.setIsLogged = true;
      this.setLoading = false;
    })
  }
}
