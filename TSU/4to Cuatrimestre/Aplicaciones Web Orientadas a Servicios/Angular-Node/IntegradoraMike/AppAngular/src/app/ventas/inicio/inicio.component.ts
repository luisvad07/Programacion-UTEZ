import { Component, OnInit } from '@angular/core';
import { VentasService, Ventas } from '../../services-ventas/ventas.service';
import { Router } from '@angular/router';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-inicio',
  templateUrl: './inicio.component.html',
  styleUrls: ['./inicio.component.css']
})
export class InicioVentas implements OnInit {
  //variable
  ListarVentas: Ventas[] = [];
  title = 'appBootstrap';

  closeResult: string = '';

  letrero: String = '¿Estas seguro de Eliminar este registro?'

  constructor(private VentasService: VentasService, private router:Router, private modalService: NgbModal) {}

  ngOnInit(): void {
    this.listarVentas();
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

  listarVentas() {
    this.VentasService.getVentas().subscribe(res=>{
        console.log(res);
        this.ListarVentas=<any>res;
      }
    );
  }

  eliminar(id:string) {
    this.VentasService.deleteVenta(id).subscribe(()=>{
        console.log('Venta Eliminada');
        this.listarVentas();
      },
      err=> console.log(err)
      );
  }

  modificar(id:string){
    this.router.navigate(['/editv/'+id]);
  }
}
