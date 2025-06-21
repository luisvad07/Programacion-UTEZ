import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { APP_URL } from 'src/app/services/base-urls';
import { Position } from '../types/position';

@Injectable({
  providedIn: 'root'
})

export class PositionsService {
  public loading: boolean = false;
  private positions: Position[] = [];

  get gpositions(){
    return [ ...this.positions];
  }

  constructor(private http: HttpClient) { }

  findAll(){
    this.loading = true;
    return this.http
    .get<Position[]>(`${APP_URL}api/position/`)
  }

  save(position: Position){
    this.loading = true;
    return this.http
    .post<Position>(`${APP_URL}api/position/`, position);
  }
}
