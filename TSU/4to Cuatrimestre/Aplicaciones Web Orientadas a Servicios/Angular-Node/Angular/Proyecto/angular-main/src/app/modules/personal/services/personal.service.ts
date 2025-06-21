import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Personal } from '../types/personal';
import { APP_URL } from 'src/app/services/base-url.app';

@Injectable({
  providedIn: 'root',
})
export class PersonalService {
  loading: boolean = false;
  private people: Personal[] = [];
  edit: boolean = false;
  person: Personal = {
    id: 0,
    name: '',
    surname: '',
    lastname: '',
    birthday: '',
    salary: 0.0,
    position: {},
    user: undefined
  };

  get personal() {
    return [ ...this.people ];
  }

  constructor(private http: HttpClient) {
  }

  findAll() {
    this.loading = true;
    return this.http.get<Personal[]>(`${ APP_URL }api/personal/`);
  }

  findAllPositions() {
    this.loading = true;
    return this.http.get<any>(`${ APP_URL }api/position/`);
  }

  save(personal: Personal) {
    this.loading = true;
    return this.http.post<Personal>(`${ APP_URL }api/personal/`, personal);
  }

  update(personal: Personal) {
    this.loading = true;
    return this.http.put<Personal>(`${ APP_URL }api/personal/`, personal);
  }

  changeStatus(person: Personal) {
    this.loading = true;
    return this.http.delete<Personal>(`${ APP_URL }api/personal/`,
      { body: person });
  }
}
