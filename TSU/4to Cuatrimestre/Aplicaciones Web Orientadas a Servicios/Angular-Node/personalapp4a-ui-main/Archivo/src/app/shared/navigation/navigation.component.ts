import { Component } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { GeneralService } from 'src/app/services/general.service';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css'],
})
export class NavigationComponent {
  
  isHandset$: Observable<boolean> = this.breakpointObserver
    .observe(Breakpoints.Handset)
    .pipe(
      map((result) => result.matches),
      shareReplay()
    );

    get session(){
      return this.loginState.isLogged;
    }
  constructor(private breakpointObserver: BreakpointObserver, 
    private loginState: GeneralService) {
    this.loginState.setIsLogged = !!localStorage.getItem('token');
  }
}
