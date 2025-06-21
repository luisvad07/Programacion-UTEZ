import {NgModule} from "@angular/core";
import { HeroeMainPageComponent } from "./main-page/main-page.component";
import { ListComponent } from './list/list.component';
import { AddComponent } from './add/add.component';
import { FormsModule } from "@angular/forms";
import { CommonModule } from '@angular/common';
@NgModule({
  declarations:[HeroeMainPageComponent, ListComponent, AddComponent], //Todos los componentes del Modulo
  imports:[FormsModule, CommonModule], //Solo se agregan otros modulos
  providers: [], // Se agregan los servicios
  exports: [HeroeMainPageComponent],
})

export class HeroesModule{}
