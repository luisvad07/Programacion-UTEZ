import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainPersonalComponent } from '../../modules/personal/pages/main-personal/main-personal.component';
import { SigninComponent } from '../../modules/auth/pages/signin/signin.component';
import { AppComponent } from '../../app.component';

const routes: Routes = [
  {
    path: '',
    component: MainPersonalComponent,
    pathMatch: 'full',
  },
  {
    path: 'auth',
    component: SigninComponent,
  },
  {
    path: '**',
    redirectTo: '',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  bootstrap: [AppComponent],
})
export class AppRouterModule {}
