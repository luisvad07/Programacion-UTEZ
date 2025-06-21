import { Component } from "@angular/core";
import { BreakpointObserver, Breakpoints } from "@angular/cdk/layout";
import { Observable } from "rxjs";
import { map, shareReplay } from "rxjs/operators";
import { Router } from "@angular/router";
import { LoginStateService } from "../../services/login-state.service";

@Component({
  selector: "app-navigation",
  templateUrl: "./navigation.component.html",
  styleUrls: ["./navigation.component.css"],
})
export class NavigationComponent {
  logoPath = "../../../../assets/img/utez.png";

  get session() {
    return this.loginStateService.isLogged;
  }

  isHandset$: Observable<boolean> = this.breakpointObserver
    .observe(Breakpoints.Handset)
    .pipe(
      map((result) => result.matches),
      shareReplay()
    );

  constructor(
    private breakpointObserver: BreakpointObserver,
    private router: Router,
    private loginStateService: LoginStateService
  ) {
    this.loginStateService.setIsLogged = !!localStorage.getItem("token");
    if (this.loginStateService.setIsLogged) this.router.navigateByUrl("/auth");
  }

  logout() {
    localStorage.clear();
    this.loginStateService.setIsLogged = false;
    this.router.navigateByUrl("/auth");
  }
}
