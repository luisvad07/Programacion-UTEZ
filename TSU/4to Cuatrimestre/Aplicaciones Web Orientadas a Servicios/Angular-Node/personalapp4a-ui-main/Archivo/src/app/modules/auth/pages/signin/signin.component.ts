import { Component, OnInit } from '@angular/core';
import { UserLogin } from '../../types/user';
import { AuthService } from '../../service/auth.service';
import { Router } from '@angular/router';
import { GeneralService } from 'src/app/services/general.service';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
})
export class SigninComponent implements OnInit {
  user: UserLogin = {
    email: '',
    password: '',
  };

  logoPath: string = '../../../../../assets/img/utez.png';
  get isLoading(){
    return this.authService.loading;
  }

  get session(){
    return this.loginState.isLogged;
  }

  constructor(private authService: AuthService, private router: Router,
    private loginState: GeneralService) {
    this.loginState.setIsLogged = localStorage.getItem("token")? true: false;
  }

  ngOnInit(): void {
    if(this.loginState.isLogged.logged){
      this.router.navigateByUrl('/');
    }
  }

  signin() {
    this.authService.signin(this.user);
    
  }
}
