import { Component, OnInit } from '@angular/core';
import { Usuario, UsuariosService } from '../../services/usuarios.service';
import { Router } from "@angular/router";

@Component({
  selector: 'app-agregar',
  templateUrl: './agregar.component.html',
  styleUrls: ['./agregar.component.css']
})
export class AgregarComponent implements OnInit{

  usuario: Usuario = {
    id: '',
    name: '',
    surname: '',
    lastname: '',
    password: '',
    curp: '',
    id_usuario: 0
  };

  constructor(private UsuariosService:UsuariosService, private router:Router){}

  ngOnInit(): void {
  }

  agregar(){
    delete this.usuario.id;

    this.UsuariosService.addUsuario(this.usuario).subscribe();
    this.router.navigate(['/empleados']);
  }

}
