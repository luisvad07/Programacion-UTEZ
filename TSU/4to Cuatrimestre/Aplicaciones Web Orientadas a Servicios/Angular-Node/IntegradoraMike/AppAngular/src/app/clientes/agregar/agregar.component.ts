import { Component, OnInit} from '@angular/core';
import { Clientes, ClientesService } from '../../services-clientes/clientes.service';
import { Router } from "@angular/router";

@Component({
  selector: 'app-agregar',
  templateUrl: './agregar.component.html',
  styleUrls: ['./agregar.component.css']
})
export class AgregarCliente implements OnInit {

  cliente: Clientes = {
    id: '',
    nombre: '',
    apellidos: '',
    email: '',
    password: '',
    id_cliente: 0
  };

  constructor(private ClientesService:ClientesService, private router:Router){}

  ngOnInit(): void {
  }

  agregar(){
    delete this.cliente.id;

    this.ClientesService.addCliente(this.cliente).subscribe();
    this.router.navigate(['/clientes']);
  }

}
