import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { catchError } from "rxjs";
import { APP_URL } from "src/app/services/base-url.app";
import { User } from "../types/user";
import { LoginStateService } from '../../../services/login-state.service';

@Injectable({
  providedIn: "root",
})
export class AuthService {
  private loading: boolean = false;
  get isLoading() {
    return this.loading;
  }

  constructor(private httpClient: HttpClient, private router: Router,
    private loginState:LoginStateService) {}

  login(user: User) {
    this.loading = true;
    this.httpClient
      .post<any>(APP_URL + "api/auth/", user, {
        headers: { "Content-Type": "application/json" },
      })
      .pipe(
        catchError((error)=>{
          this.loading = false;
            return error;
          })
      )
      .subscribe((response) => {
        localStorage.setItem("token", response.token);
        this.loading = false;
        this.loginState.setIsLogged = true;
        this.router.navigateByUrl("/");
      });
  }
}
