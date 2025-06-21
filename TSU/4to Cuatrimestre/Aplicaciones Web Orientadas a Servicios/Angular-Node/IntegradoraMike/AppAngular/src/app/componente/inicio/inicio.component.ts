import { Component, OnInit } from '@angular/core';
import { UsuariosService, Usuario } from '../../services/usuarios.service';
import { Router } from '@angular/router';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
@Component({
  selector: 'app-inicio',
  templateUrl: './inicio.component.html',
  styleUrls: ['./inicio.component.css']
})

export class InicioComponent implements OnInit {
  //variable
  ListarUsuario: Usuario[] = [];
  title = 'appBootstrap';

  closeResult: string = '';

  letrero: String = '¿Estas seguro de Eliminar este registro?'

  constructor(private UsuariosService: UsuariosService, private router:Router, private modalService: NgbModal) {}

  ngOnInit(): void {
    this.listarUsuario();
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

  listarUsuario() {
    this.UsuariosService.getUsuarios().subscribe(res=>{
        console.log(res);
        this.ListarUsuario=<any>res;
      }
    );
  }

  eliminar(id:string) {
    this.UsuariosService.deleteUsuario(id).subscribe(()=>{
        console.log('Usuario Eliminado');
        this.listarUsuario();
      },
      err=> console.log(err)
      );
  }

  modificar(id:string){
    this.router.navigate(['/edit/'+id]);
  }

}
