import { Component, OnInit } from '@angular/core';
import {Clientes, ClientesService} from '../../services-clientes/clientes.service';
import {Router, ActivatedRoute} from '@angular/router';


@Component({
  selector: 'app-modificar',
  templateUrl: './modificar.component.html',
  styleUrls: ['./modificar.component.css']
})
export class ModificarCliente implements OnInit {

  cliente: Clientes = {
    id: '',
    nombre: '',
    apellidos: '',
    email: '',
    password: '',
    id_cliente: 0
  };

  constructor(private ClientesService:ClientesService, private router:Router, private activeRoute:ActivatedRoute) { }

  ngOnInit(): void {
    const id_entrada = <string>this.activeRoute.snapshot.params['id'];
    console.log('id de entrada: '+id_entrada);

    if(id_entrada){
      this.ClientesService.getUnCliente(id_entrada).subscribe(res=>{
          this.cliente = res[0];
          console.log(res[0]);
        },
        err=>console.log(err)
      );
    }
  }

  modificar(){
    this.ClientesService.editCliente(this.cliente.id!, this.cliente).subscribe(res=>{
        console.log(res);
      },
        err=>console.log(err)
    );
    this.router.navigate(['/clientes']);
  }

}
