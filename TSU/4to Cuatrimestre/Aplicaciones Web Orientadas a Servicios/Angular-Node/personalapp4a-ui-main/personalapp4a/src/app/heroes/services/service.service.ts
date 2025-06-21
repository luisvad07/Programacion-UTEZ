import { Injectable } from '@angular/core';
import { Heroe } from '../types/heroe';

@Injectable({
  providedIn: 'root'
})

export class HeroesService {
  private _heroes: Heroe[] = [];

  get heroes() {
    return [...this._heroes];
  }


  saveHeroe(heroe: Heroe) {
    //Todas las validaciones correspondientes
    this._heroes.push(heroe);
  }
}