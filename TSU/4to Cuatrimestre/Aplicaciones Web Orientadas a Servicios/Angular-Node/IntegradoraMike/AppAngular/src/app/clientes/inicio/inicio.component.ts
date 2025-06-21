import { Component, OnInit } from '@angular/core';
import { Clientes, ClientesService } from '../../services-clientes/clientes.service';
import { Router } from '@angular/router';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-inicio',
  templateUrl: './inicio.component.html',
  styleUrls: ['./inicio.component.css']
})
export class InicioCliente implements OnInit {
  //variable
  ListarClientes: Clientes[] = [];
  title = 'appBootstrap';

  closeResult: string = '';

  letrero: String = '¿Estas seguro de Eliminar este registro?'

  constructor(private ClientesService: ClientesService, private router:Router, private modalService: NgbModal) {}

  ngOnInit(): void {
    this.listarClientes();
  }

  open(content:any) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return  `with: ${reason}`;
    }
  }

  listarClientes() {
    this.ClientesService.getClientes().subscribe(res=>{
        console.log(res);
        this.ListarClientes=<any>res;
      }
    );
  }

  eliminar(id:string) {
    this.ClientesService.deleteCliente(id).subscribe(()=>{
        console.log('Cliente Eliminado');
        this.listarClientes();
      },
      err=> console.log(err)
      );
  }

  modificar(id:string){
    this.router.navigate(['/editc/'+id]);
  }

}
