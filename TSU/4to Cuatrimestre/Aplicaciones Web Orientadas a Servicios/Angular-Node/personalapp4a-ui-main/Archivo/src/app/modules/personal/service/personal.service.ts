import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { APP_URL } from 'src/app/services/base-urls';
import { Personal } from '../types/personal';

@Injectable({
  providedIn: 'root'
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
