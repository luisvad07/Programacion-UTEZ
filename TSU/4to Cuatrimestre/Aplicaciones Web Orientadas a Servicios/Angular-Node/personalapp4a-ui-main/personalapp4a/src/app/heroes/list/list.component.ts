import { Component } from '@angular/core';
import { HeroesService } from '../services/service.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html'
})
export class ListComponent{

  get heroes(){
    return this.heroeService.heroes;
  }
  constructor(private readonly heroeService: HeroesService) {
    console.log("Constructor");
  }

}
