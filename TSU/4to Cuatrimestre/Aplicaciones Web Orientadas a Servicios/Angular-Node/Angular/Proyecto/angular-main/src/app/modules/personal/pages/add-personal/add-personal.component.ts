import { Component, OnInit } from '@angular/core';
import { Personal } from '../../types/personal';
import { Position } from '../../../positions/types/position';
import { PersonalService } from '../../services/personal.service';
import { DialogRef } from '@angular/cdk/dialog';

@Component({
  selector: 'app-add-personal',
  templateUrl: './add-personal.component.html'
})
export class AddPersonalComponent implements OnInit {
  personal: Personal;
  positions: any[] = [];
  loadedFile: string = '';

  get edit() {
    return this.personalService.edit;
  }

  constructor(private personalService: PersonalService,
              public modalRef: DialogRef<AddPersonalComponent>) {
    this.personal = this.personalService.person;
  }

  ngOnInit(): void {
    //Back-End -> servicio que obtenga las positions
    //Realizar la petición hacia su servicio de positions
    //Asignar las positions o nuestra variable this.positions
    this.personalService.findAllPositions()
      .subscribe((response) => {
        this.positions = response;
      });
  }

  savePersonal() {
    console.log(this.personal);
    if (this.personalService.edit) {
      this.personalService.update(this.personal)
        .subscribe((response) => {
          this.modalRef.close();
        });
    } else {
      this.personalService.save(this.personal)
        .subscribe((response) => {
          this.modalRef.close();
        });
    }
  }

  previewFile(event: any) {
    const { target } = event;
    console.log(target.value);
    const reader = new FileReader();
    reader.readAsDataURL(target.files[0]);
    reader.onloadend = (result) => {
      this.loadedFile = result.target!.result + '';
    };
  }
}
