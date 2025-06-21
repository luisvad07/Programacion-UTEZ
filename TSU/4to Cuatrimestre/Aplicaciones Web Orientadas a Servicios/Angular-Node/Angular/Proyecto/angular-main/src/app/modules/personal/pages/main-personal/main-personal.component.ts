import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Component, OnInit, ViewChild } from '@angular/core';
import { PersonalService } from '../../services/personal.service';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort, Sort } from '@angular/material/sort';
import { Personal } from '../../types/personal';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { AddPersonalComponent } from '../add-personal/add-personal.component';

@Component({
  selector: 'app-main-personal',
  templateUrl: './main-personal.component.html',
})
export class MainPersonalComponent implements OnInit {
  displayedColumns: string[] = [
    '#',
    'name',
    'surname',
    'lastname',
    'birthday',
    'salary',
    'actions'
  ];
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  personal!: MatTableDataSource<Personal>;

  get isLoading() {
    return this.personalService.loading;
  }

  constructor(private personalService: PersonalService,
              private _liveAnnouncer: LiveAnnouncer,
              public dialog: MatDialog) {
  }

  ngOnInit() {
    this.getAllPersonal();
  }

  getAllPersonal() {
    this.personalService.findAll().subscribe((response) => {
      this.personal = new MatTableDataSource<Personal>(response);
      this.personalService.loading = false;
      this.personal.paginator = this.paginator;
      this.personal.sort = this.sort;
    });
  }

  announceSortChange(sort: Sort) {
    if (sort.direction) {
      this._liveAnnouncer.announce(`Sorted ${ sort.direction } ending`);
    } else {
      this._liveAnnouncer.announce(`Sort cleared`);
    }
  }

  openDialog(
    enterAnimation: string,
    exitAnimation: string
  ) {// ng g c modules/personal/pages/addPersonal
    const modalRef = this.dialog.open(AddPersonalComponent, {
      width: '60%',
      enterAnimationDuration: enterAnimation,
      exitAnimationDuration: exitAnimation,
      disableClose: true
    });
    modalRef.afterClosed().subscribe((result: any) => {
      this.getAllPersonal();
      this.personalService.person = {
        id: 0,
        name: '',
        surname: '',
        lastname: '',
        birthday: '',
        salary: 0.0,
        position: {},
        user: undefined
      };
    });
  }

  editPerson(person: any) {
    this.personalService.person = {
      ...person,
      birthday: person.birthday.split('T')[0],
      position: { id: person.position_id }
    };
    this.personalService.edit = true;
    this.openDialog('2ms', '2ms');
  }

  changeStatus(person: Personal) {
    this.personalService.changeStatus(person)
      .subscribe((response) => {
        console.log(response);
        this.personalService.loading = false;
        this.getAllPersonal();
      });
  }
}





