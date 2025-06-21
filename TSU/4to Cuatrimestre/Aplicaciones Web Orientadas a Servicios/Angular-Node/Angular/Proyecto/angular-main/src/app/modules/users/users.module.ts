import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MainUserComponent } from './main-user/main-user.component';
import { ListUserComponent } from './list-user/list-user.component';
import { AddUserComponent } from './add-user/add-user.component';



@NgModule({
  declarations: [
  
    MainUserComponent,
       ListUserComponent,
       AddUserComponent
  ],
  imports: [
    CommonModule
  ]
})
export class UsersModule { }
