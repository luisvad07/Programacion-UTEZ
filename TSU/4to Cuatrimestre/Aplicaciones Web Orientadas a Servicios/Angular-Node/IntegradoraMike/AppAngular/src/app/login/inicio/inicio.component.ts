import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-inicio',
  templateUrl: './inicio.component.html',
  styleUrls: ['./inicio.component.css']
})
export class InicioLogin implements OnInit {
  public myForm!: FormGroup;

  constructor(private fb:FormBuilder){}

  ngOnInit(): void {
    this.myForm = this.createMyForm();
  }

  private createMyForm():FormGroup{
    return this.fb.group({
      email:[],
      password:[]
    });
  }

  public submitFormulario(){
    alert("Inicio");
    console.log(this.myForm.value);
  }

}
