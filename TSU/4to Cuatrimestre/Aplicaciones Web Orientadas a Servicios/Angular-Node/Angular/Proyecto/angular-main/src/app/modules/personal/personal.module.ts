import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MainPersonalComponent } from "./pages/main-personal/main-personal.component";
import { FormsModule } from "@angular/forms";
import { materialModules } from "src/app/services/material.modules";
import { AddPersonalComponent } from './pages/add-personal/add-personal.component';

@NgModule({
  declarations: [
    MainPersonalComponent,
    AddPersonalComponent,
  ],
  imports: [CommonModule, FormsModule, ...materialModules],
  exports: [MainPersonalComponent],
  bootstrap: [MainPersonalComponent],
})
export class PersonalModule {}
