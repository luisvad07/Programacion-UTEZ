import { Component, OnInit } from '@angular/core';
import {Usuario, UsuariosService} from '../../services/usuarios.service';
import {Router, ActivatedRoute} from '@angular/router';


@Component({
  selector: 'app-modificar',
  templateUrl: './modificar.component.html',
  styleUrls: ['./modificar.component.css']
})
export class ModificarComponent implements OnInit {

  usuario: Usuario = {
    id: '',
    name: '',
    surname: '',
    lastname: '',
    password: '',
    curp: '',
    id_usuario: 0
  };

  constructor(private UsuariosService:UsuariosService, private router:Router, private activeRoute:ActivatedRoute) { }

  ngOnInit(): void {
    const id_entrada = <string>this.activeRoute.snapshot.params['id'];
    console.log('id de entrada: '+id_entrada);

    if(id_entrada){
      this.UsuariosService.getUnUsuario(id_entrada).subscribe(res=>{
          this.usuario = res[0];
          console.log(res[0]);
        },
        err=>console.log(err)
      );
    }
  }

  modificar(){
    this.UsuariosService.editUsuario(this.usuario.id!, this.usuario).subscribe(res=>{
        console.log(res);
      },
        err=>console.log(err)
    );
    this.router.navigate(['/empleados']);
  }

}
