import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SigninComponent } from './pages/signin/signin.component';
import { FormsModule } from '@angular/forms';
import { materialModules } from 'src/app/types/material-modules';

@NgModule({
  declarations: [SigninComponent],
  imports: [CommonModule, FormsModule, ...materialModules],
  exports: [SigninComponent],
  bootstrap: [SigninComponent],
})
export class AuthModule {}
