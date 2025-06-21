import { DialogRef } from '@angular/cdk/dialog';
import { Component, OnInit } from '@angular/core';
import { MatSelect, MatSelectChange, MatSelectModule } from '@angular/material/select';
import { PersonalService } from '../../service/personal.service';
import { PositionsService } from '../../service/positions.service';
import { Personal } from '../../types/personal';
import { Position } from '../../types/position';

@Component({
  selector: 'app-add-personal',
  templateUrl: './add-personal.component.html'
})
export class AddPersonalComponent implements OnInit {
  personal: Personal;
  positions: Position[] = [];
  loadedFile: string = '';

  constructor(private positionsService: PositionsService,
    private personalService: PersonalService,
    public modalRef: DialogRef<AddPersonalComponent>) {
      this.personal = this.personalService.person;
     }

  ngOnInit(): void {
    //Back-End -> Servicio que obtenga las positions
    //Realizar la petición hacia su servicio de positions
    //Asignar las positions a nuestra variable this.positions
    this.positionsService.findAll()
      .subscribe((response) => {
        console.log(response);
        this.positions = response;
      })
  }

  //Para bloquear campos si se esta editando un usuario.
  //[disabled]="edit"
  get edit(){
    return this.personalService.edit;
  }

  savePersonal() {
    console.log(this.personal);
    this.personal.avatar = this.loadedFile;
    if (this.personalService.edit && this.personal.id!=0) {
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
