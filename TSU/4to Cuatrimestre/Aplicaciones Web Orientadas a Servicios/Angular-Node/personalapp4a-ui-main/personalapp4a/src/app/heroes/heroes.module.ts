import { NgModule } from '@angular/core';
import { HeroesMainPageComponent } from './main-page/main-page.component';
import { ListComponent } from './list/list.component';
import { AddComponent } from './add/add.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@NgModule({
  declarations: [HeroesMainPageComponent, ListComponent, AddComponent], //Todos los componentes del modulo
  imports: [FormsModule, CommonModule], //Solo se agregan otros modulos
  providers: [], //Se agregan los servicios
  exports: [HeroesMainPageComponent],
})

export class HeroesModule{

}
